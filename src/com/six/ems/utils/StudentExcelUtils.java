package com.six.ems.utils;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.util.Iterator;

import com.jll.jdbc.tools.AdvanceUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.six.ems.entity.tables.Student;

/**
 * 导入学生信息
 *
 * @author Direct
 */
public class StudentExcelUtils {
    /**
     * 定义把excel文档中内容导入到数据库
     *
     * @param name
     * @param in
     * @return
     */
    public static String importFile(String name, InputStream in, Class<?> clazz) {
        int flag = 0;
        // 判断上传文件的模板是2003还是2007版本
        // "dxxx.xls"
        String filePath = name.replace("\"", "").toLowerCase();
        // 定义工作簿对象句柄
        Workbook wb = null;
        try {
            if (filePath.endsWith(".xls")) {
                wb = new HSSFWorkbook(in);
            } else if (filePath.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(in);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取工作簿中所有工作表
        int numberOfSheets = wb.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = wb.getSheetAt(i);
            System.out.println(sheet.getSheetName());
        }

        // 获取工作簿中的第一个工作表
        Sheet sheet = wb.getSheetAt(0);
        // 获取总共行数
        int firstRowNumber = sheet.getFirstRowNum();
        int endRowNumber = sheet.getLastRowNum();
        System.out.println(firstRowNumber + "-" + endRowNumber);
        // 获取第一行的值(表头)
        Row header = sheet.getRow(firstRowNumber);

        Integer stuClassId = null;
        // 遍历excel中有效行
        for (int i = firstRowNumber + 1; i <= endRowNumber; i++) {
            // 获取当前遍历行
            Row row = sheet.getRow(i);
            // 把当前遍历的行转换为一个单元格的迭代器对象
            Iterator<Cell> cellIterator = row.cellIterator();
            try {
                // 创建一个学生对象
                Student instance = (Student) clazz.newInstance();
                // 遍历迭代器
                while (cellIterator.hasNext()) {
                    // 获取每一个单元格对象
                    Cell cell = cellIterator.next();
                    String fieldName = (getCellValue(header.getCell(cell.getColumnIndex()))).toString();
                    Object value = getCellValue(cell);
                   if (fieldName.equalsIgnoreCase("stuClass")){
                       stuClassId = Integer.valueOf(String.valueOf(value));
                       continue;
                   }
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, Student.class);
                    Class<?> type = pd.getReadMethod().getReturnType();


                    if (type == int.class) {
                        pd.getWriteMethod().invoke(instance, (int) value);
                    }

                    if (type == float.class) {
                        pd.getWriteMethod().invoke(instance, (float) value);
                    }

                    if (type == double.class) {
                        pd.getWriteMethod().invoke(instance, (double) value);
                    }

                    if (type == String.class) {
                        pd.getWriteMethod().invoke(instance, String.valueOf(value));
                    }

                    if (type == Integer.class) {
                        pd.getWriteMethod().invoke(instance, Integer.valueOf(String.valueOf(value)));
                    }
                }

                // 持久化到数据库中
                flag = AdvanceUtil.insert(instance);
                // 给学生批量赋权
                if (flag > 0) {
                    // 获取学生对象账号查询
                    InsertUser.InserttStudents(instance ,stuClassId);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag > 0 ? "OK" : "NO";
    }

    /**
     * 转换单元格格式的方法
     */
    private static Object getCellValue(Cell cell) {
        Object value = null;
        if (cell == null) {
            return value;
        }
        // 获取单元格中值得类型
        CellType type = cell.getCellTypeEnum();

        switch (type) {
            case NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return value;
    }
}
