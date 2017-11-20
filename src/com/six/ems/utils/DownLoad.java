package com.six.ems.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DownLoad {

    public static HSSFWorkbook getHSSFWorkbook(String sheetName,  List<String> title, List<Map<String, Object>> values){
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook  wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = null;
        //创建标题
        for(int i=0;i<title.size();i++){
            cell = row.createCell(i);
            cell.setCellValue(title.get(i));
            cell.setCellStyle(style);
        }
        //创建内容
        for(int i=0;i<values.size();i++){
            row = sheet.createRow(i + 1);
            Map<String, Object> map = values.get(i);
            Set<String> keySet = map.keySet();
           int j =0;
            /**
             stu.stu_id, stu.stu_real_name, score.auto_score, score.sub_score,score.total_score
             */
            row.createCell(j++).setCellValue(map.get("stu_id").toString());
            row.createCell(j++).setCellValue(map.get("stu_real_name").toString());
            row.createCell(j++).setCellValue(map.get("auto_score").toString());
            row.createCell(j++).setCellValue(map.get("sub_score").toString());
            row.createCell(j++).setCellValue(map.get("total_score").toString());
        }
        return wb;
    }
}