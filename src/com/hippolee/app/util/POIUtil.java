package com.hippolee.app.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hippolee.app.manager.ApplicationManager;
import com.hippolee.app.manager.TaskDataManager;

/**
 * POI文件读写工具类
 *
 * @author hippolee
 * @create 2017年2月24日
 * @version 1.0
 */
public class POIUtil {

	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

	/** 文件扩展Excel_xlsx */
	private final static String FILE_EXTENSION_XLSX = "xlsx";

	public static Vector<Vector<Object>> readExcelByVector(File file) {
		// 数据
		Vector<Vector<Object>> vector = new Vector<>();
		// 检查文件
		boolean result = checkExcelFile(file);
		if (!result) {
			return vector;
		}
		// 读取Wookbook
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file);
			// 读第一个Sheet
			XSSFSheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {
				return vector;
			}
			// 当前Sheet的开始行号
			int firstRowNum = sheet.getFirstRowNum();
			// 当前Sheet的开始行号
			int lastRowNum = sheet.getLastRowNum();
			// int numberOfRows = sheet.getPhysicalNumberOfRows();
			// 循环除了第一行的所有行
			for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
				// 获得当前行
				XSSFRow row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				// 获得当前行的开始列
				int firstCellNum = row.getFirstCellNum();
				// 获得当前行的结束列
				int lastCellNum = row.getLastCellNum();
				// 获得当前行的列数
				// int numberOrCells = row.getPhysicalNumberOfCells();
				// 行数据
				Vector<Object> cellValues = new Vector<>();
				// 循环当前行
				for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
					// 单元格
					XSSFCell cell = row.getCell(cellNum);
					cellValues.add(getCellValue(cell));
				}
				vector.add(cellValues);
			}
		} catch (Exception e) {
			logger.error("Exception When Reading Workbook Data!", e);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("Exception When Workbook Close!", e);
				}
			}
		}

		return vector;
	}

	public static Object[][] readExcelByArray(File file) {
		List<Object[]> list = readExcelByListObjects(file);
		return list.toArray(new Object[list.size()][]);
	}

	public static List<Object[]> readExcelByListObjects(File file) {
		// 数据
		List<Object[]> dataList = new ArrayList<>();
		// 检查文件
		boolean result = checkExcelFile(file);
		if (!result) {
			return dataList;
		}
		// 读取Wookbook
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file);
			// 读第一个Sheet
			XSSFSheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {
				return dataList;
			}
			// 当前Sheet的开始行号
			int firstRowNum = sheet.getFirstRowNum();
			// 当前Sheet的开始行号
			int lastRowNum = sheet.getLastRowNum();
			// int numberOfRows = sheet.getPhysicalNumberOfRows();
			// 循环除了第一行的所有行
			for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
				// 获得当前行
				XSSFRow row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				// 获得当前行的开始列
				int firstCellNum = row.getFirstCellNum();
				// 获得当前行的结束列
				int lastCellNum = row.getLastCellNum();
				// 获得当前行的列数
				// int numberOrCells = row.getPhysicalNumberOfCells();
				// 行数据
				ArrayList<Object> cellValues = new ArrayList<>();
				// 循环当前行
				for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
					// 单元格
					XSSFCell cell = row.getCell(cellNum);
					cellValues.add(getCellValue(cell));
				}
				dataList.add(cellValues.toArray(new Object[cellValues.size()]));
			}
		} catch (Exception e) {
			logger.error("Exception When Reading Workbook Data!", e);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					logger.error("Exception When Workbook Close!", e);
				}
			}
		}

		return dataList;
	}

	private static boolean checkExcelFile(File file) {
		// 判断文件是否存在
		if (file == null || !file.exists()) {
			logger.error("File not found!");
			return false;
		}
		// 获得文件扩展名
		String fileExtension = FilenameUtils.getExtension(file.getName());
		// 判断文件是否是excel文件
		if (!fileExtension.equalsIgnoreCase(FILE_EXTENSION_XLSX)) {
			logger.error("File {} is not Excel 2007 Document!", file.getName());
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private static Object getCellValue(XSSFCell cell) {
		// 单元格数据
		Object cellValue = null;
		if (cell == null) {
			return null;
		}

		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数值
			cellValue = Double.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN: // 布尔
			cellValue = Boolean.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR: // 错误
			cellValue = "非法数据";
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			// 公式结果类型
			switch (cell.getCachedFormulaResultType()) {
			case Cell.CELL_TYPE_NUMERIC:// 数值
				cellValue = Double.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:// 字符串
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN: // 布尔
				cellValue = Boolean.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR: // 错误
				cellValue = "非法数据";
				break;
			default:
				break;
			}
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}

	public static void writePowerpointByArray(File folder) {
		// ppt文档
		XMLSlideShow ppt = new XMLSlideShow();
		int width = 891;
		int height = 630;
		int textWidth = width / 3;
		ppt.setPageSize(new Dimension(width, height));
		try {
			// 任务数据
			Object[][] taskData = TaskDataManager.getInstance().getTaskData();
			for (Object[] data : taskData) {
				// 添加幻灯片
				XSLFSlide slide = ppt.createSlide();

				// 产品经理
				XSLFTextBox shape1 = slide.createTextBox();
				shape1.setAnchor(new Rectangle(0, 0, textWidth, 100));
				// shape1.setFillColor(Color.RED);
				XSLFTextParagraph paragraph1 = shape1.addNewTextParagraph();
				paragraph1.setTextAlign(TextAlign.CENTER);
				XSLFTextRun tr1 = paragraph1.addNewTextRun();
				tr1.setText("PM:" + data[2]);
				tr1.setFontColor(Color.BLACK);
				tr1.setFontFamily("Hannotate SC Regular");
				tr1.setBold(true);
				tr1.setFontSize(48D);

				// 客户端
				XSLFTextBox shape2 = slide.createTextBox();
				shape2.setAnchor(new Rectangle(textWidth, 0, textWidth, 100));
				// shape2.setFillColor(Color.GREEN);
				XSLFTextParagraph paragraph2 = shape2.addNewTextParagraph();
				paragraph2.setTextAlign(TextAlign.CENTER);
				XSLFTextRun tr2 = paragraph2.addNewTextRun();
				tr2.setText((String) data[3]);
				tr2.setFontColor(Color.BLACK);
				tr2.setFontFamily("Hannotate SC Regular");
				tr2.setBold(true);
				tr2.setFontSize(48D);

				// 迭代
				XSLFTextBox shape3 = slide.createTextBox();
				shape3.setAnchor(new Rectangle(textWidth * 2, 0, textWidth, 100));
				// shape3.setFillColor(Color.YELLOW);
				XSLFTextParagraph paragraph3 = shape3.addNewTextParagraph();
				paragraph3.setTextAlign(TextAlign.CENTER);
				XSLFTextRun tr3 = paragraph3.addNewTextRun();
				tr3.setText((String) data[1]);
				tr3.setFontColor(Color.BLACK);
				tr3.setFontFamily("Hannotate SC Regular");
				tr3.setBold(true);
				tr3.setFontSize(48D);

				// 特性
				XSLFTextBox shape4 = slide.createTextBox();
				shape4.setAnchor(new Rectangle(0, 100, width, height - 200));
				// shape4.setFillColor(Color.PINK);
				XSLFTextParagraph paragraph4 = shape4.addNewTextParagraph();
				paragraph4.setTextAlign(TextAlign.CENTER);
				XSLFTextRun tr4 = paragraph4.addNewTextRun();
				String description = (String) data[4];
				tr4.setText(description);
				tr4.setFontColor(Color.BLACK);
				tr4.setFontFamily("Hannotate SC Regular");
				tr4.setBold(true);
				if (description.length() <= 16) {
					tr4.setFontSize(96D);
				} else if (description.length() <= 28) {
					tr4.setFontSize(88D);
				} else if (description.length() <= 32) {
					tr4.setFontSize(72D);
				} else {
					tr4.setFontSize(60D);
				}

				// 开发
				XSLFTextBox shape5 = slide.createTextBox();
				shape5.setAnchor(new Rectangle(textWidth * 2, height - 100, textWidth, 100));
				// shape5.setFillColor(Color.ORANGE);
				XSLFTextParagraph paragraph5 = shape5.addNewTextParagraph();
				paragraph5.setTextAlign(TextAlign.CENTER);
				XSLFTextRun tr5 = paragraph5.addNewTextRun();
				tr5.setText((String) data[5]);
				tr5.setFontColor(Color.BLACK);
				tr5.setFontFamily("Hannotate SC Regular");
				tr5.setBold(true);
				tr5.setFontSize(48D);
			}
			// 任务文件名
			String fileName = "task_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".pptx";
			ppt.write(new FileOutputStream(new File(folder, fileName)));
		} catch (Exception e) {
			logger.error("Exception When Write Cookbook Data!", e);
		} finally {
			if (ppt != null) {
				try {
					ppt.close();
				} catch (IOException e) {
					logger.error("Exception When Cookbook Close!", e);
				}
			}
		}
	}

}