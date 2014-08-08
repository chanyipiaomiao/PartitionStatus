package com.linux178;


import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

class MutilThread {

    public static void startMutilThread(ArrayList<String> ip_list){

        //文本文件的行数，也就是Excel行数/线程的数量
        int size = ip_list.size();

        //初始化一个同步计数器，用于控制主线程和子线程
        CountDownLatch wait_thread_run_end = new CountDownLatch(size);
        ConnectServer.setCountDownLatch(wait_thread_run_end);

        //初始化线程对象并放入ArrayList
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        for (String ip : ip_list){
            threadArrayList.add(new ConnectServer(ip));
        }

        //开始执行多线程
        for (Thread thread : threadArrayList){
            thread.start();
        }

        //等待所有子线程运行完毕
        try {
            wait_thread_run_end.await();
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
