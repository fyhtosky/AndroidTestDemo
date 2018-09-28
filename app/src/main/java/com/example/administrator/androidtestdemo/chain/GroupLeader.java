package com.example.administrator.androidtestdemo.chain;

public class GroupLeader implements Call {
    @Override
    public Result deal(Chain chain) {
        Request request = chain.request();
        System.out.println("GroupLeader=====>request:" + request.toString());
        if (request.days() > 1) {
            // 包装新的Request对象
            Request newRequest = new Request.Builder()
                    .managerInfo(request.name() + "平时表现不错，而且现在项目也不忙")
                    .build();
            return chain.proceed(newRequest);
        }
        return new Result(true, "GroupLeader：早去早回");


    }
}
