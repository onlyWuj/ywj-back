package com.zds.scf.biz.common.report;

import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.udc.UDC;
import com.zds.scf.biz.common.util.UtilPub;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author wuxiang@zdsoft.cn
 */
@Service
public class ReportService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static int rowHeight = 20;//行高

    private final static int colWidth = 20;//列宽

    protected static final String blank = " ";

    /**
     * 构造excel
     *
     * @param sheetName
     * @param titleList
     * @param sumMap
     * @param rowInfoList
     * @param <T>
     * @return
     */
    protected <T> ReportOutDto structureExcel(String sheetName, List<String> titleList, Map<String, String> sumMap, List<T> rowInfoList) {
        String fileName = getFileName(sheetName);
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(colWidth);
//        sheet.setDisplayGridlines(false);//不显示网格线
        //head单元格样式对象
        XSSFCellStyle headStyle = UtilPub.getStyle(workBook);
        headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        //cell单元格样式对象
        XSSFCellStyle cellStyle = UtilPub.getStyle(workBook);
        //底部统计单元格样式对象
        XSSFCellStyle craStyle = UtilPub.getStyle(workBook);

        int rowSeq = -1; //行序号
        int colSeq = -1; //列序号
        //生成标题第1行
        Row titleRow = UtilPub.createRow(sheet, ++rowSeq, rowHeight);
        for (String title : titleList) {
            UtilPub.createCellTitle(titleRow, ++colSeq, headStyle).setCellValue(title);
        }
        //生成明细行
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        try {
            for (T t : rowInfoList) {
                colSeq = -1;
                Field fields[] = t.getClass().getDeclaredFields();
                Row row = UtilPub.createRow(sheet, ++rowSeq, rowHeight);
                for (Field field : fields) {
                    String fieldName = field.getName(); // 获取属性的名字
                    fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method m = t.getClass().getMethod("get" + fieldName);
                    Object value = m.invoke(t); // 调用getter方法获取属性值
                    String fieldType = field.getGenericType().toString();

                    if (fieldType.equals("class java.math.BigDecimal")) {
                        UtilPub.createCellAmount(row, ++colSeq, cellStyle).setCellValue(value.toString());
                    } else {
                        String text ;
                        if(Objects.isNull(value)){
                            text ="";
                        }else if(value instanceof UDC){
                            text = ((UDC) value).getItemName();
                        }else
                            text = value.toString();
                        UtilPub.createCellText(row, ++colSeq, cellStyle).setCellValue(text);
                    }
                    if (fieldName.startsWith("Total")) {
                        if (value instanceof BigDecimal) {
                            BigDecimal sum = map.get(fieldName);
                            if (sum == null) {
                                sum = new BigDecimal(BigInteger.ZERO);
                            }
                            sum = sum.add(convert(value));
                            map.put(fieldName, sum);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("{解析excel明细值出错}", e.getMessage(), e);
        }

        //汇总信息
        StringBuilder sumInfo = new StringBuilder("");
        Set<String> set1 = sumMap.keySet();//汇总行标题
        Set<String> set2 = map.keySet();//汇总行数据
        for (String str1 : set1) {
            for (String str2 : set2) {
                if (str1.equalsIgnoreCase(str2)) {
                    sumInfo.append(sumMap.get(str1)).append(":").append(map.get(str2)).append("; ");
                    break;
                }
            }
        }

        //生成总汇行
        int i = ++rowSeq;
        colSeq = -1;
        int j = ++colSeq;

        //合并单元格
        CellRangeAddress cra = new CellRangeAddress(i, i, j, titleList.size() - 1);
        sheet.addMergedRegion(cra);
        Row totalRow = UtilPub.createRow(sheet, i, rowHeight);

        //设置合并后单元格的边框
       /* RegionUtil.setBorderTop(HSSFCellStyle.BORDER_THIN, cra, sheet, workBook);
        RegionUtil.setBorderBottom(HSSFCellStyle.BORDER_THIN, cra, sheet, workBook);
        RegionUtil.setBorderLeft(HSSFCellStyle.BORDER_THIN, cra, sheet, workBook);
        RegionUtil.setBorderRight(HSSFCellStyle.BORDER_THIN, cra, sheet, workBook);

        UtilPub.createCellBottom(totalRow, j, craStyle).setCellValue("合计 " + sumInfo.toString());*/

        return flush(workBook, fileName, sheetName + "报表", logger);
    }


    /**
     * 输出数据流
     */
    private ReportOutDto flush(XSSFWorkbook workBook, String fileName, String reportName, Logger logger) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workBook.write(out);
            workBook.close();
            return new ReportOutDto(fileName.concat(".xlsx"), out);
        } catch (Exception e) {
            CPBusinessException.throwIt(logger.getClass().getName(), "生成" + reportName + "失败");
            return null;
        } finally {
            try {
                workBook.close();
            } catch (IOException e) {
                logger.error("{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 生成文件名
     */
    private String getFileName(String reportName) {
        return reportName.concat("(".concat(UtilPub.getTodayDLFormat()).concat(")"));
    }

    public void createExcel(ReportOutDto result, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload");

            String filename = result.getFileName();
            String iso_filename = parseGBK(filename);
            response.setHeader("Content-Disposition", "attachment;fileName=" + iso_filename);

            result.response(new ReportOutDto.DoUot() {
                @Override
                public void out(ByteArrayOutputStream inputStream) {
                    OutputStream os = null;
                    try {
                        os = response.getOutputStream();
                        inputStream.writeTo(os);
                        // 这里主要关闭。
                        os.close();
                        inputStream.close();
                        logger.info(filename + "报表excel导出成功");
                    } catch (IOException e) {
                        logger.error("{}", e.getMessage(), e);
                    }

                }
            });
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    private String parseGBK(String sIn) {
        if (sIn == null || sIn.equals(""))
            return sIn;
        try {
            return new String(sIn.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException usex) {
            return sIn;
        }
    }

    private BigDecimal convert(Object o) {
        DecimalFormat df = new DecimalFormat("0.0000000000");
        BigDecimal var = (BigDecimal) o;
        if (var.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(df.format(o));
        }
    }

}
