package com.example.administrator.androidtestdemo.chain;

import android.util.Log;

public class Request {
    final String name;
    final String reason;
    final int days;
    final String groupLeaderInfo;
    final String managerInfo;
    final String departmentHeaderInfo;
    final String customInfo;

    public Request() {
        this(new Builder());
    }

    public Request(Builder builder) {
        this.name = builder.name;
        this.reason = builder. reason;
        this.days =  builder.days;
        this.groupLeaderInfo = builder. groupLeaderInfo;
        this.managerInfo =  builder.managerInfo;
        this.departmentHeaderInfo =  builder.departmentHeaderInfo;
        this.customInfo =  builder.customInfo;
    }


    public String name() {
        return name;
    }

    public String reason() {
        return reason;
    }

    public int days() {
        return days;
    }

    public String groupLeaderInfo() {
        return groupLeaderInfo;
    }

    public String managerInfo() {
        return managerInfo;
    }

    public String departmentHeaderInfo() {
        return departmentHeaderInfo;
    }

    public String customInfo() {
        return customInfo;
    }


    public static final class Builder {
         String name;
         String reason;
         int days;
         String groupLeaderInfo;
         String managerInfo;
         String departmentHeaderInfo;
         String customInfo;

        public Builder(Request request) {
            this.name = request.name;
            this.reason = request. reason;
            this.days =  request.days;
            this.groupLeaderInfo = request. groupLeaderInfo;
            this.managerInfo =  request.managerInfo;
            this.departmentHeaderInfo =  request.departmentHeaderInfo;
            this.customInfo =  request.customInfo;
        }
        public Builder() {
            name="VanHua";
            reason="华仔";
            days=1080;
            groupLeaderInfo="VanHua";
            managerInfo="VanHua";
            departmentHeaderInfo="VanHua";
            customInfo="VanHua";
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder days(int days) {
            this.days = days;
            return this;
        }

        public Builder groupLeaderInfo(String groupLeaderInfo) {
            this.groupLeaderInfo = groupLeaderInfo;
            return this;
        }

        public Builder managerInfo(String managerInfo) {
            this.managerInfo = managerInfo;
            return this;
        }

        public Builder departmentHeaderInfo(String departmentHeaderInfo) {
            this.departmentHeaderInfo = departmentHeaderInfo;
            return this;
        }

        public Builder customInfo(String customInfo) {
            this.customInfo = customInfo;
            return this;
        }


        public Request build() {
            return new Request(this);
        }
    }

    public void  show(){
        Request request=new Builder()
                .name("VanHua")
                .reason("VanHua")
                .days(1080)
                .groupLeaderInfo("VanHua")
                .managerInfo("VanHua")
                .departmentHeaderInfo("VanHua")
                .customInfo("VanHua")
                .build();
        Log.e("CHAIN",request.toString());
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                ", days=" + days +
                ", groupLeaderInfo='" + groupLeaderInfo + '\'' +
                ", managerInfo='" + managerInfo + '\'' +
                ", departmentHeaderInfo='" + departmentHeaderInfo + '\'' +
                ", customInfo='" + customInfo + '\'' +
                '}';
    }
}
