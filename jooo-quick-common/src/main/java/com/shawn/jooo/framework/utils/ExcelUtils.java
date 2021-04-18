package com.shawn.jooo.framework.utils;

import com.shawn.jooo.framework.excel.annotation.ExcelField;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExcelUtils {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";

    public boolean checkMultiFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            logger.error("上传文件格式不正确");
            return false;
        }
        return true;
    }

    /**
     * 下载excel模板
     *
     * @param request
     * @param response
     * @param excelTemplateName
     * @return
     */
    public static void downloadTemplate(HttpServletRequest request, HttpServletResponse response, String excelTemplateName) {


        try {
            String fileName = URLEncoder.encode(excelTemplateName, "UTF-8");

            OutputStream outputStream = response.getOutputStream();
            //response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            InputStream inputStream = ExcelUtils.class.getResourceAsStream("/excelTemplates/" + excelTemplateName);
            //IOUtils.copy(inputStream, outputStream);

            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = inputStream.read(b, 0, b.length)) != -1) {
                outputStream.write(b, 0, len);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("excel downloadTemplate error", e);
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            logger.error("excel downloadTemplate error", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("excel downloadTemplate error", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> importExcel(InputStream inputStream, Class<T> clazz) {

        List<T> dataList = null;
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            //类映射  注解 value-->bean columns
            Map<String, List<Field>> classMap = new HashMap<>();
            List<Field> fields = Stream.of(clazz.getDeclaredFields()).collect(Collectors.toList());
            fields.forEach(
                    field -> {
                        ExcelField annotation = field.getAnnotation(ExcelField.class);
                        if (annotation != null) {
                            String value = annotation.value();
                            if (StringUtils.isBlank(value)) {
                                return;//return起到的作用和continue是相同的 语法
                            }
                            if (!classMap.containsKey(value)) {
                                classMap.put(value, new ArrayList<>());
                            }
                            field.setAccessible(true);
                            classMap.get(value).add(field);
                        }
                    }
            );

            //默认读取第一个sheet
            Sheet sheet = workbook.getSheetAt(0);
            dataList = readSheet(sheet, classMap, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;

    }


    public static <T> List<T> readSheet(Sheet sheet, Map<String, List<Field>> classMap, Class<T> clazz) {
        //索引-->columns
        List<T> dataList = new ArrayList<>();
        Map<Integer, List<Field>> reflectionMap = new HashMap<>(16);

        boolean firstRow = true;
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            //首行  提取注解
            if (firstRow) {
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = getCellValue(cell);
                    if (classMap.containsKey(cellValue)) {
                        reflectionMap.put(j, classMap.get(cellValue));
                    }
                }
                firstRow = false;
            } else {
                //忽略空白行
                if (row == null) {
                    continue;
                }
                try {
                    T t = clazz.newInstance();
                    //判断是否为空白行
                    boolean allBlank = true;
                    for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                        if (reflectionMap.containsKey(j)) {
                            Cell cell = row.getCell(j);
                            String cellValue = getCellValue(cell);
                            if (StringUtils.isNotBlank(cellValue)) {
                                allBlank = false;
                            }
                            List<Field> fieldList = reflectionMap.get(j);
                            fieldList.forEach(
                                    x -> {
                                        try {
                                            handleField(t, cellValue, x);
                                        } catch (Exception e) {
                                            logger.error(String.format("reflect field:%s value:%s exception!", x.getName(), cellValue), e);
                                        }
                                    }
                            );
                        }
                    }
                    if (!allBlank) {
                        dataList.add(t);
                    } else {
                        logger.warn(String.format("row:%s is blank ignore!", i));
                    }
                } catch (Exception e) {
                    logger.error(String.format("parse row:%s exception!", i), e);
                }
            }
        }
        return dataList;
    }


    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return DateUtil.getJavaDate(cell.getNumericCellValue()).toString();
            } else {
                return new BigDecimal(cell.getNumericCellValue()).toString();
            }
        } else if (cell.getCellType() == CellType.STRING) {
            return StringUtils.trimToEmpty(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            return StringUtils.trimToEmpty(cell.getCellFormula());
        } else if (cell.getCellType() == CellType.BLANK) {
            return "";
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.ERROR) {
            return "ERROR";
        } else {
            return cell.toString().trim();
        }

    }

    private static <T> void handleField(T t, String value, Field field) throws Exception {
        Class<?> type = field.getType();
        if (type == null || type == void.class || StringUtils.isBlank(value)) {
            return;
        }
        if (type == Object.class) {
            field.set(t, value);
            //数字类型
        } else if (type.getSuperclass() == null || type.getSuperclass() == Number.class) {
            if (type == int.class || type == Integer.class) {
                field.set(t, NumberUtils.toInt(value));
            } else if (type == long.class || type == Long.class) {
                field.set(t, NumberUtils.toLong(value));
            } else if (type == byte.class || type == Byte.class) {
                field.set(t, NumberUtils.toByte(value));
            } else if (type == short.class || type == Short.class) {
                field.set(t, NumberUtils.toShort(value));
            } else if (type == double.class || type == Double.class) {
                field.set(t, NumberUtils.toDouble(value));
            } else if (type == float.class || type == Float.class) {
                field.set(t, NumberUtils.toFloat(value));
            } else if (type == char.class || type == Character.class) {
                field.set(t, CharUtils.toChar(value));
            } else if (type == boolean.class) {
                field.set(t, BooleanUtils.toBoolean(value));
            } else if (type == BigDecimal.class) {
                field.set(t, new BigDecimal(value));
            }
        } else if (type == Boolean.class) {
            field.set(t, BooleanUtils.toBoolean(value));
        } else if (type == Date.class) {
            //
            field.set(t, value);
        } else if (type == String.class) {
            field.set(t, value);
        } else {
            Constructor<?> constructor = type.getConstructor(String.class);
            field.set(t, constructor.newInstance(value));
        }
    }
}
