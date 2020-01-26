package com.techwells.wumei.util;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class ExcelUtil {

	@SuppressWarnings({ "deprecation", "static-access" })
	public static String getValue(Cell hssfCell) {

		if (hssfCell == null) {
			return "";
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值/返回日期类型的值
			if (DateUtil.isCellDateFormatted(hssfCell)) {
				Date date = hssfCell.getDateCellValue();
				return DateFormatUtils.format(date, "yyyy-MM-dd");
			} else {
				int str = (int) hssfCell.getNumericCellValue();
				return String.valueOf(str);
			}
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());

		}
	}
}
