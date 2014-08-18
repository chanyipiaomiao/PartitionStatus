package com.linux178;


import jxl.Workbook;
import jxl.write.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



class ResultToExcel implements WriteResult {

    private static String result_file = "result/";
    private static int warn_percent = 90;
    private static String content_title = "Partition Status";


    public static void setResult_file(String result_file) {
        ResultToExcel.result_file = result_file;
    }

    public static void setContent_title(String content_title) {
        ResultToExcel.content_title = content_title;
    }

    public static void setWarn_percent(int warn_percent) {
        ResultToExcel.warn_percent = warn_percent;
    }


    /**
     * 把得到到ArrayList写入到Excel文件中去
     * */
    public String writeResult(ArrayList<String> result_list){

        String date = FunctionKit.getDate("yyyy-MM-dd_HH:mm:ss");
        String file_date = date.replaceAll(":","");
        String file_name = result_file + file_date + ".xls";
        File file = new File(file_name);
        String title = date + " " + content_title;

        try {

            //创建一个文件
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);

            //在最前面新建一个sheet
            WritableSheet sheet = writableWorkbook.createSheet(file_date,-1);

            //设置第1列和第6列的列宽为15
            sheet.setColumnView(0,15);
            sheet.setColumnView(3,15);
            sheet.setColumnView(5,15);

            //设置标题的字体 大小 加粗
            WritableCellFormat title_format = new WritableCellFormat(new WritableFont(WritableFont.TIMES,16,WritableFont.BOLD));

            //设置标题水平方向居中对齐
            title_format.setAlignment(jxl.format.Alignment.CENTRE);

            //设置标题垂直方向居中对齐
            title_format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

            //增加标题内容
            sheet.addCell(new Label(0,0,title,title_format));

            //第一行 至 第二行 合并
            sheet.mergeCells(0,0,5,1);

            //设置当百分比大于90%时单元格的背景为红色以示提醒
            WritableCellFormat percent_cell_format = new WritableCellFormat();
            percent_cell_format.setBackground(jxl.format.Colour.RED);

            sheet.addCell(new Label(0,2,"IP地址"));
            sheet.addCell(new Label(1,2,"挂载点"));
            sheet.addCell(new Label(2,2,"总大小"));
            sheet.addCell(new Label(3,2,"已使用大小"));
            sheet.addCell(new Label(4,2,"剩余大小"));
            sheet.addCell(new Label(5,2,"已使用百分比"));

            //循环添加单元格内容
            int current_row = 3;
            for (String result : result_list){
                String[] result_array  = result.split("\\s+");
                for (int col = 0; col <=5 ; col++) {
                    if (col == 5){
                        String percent = result_array[col];
                        int percent_int = Integer.parseInt(percent.substring(0,percent.indexOf("%")));
                        if (percent_int > warn_percent){
                            sheet.addCell(new Label(col,current_row,result_array[col],percent_cell_format));
                        } else {
                            sheet.addCell(new Label(col,current_row,result_array[col]));
                        }
                    } else {
                        sheet.addCell(new Label(col,current_row,result_array[col]));
                    }
                }
                current_row++;
            }
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (WriteException e){
            System.out.println(e.getMessage());
        }
        return file_name;
    }
}
