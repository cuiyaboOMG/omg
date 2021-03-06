package com.omg.util;

import com.google.common.collect.Lists;
import com.omg.annotation.FieldValue;
import com.omg.annotation.Repetition;
import com.omg.util.excel.ExcelInfo;
import com.omg.util.excel.ImportResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.common.Mapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel通用数据封装和重复性校验
 * Created by gp-0096 on 2018/8/24.
 */
public class ExcelUtils {
    private static NumberFormat numberFormat = NumberFormat.getInstance();

    public static <T> ImportResult importExcel(Class<T> clazz,Mapper<T> mapper,MultipartFile multipartFile) throws InstantiationException, IllegalAccessException, NoSuchFieldException, ParseException {
        Workbook workbook = getWorkbook(multipartFile);
        List<T> data = importExcel(clazz, workbook);
        ImportResult validate = validate(clazz, data, mapper);
        return validate;
    }

    public static <T> ImportResult importExcel(Class<T> clazz,MultipartFile multipartFile) throws InstantiationException, IllegalAccessException, ParseException {
        Workbook workbook = getWorkbook(multipartFile);
        List<T> data = importExcel(clazz, workbook);
        ImportResult importResult = new ImportResult();
        importResult.setData(data);
        return importResult;
    }

    private static Workbook getWorkbook(MultipartFile file) {
        String filePath = file.getOriginalFilename();
        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        InputStream stream;
        Workbook wb = null;
        try {
            stream = file.getInputStream();
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(stream);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(stream);
            } else {
                throw new RuntimeException("文件类型错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static <T> List<T> importExcel(Class<T> clazz,Workbook workbook) throws IllegalAccessException, InstantiationException, ParseException {
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
                String contents = getCellValue(cell);
                for (Field field:fields) {
                    if(field.isAnnotationPresent(FieldValue.class)){
                        FieldValue annotation = field.getAnnotation(FieldValue.class);
                        String value = annotation.value();
                        if(fileTitle.get(j).equals(value)){
                            setField(t, contents, field);
                            continue;
                        }
                    }
                }
            }
            list.add(t);
        }

        return list;
    }

    private static String getCellValue(Cell cell){
        if(cell==null){
            return "";
        }
        if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }
        if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
            }
            //去除千位符之类的
            numberFormat.setGroupingUsed(false);
            return numberFormat.format(cell.getNumericCellValue());
        }
        return String.valueOf(cell.getStringCellValue());
    }

    /**通过注解Repetition校验重复性*/
    private static <T> ImportResult validate(Class<T> clazz,List<T> data,Mapper<T> mapper) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        ImportResult importResult = new ImportResult();
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        Boolean needRepetition = false;
        List<String> repetitionName = Lists.newArrayList();
        StringBuffer errorMessage = new StringBuffer();
        for (Field field:fields){
            if(field.isAnnotationPresent(Repetition.class)){
                needRepetition = true;
                Repetition annotation = field.getAnnotation(Repetition.class);
                errorMessage.append(annotation.message());
                repetitionName.add(field.getName());
            }
        }
        if(!needRepetition){
            return importResult;
        }
        //1.重复名  2.mapper 3.map
        HashMap<String,Object> map = new HashMap();
        /**获取数据库中原有的数据查出*/
        List<T> allInfo = mapper.selectAll();
        for(T info:allInfo){
            Class<?> aClass = info.getClass();
            StringBuffer keyStr = new StringBuffer();
            buildKey(repetitionName, info, aClass, keyStr);
            map.put(keyStr.substring(0,keyStr.length()-1),info);
        }
        List<T> repetitionList = Lists.newArrayList();
        for (T t:data) {
            Class<?> aClass = t.getClass();
            StringBuffer keyStr = new StringBuffer();
            buildKey(repetitionName, t, aClass, keyStr);
            if(map.containsKey(keyStr.substring(0,keyStr.length()-1))){
                repetitionList.add(t);
            }
        }
        if(CollectionUtils.isNotEmpty(repetitionList)){
            importResult.setRepetitionData(repetitionList);
            importResult.setStatus(false);
        }
        importResult.setData(data);
        return importResult;
    }

    private static <T> void buildKey(List<String> repetitionName, T info, Class<?> aClass, StringBuffer keyStr) throws NoSuchFieldException, IllegalAccessException {
        for(String rep:repetitionName){
            Field declaredField = aClass.getDeclaredField(rep);
            declaredField.setAccessible(true);
            Object key = declaredField.get(info);
            keyStr.append(key.toString());
            keyStr.append("_");
        }
    }

    private static <T> void setField(T t, String contents, Field field) throws IllegalAccessException, ParseException {
        field.setAccessible(true);
        String type = field.getType().getSimpleName();
        if("String".equals(type)){
            field.set(t, contents);
        }
        if("int".equals(type)||"Integer".equals(type)){
            field.set(t,Integer.valueOf(contents));
        }
        if("Date".equals(type)){
            field.set(t, org.apache.commons.lang3.time.DateUtils.parseDate(contents,"yyyy-MM-dd hh:mm:ss"));
        }
        if("BigDecimal".equals(type)){
            field.set(t, new BigDecimal(contents));
        }
    }


    public static Workbook writtenInfoToExcel(List<ExcelInfo> errorExcelInfos, MultipartFile file) {
        Workbook workbook = getWorkbook(file);
        Sheet sheet1 = workbook.getSheetAt(0);
        Drawing drawingPatriarch = sheet1.createDrawingPatriarch();
        Font commentFormatter = workbook.createFont();
        commentFormatter.setFontName("宋体");
        commentFormatter.setFontHeightInPoints((short) 9);
        for (ExcelInfo info:errorExcelInfos) {
            //设置批注
            Comment comment = null;
            if(workbook instanceof HSSFWorkbook){
                HSSFRichTextString rtf = new HSSFRichTextString(info.getErrorMessage());
                rtf.applyFont(commentFormatter);
                comment = drawingPatriarch.createCellComment(new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)7, 7));
                comment.setString(rtf);
                comment.setAuthor("错误");

            }
            if(workbook instanceof XSSFWorkbook){
                XSSFRichTextString rtf = new XSSFRichTextString(info.getErrorMessage());
                rtf.applyFont(commentFormatter);
                comment = drawingPatriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 4, 2, 7, 7));
                comment.setString(rtf);
                comment.setAuthor("错误");
            }
            Row row = sheet1.getRow(info.getRowNum()-1);
            if(row==null){
                continue;
            }
            Cell cell = row.getCell(info.getLineNum());
            if(cell==null){
                continue;
            }
            cell.removeCellComment();
            cell.setCellComment(comment);
            //设置背景色
            CellStyle cellStyle =workbook.createCellStyle();
            cellStyle.cloneStyleFrom(cell.getCellStyle());
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(cellStyle);
        }
        return workbook;
    }
}
