package com.hippolee.app.manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hippolee.app.manager.TaskDataManager;
import com.hippolee.app.util.StringPrintUtil;

public class PrintManager {

	private static final Integer DEFAULT_A4_PAPER_WIDTH = 595;
	private static final Integer DEFAULT_A4_PAPER_HEIGHT = 842;

	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(PrintManager.class);

	private static final PrintManager instance = new PrintManager();

	private PrintManager() {

	}

	public static PrintManager getInstance() {
		return instance;
	}

	public void printTaskData() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
		Paper paper = pf.getPaper();
		paper.setSize(DEFAULT_A4_PAPER_WIDTH, DEFAULT_A4_PAPER_HEIGHT);
		paper.setImageableArea(0, 0, DEFAULT_A4_PAPER_WIDTH, DEFAULT_A4_PAPER_HEIGHT);
		// 设置打印方向
		pf.setOrientation(PageFormat.LANDSCAPE);
		pf.setPaper(paper);
		pj.setPrintable(new TaskPrinter(), pf);

		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException e) {
				logger.error("print error", e);
			}
		}
	}

	public class TaskPrinter implements Printable {

		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			// 任务数据
			Object[][] taskData = TaskDataManager.getInstance().getTaskData();
			int firstIndex = pageIndex * 9;
			if (taskData == null || taskData.length <= firstIndex) {
				return Printable.NO_SUCH_PAGE;
			}

			Graphics2D g2d = (Graphics2D) graphics;
			// 可打印区域宽高
			int imageableWidth = (int) pageFormat.getImageableWidth();
			int imageableHeight = (int) pageFormat.getImageableHeight();
			// 可打印区域原点
			int imageableX = (int) pageFormat.getImageableX();
			int imageableY = (int) pageFormat.getImageableY();
			// 基准偏移
			int[] baseOffset = new int[] { imageableX, imageableY };
			// 项目块宽高
			int blockWidth = imageableWidth / 3;
			int blockHeight = imageableHeight / 3;
			// 文本块宽高
			int baseTextWidth = blockWidth / 3;
			int baseTextHeight = blockHeight / 6;
			// 文本块尺寸
			int[] baseTextSize = new int[] { baseTextWidth, baseTextHeight };

			// 每页9项任务
			for (int i = 0; i < 9; i++) {
				// 偏移量处理
				int[] blockOffset = new int[] { blockWidth * (i % 3), blockHeight * (i == 0 ? 0 : i / 3) };

				// 绘制框线
				if (i % 3 == 2) {
					// 第一根横线
					g2d.drawLine(imageableX, imageableY + blockOffset[1], imageableX + imageableWidth,
							imageableY + blockOffset[1]);
					// 第二根横线
					g2d.drawLine(imageableX, imageableY + blockOffset[1] + baseTextHeight, imageableX + imageableWidth,
							imageableY + blockOffset[1] + baseTextHeight);
					// 第三根横线
					g2d.drawLine(imageableX, imageableY + blockOffset[1] + blockHeight - baseTextHeight,
							imageableX + imageableWidth, imageableY + blockOffset[1] + blockHeight - baseTextHeight);
				}
				if (i >= 6) {
					// block左竖线
					g2d.drawLine(imageableX + blockOffset[0], imageableY, imageableX + blockOffset[0],
							imageableY + blockOffset[1] + blockHeight);
				}
				if (i == 8) {
					// 最右侧竖线
					g2d.drawLine(imageableX + blockOffset[0] + blockWidth, imageableY,
							imageableX + blockOffset[0] + blockWidth, imageableY + blockOffset[1] + blockHeight);
					// 最底部横线
					g2d.drawLine(imageableX, imageableY + blockOffset[1] + blockHeight, imageableX + imageableWidth,
							imageableY + blockOffset[1] + blockHeight);
				}
				// block上部第一栏短竖线
				g2d.drawLine(imageableX + blockOffset[0] + baseTextWidth, imageableY + blockOffset[1],
						imageableX + blockOffset[0] + baseTextWidth, imageableY + blockOffset[1] + baseTextHeight);
				// block上部第二栏短竖线
				g2d.drawLine(imageableX + blockOffset[0] + baseTextWidth * 2, imageableY + blockOffset[1],
						imageableX + blockOffset[0] + baseTextWidth * 2, imageableY + blockOffset[1] + baseTextHeight);
				// block下部第一栏短竖线
				g2d.drawLine(imageableX + blockOffset[0] + baseTextWidth,
						imageableY + blockOffset[1] + blockHeight - baseTextHeight,
						imageableX + blockOffset[0] + baseTextWidth, imageableY + blockOffset[1] + blockHeight);
				// block下部部第二栏短竖线
				g2d.drawLine(imageableX + blockOffset[0] + baseTextWidth * 2,
						imageableY + blockOffset[1] + blockHeight - baseTextHeight,
						imageableX + blockOffset[0] + baseTextWidth * 2, imageableY + blockOffset[1] + blockHeight);

				// 数据Index
				int dataIndex = pageIndex * 9 + i;
				// 越界返回
				if (taskData.length <= dataIndex) {
					continue;
				}

				// 当前项数据
				Object[] data = taskData[dataIndex];

				// 产品经理
				this.drawText(g2d, "PM:" + data[2], baseOffset, blockOffset, new int[] { 0, 0 }, baseTextSize, 18, true,
						true);
				// 客户端
				this.drawText(g2d, (String) data[3], baseOffset, blockOffset, new int[] { baseTextWidth, 0 },
						baseTextSize, 18, true, true);
				// 迭代
				this.drawText(g2d, (String) data[1], baseOffset, blockOffset, new int[] { baseTextWidth * 2, 0 },
						baseTextSize, 18, true, true);
				// 特性
				String description = (String) data[4];
				int fontSize = 0;
				if (description.length() <= 14) {
					fontSize = 36;
				} else if (description.length() <= 27) {
					fontSize = 28;
				} else if (description.length() <= 40) {
					fontSize = 24;
				} else if (description.length() <= 65) {
					fontSize = 20;
				} else if (description.length() <= 96) {
					fontSize = 16;
				} else {
					fontSize = 14;
				}
				this.drawText(g2d, description, baseOffset, blockOffset, new int[] { 0, baseTextHeight },
						new int[] { baseTextWidth * 3, blockHeight - baseTextHeight * 2 }, fontSize, true, false);
				// 工时
				this.drawText(g2d, "工时:", baseOffset, blockOffset, new int[] { 0, blockHeight - baseTextHeight },
						new int[] { baseTextWidth, baseTextHeight }, 16, false, true);
				// 截止日期
				this.drawText(g2d, "截止日期:", baseOffset, blockOffset,
						new int[] { baseTextWidth, blockHeight - baseTextHeight }, new int[] { 45, baseTextHeight }, 12,
						false, true);
				// 开发
				this.drawText(g2d, (String) data[5], baseOffset, blockOffset,
						new int[] { baseTextWidth * 2, blockHeight - baseTextHeight },
						new int[] { baseTextWidth, baseTextHeight }, 18, true, true);
			}
			return Printable.PAGE_EXISTS;
		}

		/**
		 * 绘制文字，居中
		 * 
		 * @param g2d
		 * @param text
		 * @param baseOffset
		 *            基准偏移
		 * @param blockOffset
		 *            块偏移
		 * @param textOffset
		 *            文本块偏移
		 * @param baseTextSize
		 *            基准文本块尺寸
		 * @param fontSize
		 *            字号
		 * @param isCenter
		 *            居中
		 */
		private void drawText(Graphics2D g2d, String text, int[] baseOffset, int[] blockOffset, int[] textOffset,
				int[] baseTextSize, int fontSize, boolean isCenter, boolean isHCenter) {
			// 字体
			Font font = new Font("Hannotate SC", Font.BOLD, fontSize);
			g2d.setFont(font);
			// FontMetrics
			FontMetrics fm = g2d.getFontMetrics();
			// 文本宽度
			int textWidth = fm.stringWidth(text);
			// 行高
			int lineHeight = font.getSize();
			if (textWidth < baseTextSize[0]) {
				// 文本实际起始位置
				int textX = baseOffset[0] + blockOffset[0] + textOffset[0]
						+ (isCenter ? ((baseTextSize[0] - textWidth) / 2) : 5);
				int textY = baseOffset[1] + blockOffset[1] + textOffset[1]
						+ (isHCenter ? ((baseTextSize[1] - (int) (lineHeight * 1.2)) / 2) : 0)
						+ (int) (lineHeight * 1.2) - (int) (lineHeight * 0.2);
				// 直接绘制
				g2d.drawString(text, textX, textY);
			} else {
				// 折行数据
				String[] printableTextArray = StringPrintUtil.printableTextArray(fm, text, baseTextSize[0] - 10);
				// 行数
				int lines = printableTextArray.length;
				// 绘制每一行
				for (int i = 0; i < lines; i++) {
					String printableText = printableTextArray[i];
					// 文本实际起始位置
					int textX = baseOffset[0] + blockOffset[0] + textOffset[0] + 5;
					int textY = baseOffset[1] + blockOffset[1] + textOffset[1]
							+ (isHCenter ? (baseTextSize[1] - (int) (lineHeight * 1.2) * lines) / 2 : 0)
							+ (int) (lineHeight * 1.2) * (i + 1) - (int) (lineHeight * 0.2);
					// 直接绘制
					g2d.drawString(printableText, textX, textY);
				}
			}
		}

		Color randomColor() {
			Random r = new Random();
			return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
		}

	}
}
