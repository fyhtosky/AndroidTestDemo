package com.example.administrator.androidtestdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Test implements Parcelable {


    /**
     * lyricStartTime : 7.56
     * lyricEndTime : 8.49
     * lyricChineseText :
     * lyricEnglishText : Goodmorning.
     * lyricUuidProperty : b1131082f4f8486fa70cd6c1ad5aa899
     * recordAble : true
     */

    private String lyricStartTime;
    private String lyricEndTime;
    private String lyricChineseText;
    private String lyricEnglishText;
    private String lyricUuidProperty;
    private String recordAble;

    public String getLyricStartTime() {
        return lyricStartTime;
    }

    public void setLyricStartTime(String lyricStartTime) {
        this.lyricStartTime = lyricStartTime;
    }

    public String getLyricEndTime() {
        return lyricEndTime;
    }

    public void setLyricEndTime(String lyricEndTime) {
        this.lyricEndTime = lyricEndTime;
    }

    public String getLyricChineseText() {
        return lyricChineseText;
    }

    public void setLyricChineseText(String lyricChineseText) {
        this.lyricChineseText = lyricChineseText;
    }

    public String getLyricEnglishText() {
        return lyricEnglishText;
    }

    public void setLyricEnglishText(String lyricEnglishText) {
        this.lyricEnglishText = lyricEnglishText;
    }

    public String getLyricUuidProperty() {
        return lyricUuidProperty;
    }

    public void setLyricUuidProperty(String lyricUuidProperty) {
        this.lyricUuidProperty = lyricUuidProperty;
    }

    public String getRecordAble() {
        return recordAble;
    }

    public void setRecordAble(String recordAble) {
        this.recordAble = recordAble;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lyricStartTime);
        dest.writeString(this.lyricEndTime);
        dest.writeString(this.lyricChineseText);
        dest.writeString(this.lyricEnglishText);
        dest.writeString(this.lyricUuidProperty);
        dest.writeString(this.recordAble);
    }

    public Test() {
    }

    protected Test(Parcel in) {
        this.lyricStartTime = in.readString();
        this.lyricEndTime = in.readString();
        this.lyricChineseText = in.readString();
        this.lyricEnglishText = in.readString();
        this.lyricUuidProperty = in.readString();
        this.recordAble = in.readString();
    }

    public static final Parcelable.Creator<Test> CREATOR = new Parcelable.Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
