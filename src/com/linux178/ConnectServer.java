package com.linux178;

import com.jcraft.jsch.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


class ConnectServer extends Thread {

    private String host = null;
    private static CountDownLatch countDownLatch = null;
    private static ArrayList<String> result_list = null;
    private static String username = null;
    private static String password = null;
    private static int port = 22;
    private static int enable_enable_http_proxy = 0;
    private static String http_proxy_server = null;
    private static int http_proxy_port = 80;
    private static int sleep_time = 300;
    private static String command = null;
    private static ArrayList<String> faild_list = null;

    ConnectServer(String host) {
        this.host = host;
    }

    public static void setUsername(String username) {
        ConnectServer.username = username;
    }

    public static void setPassword(String password) {
        ConnectServer.password = password;
    }

    public static void setPort(int port) {
        ConnectServer.port = port;
    }

    public static void setCommand(String command) {
        ConnectServer.command = command;
    }

    public static void setHttp_proxy_server(String http_proxy_server) {
        ConnectServer.http_proxy_server = http_proxy_server;
    }

    public static void setEnable_enable_http_proxy(int enable_enable_http_proxy) {
        ConnectServer.enable_enable_http_proxy = enable_enable_http_proxy;
    }

    public static void setHttp_proxy_port(int http_proxy_port) {
        ConnectServer.http_proxy_port = http_proxy_port;
    }

    public static void setSleep_time(int sleep_time) {
        ConnectServer.sleep_time = sleep_time;
    }

    public static void setCountDownLatch(CountDownLatch countDownLatch) {
        ConnectServer.countDownLatch = countDownLatch;
    }

    public static void setResult_list(ArrayList<String> result_list) {
        ConnectServer.result_list = result_list;
    }

    public static void setFaild_list(ArrayList<String> faild_list) {
        ConnectServer.faild_list = faild_list;
    }

    @Override
    public void run() {
        ArrayList<String> result = execCommandReturnResult();
        int result_len = result.size();
        if ( result_len== 2){
            String tmp_result = FunctionKit.listToString(result);
            result_list.add(tmp_result);
        } else if (result_len == 1){
            System.out.println(result.get(0) + " 未获取到结果!!!");
        }
        countDownLatch.countDown();
    }

    /**
     * 连接远程服务器执行命令
     * */
    public ArrayList<String> execCommandReturnResult(){

        JSch jSch = new JSch();
        ArrayList<String> result = new ArrayList<String>();
        try {
            Session session = jSch.getSession(username,host,port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            if (enable_enable_http_proxy == 1){
                session.setProxy(new ProxyHTTP(http_proxy_server,http_proxy_port));
            }
            session.connect();
            ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
            channelExec.setCommand(command);
            BufferedReader exec_result = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
            channelExec.connect();
            Thread.sleep(sleep_time);
            result.add(host);
            while (exec_result.ready()){
                result.add(exec_result.readLine());
            }
            channelExec.disconnect();
            session.disconnect();
        } catch (Exception e){
            faild_list.add(host);
            System.out.println(host + "... Connect Failed!!!");
        }
        return result;
    }
}
