package com.hanyi.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.hanyi.web.bo.Student;
import com.hanyi.web.bo.User;
import com.hanyi.web.bo.UserExcelModel;
import com.hanyi.web.common.constant.ExcelConstant;
import com.hanyi.web.common.listener.ModelExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
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
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {

    /**
     * 下载模板
     *
     * @param response 返回体
     */
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) {
        ClassPathResource classPathResource = new ClassPathResource("excelTemplate/easyexcel.xls");
        try (InputStream inputStream = classPathResource.getInputStream(); OutputStream outputStream = response.getOutputStream()) {
            Workbook workbook = new HSSFWorkbook(inputStream);
            response.setContentType(ExcelConstant.CONTENT_TYPE_EXCEL);
            response.setHeader(ExcelConstant.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode("easyexcel.xls", CharsetUtil.UTF_8));
            response.setHeader("Access-Control-Expose-Headers", ExcelConstant.CONTENT_DISPOSITION);
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }

    /**
     * 导出数据
     */
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {

        String[] columnNames = {"用户名", "年龄", "手机号", "性别"};

        XSSFWorkbook workbook = new XSSFWorkbook();
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
        List<UserExcelModel> dataList = new ArrayList<>();
        dataList.add(new UserExcelModel("张三", 12, "13867098765", "男"));
        dataList.add(new UserExcelModel("张三1", 12, "13867098766", "男"));
        dataList.add(new UserExcelModel("张三2", 12, "13867098767", "男"));
        dataList.add(new UserExcelModel("张三3", 12, "13867098768", "男"));

        //创建数据行并写入值
        for (UserExcelModel userExcelModel : dataList) {
            int lastRowNum = sheet.getLastRowNum();
            Row dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(userExcelModel.getName());
            dataRow.createCell(1).setCellValue(userExcelModel.getAge());
            dataRow.createCell(2).setCellValue(userExcelModel.getMobile());
            dataRow.createCell(3).setCellValue(userExcelModel.getSex());
        }
        try (OutputStream outputStream = response.getOutputStream()) {
            response.setContentType(ExcelConstant.CONTENT_TYPE_EXCEL);
            response.setHeader(ExcelConstant.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode("easyexcel.xls", CharsetUtil.UTF_8));
            response.setHeader("Access-Control-Expose-Headers", ExcelConstant.CONTENT_DISPOSITION);
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
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
            list = EasyExcel.read(file.getInputStream(), UserExcelModel.class, new ModelExcelListener()).sheet().doReadSync();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    /**
     * 使用HuTool工具类导出
     *
     * @param response 响应对象
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        List<User> list = new ArrayList<>();
        list.add(new User("哈士奇", "1231", DateUtil.date()));
        list.add(new User("哈士奇1", "1232", DateUtil.date()));
        list.add(new User("哈士奇2", "1233", DateUtil.date()));
        list.add(new User("哈士奇3", "1234", DateUtil.date()));
        list.add(new User("哈士奇4", "1235", DateUtil.date()));
        list.add(new User("哈士奇5", "1236", DateUtil.date()));
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //自定义标题别名
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("birthDay", "生日");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(2, "申请人员信息");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String name = "test";
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
