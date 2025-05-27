package com.bcu.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * 安全读取 Excel 单元格工具（兼容各种类型，无 .0 问题）
 */
public class ExcelCellUtil {

    public static String getString(Row row, int index) {
        if (row == null || row.getCell(index) == null) return "";
        Cell cell = row.getCell(index);

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue()); // 去掉 .0
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula(); // 或调用 evaluate
            case BLANK, _NONE -> "";
            default -> cell.toString().trim();
        };
    }

    public static int getInt(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell == null) return 0;

            return switch (cell.getCellType()) {
                case NUMERIC -> (int) cell.getNumericCellValue();
                case STRING -> Integer.parseInt(cell.getStringCellValue().trim());
                default -> 0;
            };
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getDouble(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell == null) return 0.0;

            return switch (cell.getCellType()) {
                case NUMERIC -> cell.getNumericCellValue();
                case STRING -> Double.parseDouble(cell.getStringCellValue().trim());
                default -> 0.0;
            };
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static boolean getBoolean(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell == null) return false;

            return switch (cell.getCellType()) {
                case BOOLEAN -> cell.getBooleanCellValue();
                case STRING -> Boolean.parseBoolean(cell.getStringCellValue().trim());
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }
}
