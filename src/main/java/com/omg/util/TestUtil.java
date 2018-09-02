package com.omg.util;

import com.omg.annotation.FieldValue;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by gp-0096 on 2018/8/24.
 */
public class TestUtil {

    public static <T> List<T> importExcel(Class<T> clazz,Workbook workbook) throws IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();
        Sheet sheet1 = workbook.getSheetAt(0);
        int coloumNum=sheet1.getRow(0).getPhysicalNumberOfCells();
        int rowNum=sheet1.getLastRowNum()+1;//获得总行数 /getLastRowNum 最后一行行标，比行数小1
        LinkedHashMap<Integer, String> fileTitle = new LinkedHashMap<>();
        for(int i=0;i<coloumNum;i++) {
            Row row = sheet1.getRow(0);
            Cell cell = row.getCell(i);
            String contents = cell.getStringCellValue();
            if(StringUtils.isNotBlank(contents)){
                fileTitle.put(i, contents);
            }
        }
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        for(int i=0;i<rowNum;i++) {
            //title跳过
            if(i==0){
                continue;
            }
            T t = clazz.newInstance();
            for(int j=0;j<coloumNum;j++){
                Row row = sheet1.getRow(i);
                Cell cell = row.getCell(j);
                String contents = cell==null?null:cell.getStringCellValue();
                for (Field field:fields) {
                    if(field.isAnnotationPresent(FieldValue.class)){
                        FieldValue annotation = field.getAnnotation(FieldValue.class);
                        String value = annotation.value();
                        if(fileTitle.get(j).equals(value)){
                            Class<?> type = field.getType();
                            field.setAccessible(true);
                            field.set(t, contents);
                            continue;
                        }
                    }
                }
            }
            list.add(t);
        }
        return list;
    }
}
