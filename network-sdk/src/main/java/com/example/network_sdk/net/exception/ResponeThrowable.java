package com.example.network_sdk.net.exception;

public class ResponeThrowable  extends Exception{
    public int code;
    public String message;

    public ResponeThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
