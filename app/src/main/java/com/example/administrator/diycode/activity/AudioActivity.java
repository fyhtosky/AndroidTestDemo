package com.example.administrator.diycode.activity;

import android.Manifest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.activity.base.BaseActivity;
import com.example.administrator.diycode.audioView.VisualFrequencyView;
import com.example.administrator.diycode.audioView.common.FileHelper;
import com.example.network_sdk.base.BaseView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@RuntimePermissions
public class AudioActivity extends BaseActivity implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnBufferingUpdateListener {

    private static final int ACTION_START_RECORD_AUDIO = 1;
    private static final int ACTION_UPDATE_RECORD_TIME = 2;
    private static final int ACTION_FINISH_RECORD_AUDIO = 3;
    private static final int ACTION_UPDATE_PLAY_TIME = 5;
    private static final int ACTION_FINISH_PLAY_AUDIO = 6;
    private static final int PLAY_STATE_PREPARED = 12;
    private static final int PLAY_STATE_PLAYING = 13;
    private static final int PLAY_STATE_PAUSE = 14;
    private static final int PLAY_STATE_PLAY_COMPLETED = 15;
    private static final int PLAY_STATE_RELEASED = 16;
    private static final int RECORD_STATE_INIT = 21;
    private static final int RECORD_STATE_RECORDING = 22;
    private static final int RECORD_STATE_STOP = 23;
    private static final int STATE_OCCUR_ERROR = 25;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.frequency_view)
    VisualFrequencyView frequencyView;
    @BindView(R.id.player_pass_time)
    TextView playerPassTime;
    @BindView(R.id.player_total_time)
    TextView playerTotalTime;
    @BindView(R.id.player_sb_progress)
    SeekBar rPlayerSbProgress;
    private MediaRecorder mediaRecorder;
    private Timer mTimer;
    private TimerTask mTimerTask;
    //录音器状态
    private int currentRecordState = RECORD_STATE_INIT;
    //播放器状态
    private int currentPlayState;
    //录音开始时间
    private long audioRecordStartTime;
    // 录音播放器
    private MediaPlayer mediaPlayer;
    // 录制文件存放路径
    private File recordFolder;
    // 当前录制文件的引用
    private File currentRecordFile;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ACTION_START_RECORD_AUDIO:
                    frequencyView.startResponse();
                    handler.sendEmptyMessageDelayed(ACTION_UPDATE_RECORD_TIME, 500);
                    break;
                case ACTION_UPDATE_RECORD_TIME:
                    if (mediaRecorder != null && currentRecordState == RECORD_STATE_RECORDING) {
                        final int duration = (int) (System.currentTimeMillis() - audioRecordStartTime);
                        tvTime.setText(formatTime(duration));
                        handler.sendEmptyMessageDelayed(ACTION_UPDATE_RECORD_TIME, 500);
                    }
                    break;
                case ACTION_FINISH_RECORD_AUDIO:
                    tvTime.setText(formatTime(0));
                    frequencyView.stopResponse();
                    break;
                case PLAY_STATE_PLAYING:
                    playerPassTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
                    break;
            }
        }
    };
    private boolean isUserSeekingBar=false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_audio;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        ButterKnife.bind(this);
        mTimer=new Timer();
        mTimerTask=new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null && currentPlayState == PLAY_STATE_PLAYING) {
                    if (!isUserSeekingBar) {
                        rPlayerSbProgress.setProgress(mediaPlayer.getCurrentPosition());
                        handler.sendEmptyMessage(PLAY_STATE_PLAYING);
                    }

                }
            }
        };

    }


    /**
     * 初始化录音器
     * @param filePath
     */
    private void initAndStartMediaRecorder(String filePath) {

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.reset();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(filePath);
            mediaRecorder.prepare();
            mediaRecorder.start();
            audioRecordStartTime = System.currentTimeMillis();
            currentRecordState = RECORD_STATE_RECORDING;
            handler.sendEmptyMessage(ACTION_START_RECORD_AUDIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 释放媒体播放器资源
    private void stopMediaRecorder() {
        if (mediaRecorder != null) {
            if (currentRecordState == RECORD_STATE_RECORDING) {
                mediaRecorder.stop();
            }
            mediaRecorder.release();
            currentRecordState = RECORD_STATE_STOP;
            mediaRecorder = null;
        }
    }

    // 初始化媒体播放器
    private void initMediaPlayer(String file) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setLooping(false);
        try {
            mediaPlayer.setDataSource(file);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            currentPlayState = PLAY_STATE_PREPARED;
        } catch (Exception e) {
            Log.e(AudioActivity.class.getSimpleName(),"初始化播放器出错："+e.getLocalizedMessage());
        }
    }

    // 启动媒体播放器
    private void startMediaPlayer() {
        if (mediaPlayer != null
                && (currentPlayState == PLAY_STATE_PAUSE || currentPlayState == PLAY_STATE_PREPARED || currentPlayState == PLAY_STATE_PLAY_COMPLETED)) {
            mediaPlayer.start();
            currentPlayState = PLAY_STATE_PLAYING;

        }
    }
    //暂停媒体播放器
    private void pauseMediaPlayer() {
        if (mediaPlayer != null && currentPlayState == PLAY_STATE_PLAYING) {
            mediaPlayer.pause();
            currentPlayState = PLAY_STATE_PAUSE;

        }
    }

    // 释放媒体播放器占用的资源
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (currentPlayState == PLAY_STATE_PLAYING || currentPlayState == PLAY_STATE_PAUSE
                    || currentPlayState == PLAY_STATE_PLAY_COMPLETED) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            currentPlayState = PLAY_STATE_RELEASED;
            handler.sendEmptyMessage(ACTION_FINISH_PLAY_AUDIO);
            mediaPlayer = null;
        }
    }

    @Override
    protected void onEvent() {
        rPlayerSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    playerPassTime.setText(formatTime(i));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isUserSeekingBar = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(rPlayerSbProgress.getProgress());
                    rPlayerSbProgress.setProgress(mediaPlayer.getCurrentPosition());
                }
                isUserSeekingBar = false;
            }
        });
    }

    @Override
    protected BaseView getView() {
        return null;
    }


   

    @OnClick({R.id.back, R.id.recorder_play, R.id.recorder_btn_record, R.id.recorder_use})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.recorder_play:
                switch (currentPlayState) {
                    case PLAY_STATE_PREPARED:
                    case PLAY_STATE_PAUSE:
                    case PLAY_STATE_PLAY_COMPLETED:
                        if(mediaPlayer!=null){
                            final int playTotalTime = mediaPlayer.getDuration();
                            rPlayerSbProgress.setMax(playTotalTime);
                            rPlayerSbProgress.setProgress(0);
                            playerTotalTime.setText(formatTime(playTotalTime));
                            playerPassTime.setText(formatTime(0));
                            startMediaPlayer();
                            mTimer.schedule(mTimerTask, 0, 10);
                            frequencyView.startResponse();
                        }
                        break;
                    case PLAY_STATE_PLAYING:
                        pauseMediaPlayer();
                        frequencyView.stopResponse();
                        break;
                }

                break;
            case R.id.recorder_btn_record:
                AudioActivityPermissionsDispatcher.openStorageWithPermissionCheck(this);
                break;
            case R.id.recorder_use:
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        currentPlayState = PLAY_STATE_PLAY_COMPLETED;
        frequencyView.stopResponse();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        toastShort("播放器播放出错了");
        frequencyView.stopResponse();
        return false;
    }

    /**
     * 创建存放文件的路径
     * @return
     */
    private boolean ensureFolderCreated() {
        boolean isCreated = false;
        try {
            String  path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/Andy/" + getPackageName() + "/Record";
            File file=new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            recordFolder=file;
            isCreated=true;
        } catch (Exception e) {
            e.printStackTrace();
            isCreated=false;
            Log.i(AudioActivity.class.getSimpleName(),"创建音频地址异常："+e.getMessage());
        }
        return isCreated;
    }
    //type: 2 对应的是mp3格式，其它数字对应amr格式
    private String getAudioFileSuffixByType(int type) {
        if (type != 2) {
            return ".amr";
        }
        return ".mp3";
    }
    private String formatDateToFileName(long milliSeconds, int type) {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
        return sdf.format(new Date(milliSeconds)) + getAudioFileSuffixByType(type);
    }

    private String formatStringToFileName(String fileName, int type) {
        return fileName + getAudioFileSuffixByType(type);
    }

   protected void start(){
       Observable observable=Observable.create(new Observable.OnSubscribe<Boolean>() {
           @Override
           public void call(Subscriber<? super Boolean> subscriber) {
               releaseMediaPlayer();
               subscriber.onNext(true);
               subscriber.onCompleted();
           }
       });
       observable.subscribeOn(Schedulers.newThread())
               .unsubscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<Boolean>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {
                             Log.i(AudioActivity.class.getSimpleName(),"onNext()==>抛出的异常："+e.getLocalizedMessage());
                   }

                   @Override
                   public void onNext(Boolean aBoolean) {
                       if(aBoolean){
                           switch (currentRecordState){
                               case RECORD_STATE_INIT:
                               case RECORD_STATE_STOP:
                                   if (ensureFolderCreated()) {
                                       if (FileHelper.getSDcardFreeSpace() > 1048576L) {// 大于1MB
                                           String fileName = "test";
                                           int type =2;
                                           if(fileName == null || "".equals(fileName)) {
                                               currentRecordFile = new File(recordFolder, formatDateToFileName(System.currentTimeMillis(), type));
                                           }else {
                                               currentRecordFile = new File(recordFolder, formatStringToFileName(fileName, type));
                                           }
                                           Log.i(AudioActivity.class.getSimpleName(),"音频地址："+currentRecordFile.getAbsolutePath());
                                           initAndStartMediaRecorder(currentRecordFile.getAbsolutePath());
                                       } else {
                                           toastShort("sdcard内存不足");
                                       }
                                   } else {
                                       toastShort("创建音频文件地址错误");
                                   }
                                   break;
                               case RECORD_STATE_RECORDING:
                                   stop();
                                   break;
                           }



                       }
                   }
               });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    protected void stop(){
        Observable observable=Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                stopMediaRecorder();
                subscriber.onNext(true);
                subscriber.onCompleted();
                initMediaPlayer(currentRecordFile.getAbsolutePath());
            }
        });
        observable.subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(aBoolean) {
                            playerPassTime.setText(formatTime(0));
                            audioRecordStartTime = 0;
                            frequencyView.stopResponse();
                        }
                    }
                });
    }

    private String formatTime(int ms) {
        if (ms >= 0) {
            final int totalSeconds = ms / 1000;
            final int hours = totalSeconds / 3600;
            final int minutes = (totalSeconds % 3600) / 60;
            final int second = ((totalSeconds % 3600) % 60);
            StringBuffer sb = new StringBuffer();
            if (hours <= 10) {
                sb.append("0");
            }
            sb.append(hours).append(":");
            if (minutes < 10) {
                sb.append("0");
            }
            sb.append(minutes).append(":");
            if (second < 10) {
                sb.append("0");
            }
            sb.append(second);
            return sb.toString();
        }
        return "";
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO})
    void openStorage() {
        start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AudioActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO})
    void onOpenShow(final PermissionRequest request) {
        toastShort("音频需要你内存卡的权限");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mmediaPlayer, int i) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}
