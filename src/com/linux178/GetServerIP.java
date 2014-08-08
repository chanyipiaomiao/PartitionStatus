package com.linux178;


import java.io.*;
import java.util.ArrayList;

class GetServerIP {

    private static String ip_file = "conf/servers_ip.txt";

    public static void setIp_file(String ip_file) {
        GetServerIP.ip_file = ip_file;
    }

    public static ArrayList<String> getServerIPFromFile(){
        File file = new File(ip_file);
        BufferedReader reader = null;
        ArrayList<String> ip_list = new ArrayList<String>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null){
                ip_list.add(line);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        return ip_list;
    }
}
