package com.example.administrator.androidtestdemo.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.androidtestdemo.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrescoDemoActivity extends AppCompatActivity {

    @BindView(R.id.drawee_1)
    SimpleDraweeView drawee1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_demo);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
//        GenericDraweeHierarchyBuilder builder =
//                new GenericDraweeHierarchyBuilder(getResources());
//        GenericDraweeHierarchy hierarchy = builder
//                .setFadeDuration(300)
//                .build();
//        drawee1.setHierarchy(hierarchy);
        Uri uri = Uri.parse("http://ww2.sinaimg.cn/large/7a8aed7bgw1es8c7ucr0rj20hs0qowhl.jpg");



        Postprocessor redMeshPostprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "redMeshPostprocessor";
            }

//            @Override
//            public void process(Bitmap bitmap) {
//                for (int x = 0; x < bitmap.getWidth(); x+=2) {
//                    for (int y = 0; y < bitmap.getHeight(); y+=2) {
//                        bitmap.setPixel(x, y, Color.RED);
//                    }
//                }
//            }

            @Override
            public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
                for (int x = 0; x < destBitmap.getWidth(); x++) {
                    for (int y = 0; y < destBitmap.getHeight(); y++) {
                        destBitmap.setPixel(destBitmap.getWidth() - x, y, sourceBitmap.getPixel(x, y));
                    }
                }
            }
        };

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(redMeshPostprocessor)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController)
                Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
//                        .setOldController(drawee1.getController())
                        // other setters as you need
                        .build();
        drawee1.setController(controller);

//        Uri uri = Uri.parse("res://com.example.administrator.androidtestdemo/"+R.mipmap.back);
//        drawee1.setImageURI(uri);
    }
}

