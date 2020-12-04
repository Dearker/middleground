package com.hanyi.web.controller;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.excel.EasyExcel;
import com.hanyi.web.bo.Student;
import com.hanyi.web.common.listener.ExcelListener;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出控制层
 *
 * @author wcwei@iflytek.com
 * @since 2020-12-04 10:38
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    /**
     * 下载模板
     *
     * @param response 返回体
     * @throws Exception 异常
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("excelTemplate/easyexcel.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        Workbook workbook = new HSSFWorkbook(inputStream);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("easyexcel.xlsx", CharsetUtil.UTF_8));
        response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 导出数据
     */
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();

        String[] columnNames = {"用户名", "年龄", "手机号", "性别"};

        Sheet sheet = workbook.createSheet();
        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        titleStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(0);

        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(titleStyle);
        }
        //模拟构造数据
        List<Student> dataList = new ArrayList<>();
        dataList.add(new Student(1, "张三"));
        dataList.add(new Student(2, "张三1"));
        dataList.add(new Student(3, "张三2"));
        dataList.add(new Student(4, "张三3"));

        //创建数据行并写入值
        for (Student student : dataList) {
            int lastRowNum = sheet.getLastRowNum();
            Row dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(student.getName());
            dataRow.createCell(1).setCellValue(student.getNumber());
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("easyexcel.xls", CharsetUtil.UTF_8));
        response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 读取excel文件内容
     *
     * @param file 文件
     * @return 返回文件中集合
     */
    @PostMapping("/readExcel")
    public List<Student> readExcel(@RequestParam("file") MultipartFile file) {
        List<Student> list = new ArrayList<>();
        try {
            list = EasyExcel.read(file.getInputStream(), Student.class, new ExcelListener()).sheet().doReadSync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
