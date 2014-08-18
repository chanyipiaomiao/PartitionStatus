package com.linux178;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.Velocity;

class ResultToHtml implements WriteResult{


    private static String result_file = "result/";

    public static void setResult_file(String result_file) {
        ResultToHtml.result_file = result_file;
    }

    @Override
    public String writeResult(ArrayList<String> result_list) {

        HashMap<String,PartitionInfo> normal_result = new HashMap<String, PartitionInfo>();
        HashMap<String,PartitionInfo> warn_result = new HashMap<String, PartitionInfo>();
        HashMap<String,PartitionInfo> error_result = new HashMap<String, PartitionInfo>();
        for (String item : result_list){
            String[] item_array  = item.split("\\s+");
            String percent = item_array[5];
            int percent_int = Integer.parseInt(percent.substring(0,percent.indexOf("%")));
            if (percent_int >= 90) {
                error_result.put(item_array[0], ArrayToPartitionInfo.arrayToPartition(item_array));
            } else if (percent_int >=80 && percent_int < 90){
                warn_result.put(item_array[0], ArrayToPartitionInfo.arrayToPartition(item_array));
            } else {
                normal_result.put(item_array[0],ArrayToPartitionInfo.arrayToPartition(item_array));
            }
        }

        String result_html = "";
        try {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(Velocity.INPUT_ENCODING,"UTF-8");
            ve.setProperty(Velocity.OUTPUT_ENCODING,"UTF-8");

            ve.init();
            Template template = ve.getTemplate("conf/template.html");
            VelocityContext context = new VelocityContext();
            context.put("error_result",error_result);
            context.put("warn_result",warn_result);
            context.put("normal_result",normal_result);
            context.put("date", FunctionKit.getDate("yyyy-MM-dd"));

            String date = FunctionKit.getDate("yyyy-MM-dd_HHmmss");
            result_html = result_file + date + ".html";
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(new File(result_html)),"UTF-8"));
            if (template != null){
                template.merge(context,writer);
            }
            writer.flush();
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return result_html;
    }
}
