package com.linux178;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

class GetConfigure {

    /**
     * 从properties文件中读取信息
     * */
     public static Properties getConfigureFromPropertiesFile(){
         Properties properties = new Properties();
         try {
             FileInputStream fileInputStream = new FileInputStream("conf/configure.properties");
             properties.load(fileInputStream);
             fileInputStream.close();
         } catch (FileNotFoundException e){
             System.out.println(e.getMessage());
         } catch (IOException e){
             System.out.println(e.getMessage());
         }
         return properties;
     }
}
