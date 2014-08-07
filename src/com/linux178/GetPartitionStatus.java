package com.linux178;


import java.util.ArrayList;
import java.util.Properties;


public class GetPartitionStatus {

    public static void main(String args[]){

        long start_time = System.currentTimeMillis();

        //读取配置文件信息
        Properties properties = GetConfigure.getConfigureFromPropertiesFile();

        //生成Excel或者HTML
        int excel_html = Integer.parseInt(properties.getProperty("excel_html"));

        //从文件中读取IP地址
        GetServerIP.setIp_file(properties.getProperty("ip_file"));
        ArrayList<String> ip_list = GetServerIP.getServerIPFromFile();

        //设置连接信息
        ConnectServer.setUsername(properties.getProperty("username"));
        ConnectServer.setPassword(DecryptPassword.Decrypt(properties.getProperty("password")));
        ConnectServer.setPort(Integer.parseInt(properties.getProperty("port")));
        ConnectServer.setEnable_enable_http_proxy(Integer.parseInt(properties.getProperty("enable_http_proxy")));
        ConnectServer.setHttp_proxy_server(properties.getProperty("http_proxy_server"));
        ConnectServer.setHttp_proxy_port(Integer.parseInt(properties.getProperty("http_proxy_port")));
        ConnectServer.setSleep_time(Integer.parseInt(properties.getProperty("sleep_time")));
        ConnectServer.setCommand(properties.getProperty("command"));


        //开始执行多线程获取结果并写入HTML文件
        ArrayList<String> result_list = new ArrayList<String>();
        ArrayList<String> faild_list = new ArrayList<String>();
        ConnectServer.setResult_list(result_list);
        ConnectServer.setFaild_list(faild_list);
        int ip_list_size = ip_list.size();
        int exec_count = 0;   //执行的次数
        do {

            exec_count++;
            if (result_list.size() != 0){
                result_list.clear();
            }

            MutilThread.startMutilThread(ip_list);
            if (faild_list.size() > 10) {
                System.out.println("看起来token是打开状态或者是网络出现问题了!!!");
                break;
            }

            if (ip_list_size == result_list.size()){
                if (excel_html == 0){
                    WriteResult writeResult = new ResultToHtml();
                    writeResult.writeResult(result_list);
                } else if (excel_html == 1){
                    WriteResult writeResult = new ResultToExcel();
                    writeResult.writeResult(result_list);
                } else {
                    System.out.println("你的配置有误吧!!!");
                }
                break;
            } else {
                if (exec_count > 3){
                    System.out.println("请在配置文件中修改子线程的睡眠时间!!!");
                    break;
                }
                System.out.println("结果不完整，程序会自动再次获取!!!");
            }
        } while (ip_list_size != result_list.size());

        long end_time = System.currentTimeMillis();
        System.out.println("程序运行 "+ (end_time-start_time)/1000 + " 秒");
    }
}
