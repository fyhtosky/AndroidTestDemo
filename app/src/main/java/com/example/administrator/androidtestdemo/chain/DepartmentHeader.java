package com.example.administrator.androidtestdemo.chain;

public class DepartmentHeader implements Call {
    @Override
    public Result deal(Chain chain) {
        Request request = chain.request();
        System.out.println("DepartmentHeader=====>request:"+ request.toString());
        if (request.days() > 7) {
            return new Result(false, "你这个完全没必要");
        }
        return new Result(true, "DepartmentHeader：不要着急，把事情处理完再回来！");


    }
}
