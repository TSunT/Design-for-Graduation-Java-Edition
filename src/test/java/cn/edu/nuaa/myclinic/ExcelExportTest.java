package cn.edu.nuaa.myclinic;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelExportTest {
    public static XSSFWorkbook creatExcel(String[] titles){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet1 = workbook.createSheet("Sheet1");
        XSSFRow row = sheet1.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("导入数据说明：");
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 5);
        sheet1.addMergedRegion(cellRangeAddress);
        XSSFRow row1 = sheet1.createRow(1);
        XSSFCell cell1 = row1.createCell(0);
        cell1.setCellValue("主键id不可更改，仅添加未清理原因、预计清理时间（文本格式）、备注字段");
        sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
        XSSFRow row2 = sheet1.createRow(2); int i=0;
        for (String title : titles) {
            row2.createCell(i).setCellValue(title);
            i++;
        }
        return workbook;
    }

    public static void main(String[] args) throws IOException {

        String[] titles = new String[]{
                "导入月份","部门名称","类型","发票号","项目编号","供应商名称","业务内容摘要"
                ,"应收款余额","经办人","初审","明细科目","未付原因","入账会计期间","是否民营企业","未清理原因","预计清理时间","备注"
        };

        XSSFWorkbook workbook = creatExcel(titles);
        //文档输出
        FileOutputStream out = new FileOutputStream("D:\\WorkFile\\咪咕相关\\应收应付导入表格专题\\样表\\" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xlsx");
        workbook.write(out);
        out.close();
    }
}
