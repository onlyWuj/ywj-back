package com.zds.scf.biz.common.util;

import com.zds.common.util.StringUtils;
import com.zds.scf.biz.common.CPContext;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 业务工具类
 *
 */
@SuppressWarnings("UnusedDeclaration")
public class UtilPub {

	private static SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm:ss");

	public static SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat dLFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	private static SimpleDateFormat dLFormat2 = new SimpleDateFormat("yyyyMMdd");

	public static DecimalFormat df2Format = new DecimalFormat("###,###,###,###,##0.00");

	private static TimeZone mTimeZone = TimeZone.getTimeZone("Asia/Shanghai");

	public static String getTodayDLFormat() {
		return dLFormat.format(new Date());
	}

	public static boolean isEmpty(Object[] o) {
		return o == null || o.length == 0;
	}
	public static boolean isEmpty(Object o) {
		return o == null||o.toString().equals("");
	}

	public static boolean isEmpty(Date o) {
		return o == null;
	}

	public static boolean isEmpty(Long o) {
		return o == null || o.equals(0L);
	}

	public static boolean isNotEmpty(Long o) {
		return !isEmpty(o);
	}

	public static boolean isNotEmpty(Collection collection) {
		return !isEmpty(collection);
	}

	private static boolean isEmptyOrZero(Long o) {
		return o == null || o.equals(0L);
	}

	public static boolean isNotEmptyOrZero(Long o) {
		return !isEmptyOrZero(o);
	}

	/**
	 * 获取今日
	 *
	 * @return 获取今日java.sql.Date类型日期；
	 */
	public static java.sql.Date nowDate() {
		return new java.sql.Date(System.currentTimeMillis());
	}

	/**
	 * Return a Timestamp for right now
	 *
	 * @return Timestamp for right now
	 */
	private static java.sql.Timestamp nowTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取当月最后一天日期
	 * 
	 * @param nowDate
	 * @return
	 */
	public static Date geMonthLastDay(Date nowDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDayOfMonth = calendar.getTime();
		return lastDayOfMonth;
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @param nowDate
	 * @return
	 */
	public static int getLastDay(Date nowDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastDayOfMonth;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		Date date = null;
		try {
			date = dtFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatDate(Date date) {
		String loanDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date) + " 00:00:00";

		return StrToDate(loanDate);
	}

	/**
	 * 取得系统今天的时间串
	 *
	 * @return current datetime, pattern: "yyyy-MM-dd HH:mm:ss".
	 */
	public static String nowStdDateTimeString() {
		return dtFormat.format(nowTimestamp());
	}

	public static String nowStdTimeString() {
		return tFormat.format(nowTimestamp());
	}

	public static Date getCurDate() {
		return new Date(System.currentTimeMillis());
	}

	public static String getCurDateStr() {
		return dFormat.format(new Date(System.currentTimeMillis()));
	}

	public static Timestamp getCurDatetime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String getCurDatetimeStr() {
		return dtFormat.format(new Timestamp(System.currentTimeMillis()));
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.size() == 0;
	}

	public static String formatSkuName(String skuName) {
		if (!StringUtils.isEmpty(skuName)) {
			StringBuilder displayName = new StringBuilder("");
			String skuNameArray[] = skuName.split(",");
			for (String name : skuNameArray) {
				displayName.append(name.split(":")[1]);
				displayName.append(" ");
			}
			return displayName.substring(0, displayName.length() - 1);
		}
		return "";
	}

	public static java.sql.Date toDate(String str) {
		try {
			if (isEmpty(str))
				return null;
			return java.sql.Date.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Func:为空，返回""
	 *
	 * @param str
	 *            带检查的字符串
	 * @return ""
	 */
	public static String checkNull(String str) {
		if (isEmpty(str))
			return "";
		return str.trim();
	}

	public static String checkNull(String str, String defaultStr) {
		if (isEmpty(str))
			return defaultStr;
		return str.trim();
	}

	public static String getString(Long n) {
		if (n == null)
			return "";
		return String.valueOf(n);
	}

	public static Long checkNull(Long n) {
		Long v;
		if (n == null)
			v = 0L;
		else
			v = n;
		return v;
	}

	public static BigDecimal checkNull(BigDecimal n) {
		if (n == null) {
			return BigDecimal.ZERO;
		}
		return n;
	}

	public static String getBeginTime(Date date) {
		if (null == date)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);
	}

	public static String getEndTime(Date date) {
		if (null == date)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date);
	}

	public static Row createRow(Sheet sheet, int rowNum, int rowHeight) {
		Row row = sheet.getRow(rowNum);
		if (row != null)
			return row;
		row = sheet.createRow(rowNum);
		if (rowHeight > 0)
			row.setHeightInPoints(rowHeight);
		return row;
	}

	public static Cell createCellTitle(Row row, int column, XSSFCellStyle style) {
		return createCell(row, column, style, HSSFCellStyle.ALIGN_CENTER);
	}

	public static Cell createCellBottom(Row row, int column, XSSFCellStyle style) {
		return createCell(row, column, style, HSSFCellStyle.ALIGN_LEFT);
	}

	public static Cell createCellText(Row row, int column, XSSFCellStyle style) {
		return createCell(row, column, style, HSSFCellStyle.ALIGN_CENTER);
	}

	public static Cell createCellQuantity(Row row, int column, XSSFCellStyle style) {
		return createCell(row, column, style, HSSFCellStyle.ALIGN_CENTER);
	}

	public static Cell createCellAmount(Row row, int column, XSSFCellStyle style) {
		return createCell(row, column, style, HSSFCellStyle.ALIGN_CENTER);
	}

	private static Cell createCell(Row row, int column, XSSFCellStyle style, short align) {
		Cell cell = row.getCell(column);
		if (cell == null) {
			cell = row.createCell(column);
		}
		style.setAlignment(align);
		cell.setCellStyle(style);
		return cell;
	}

	public static String getStrIgnoredZero(BigDecimal decimal) {
		if (null == decimal || decimal.compareTo(BigDecimal.ZERO) == 0)
			return " ";
		decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return decimal.toString();
	}

	/**
	 * 取百分百
	 */
	public static String calcPercent(double a, double b) {
		if (a == 0 || b == 0)
			return "0.00%";
		NumberFormat nt = NumberFormat.getPercentInstance();
		nt.setMinimumFractionDigits(2);
		return nt.format(a / b);
	}

	/**
	 * 判断时间是否在当天范围内
	 */
	public static boolean isToday(Date dateTime) {
		Date now = new Date();
		String todayStr = dFormat.format(now);
		String dateTimeStr = dFormat.format(dateTime);
		return todayStr.equals(dateTimeStr);
	}

	/**
	 * 得到Long 获取转换的Long值，有错返回0
	 *
	 * @param str
	 *            带转换的字符串
	 * @return long
	 */
	public static long getLongIgnoreErr(String str) {
		if (str == null)
			return 0L;
		str = str.trim();
		if (str.equals(""))
			return 0L;
		str = str.replaceAll(",", "").replaceAll("，", "");
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			return 0L;
		}
	}

	/**
	 * 得到date + days的日期
	 * 
	 * @param dt
	 *            日期
	 * @param days
	 *            间隔天数，正数往前，负数往后
	 * @return Date java.util.Date
	 */
	public static Date getAfterDay(java.util.Date dt, int days) {
		if (dt == null)
			return null;
		if (days == 0)
			return dt;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeZone(mTimeZone);
		gc.setTime(dt);
		gc.add(GregorianCalendar.DATE, days);
		java.sql.Date ret = new java.sql.Date(gc.getTimeInMillis());
		return new Date(ret.getTime());
	}

	/**
	 * 返回系统超级管理员
	 */
	public static CPContext.UserInfo getSysAdminUserInfo() {
		CPContext.UserInfo userInfo = new CPContext.UserInfo();
		userInfo.setId(1L);
		userInfo.setCode("admin");
		userInfo.setName("超级管理员");
		return userInfo;
	}

	/*
	 * 列数据信息单元格样式
	 */
	public static XSSFCellStyle getStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 9);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		// style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	public static String formatFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 比较两个时间大小
	 * 
	 * @param loanTime
	 * @param fixedTime
	 * @return
	 */
	public static boolean repayTime(String loanTime, String fixedTime) {
		Date start = UtilPub.StrToDate(loanTime);
		Date end = UtilPub.StrToDate(fixedTime);
		if (start.compareTo(end) >= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 逐月增加时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date repayDate(Date date, int month) {
		if (date == null)
			return null;
		if (month == 0)
			return date;
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.MONTH, +month);
		date = cl.getTime();
		return date;
	}

	/**
	 * 计算两个日期相差天数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int dayBetween(Date startTime, Date endTime) {
		try {
			endTime = dFormat.parse(dFormat.format(endTime));
			startTime = dFormat.parse(dFormat.format(startTime));
		} catch (ParseException e) {
			e.getMessage();
		}
		if (startTime.after(endTime)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startTime);
			long time1 = cal.getTimeInMillis();
			cal.setTime(endTime);
			long time2 = cal.getTimeInMillis();
			long between_days = (time1 - time2) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		}else{

			Calendar cal = Calendar.getInstance();
			cal.setTime(startTime);
			long time1 = cal.getTimeInMillis();
			cal.setTime(endTime);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		}

	}

	/**
	 * 周期换算
	 * 
	 * @param cyle
	 * @return
	 */
	public static BigDecimal cycle(BigDecimal cycle) {

		BigDecimal strValue = BigDecimal.valueOf(1);
		if (cycle.intValue() == 1) {
			strValue = new BigDecimal(1);
		} else if (cycle.intValue() == 2) {
			strValue = new BigDecimal(7);
		} else if (cycle.intValue() == 3) {
			strValue = new BigDecimal(30);
		} else if (cycle.intValue() == 4) {
			strValue = new BigDecimal(90);
		} else if (cycle.intValue() == 5) {
			strValue = new BigDecimal(360);
		}
		return strValue;
	}

	public static void main(String[] args) throws Exception {
		// String time = nowStdTimeString();
		// System.out.println("time = " + time);
		// Date now = new Date();
		// String time2 = now.getHours() + ":" + now.getMinutes() + ":" +
		// now.getSeconds();
		DecimalFormat fnum = new DecimalFormat("##0.00");
		BigDecimal b = new BigDecimal(0.0);
		float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		String f2 = fnum.format(b);
		Date dt=StrToDate("2017-05-25 00:00:00");
		Date dt1=StrToDate("2017-05-15 00:00:00");
		System.out.println(dayBetween(dt,dt1));
	}
}
