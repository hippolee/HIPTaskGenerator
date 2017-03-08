package com.shadow.app.util;

import java.awt.FontMetrics;
import java.util.Vector;

public class StringPrintUtil {

	public static String[] printableTextArray(FontMetrics metrics, String text, int maxWidth) {
		// 字符串拆分结果
		Vector<String> resultVector = new Vector<>();
		// 文本长度
		int textLength = text.length();

		int indexBegin = 0;
		int indexEnd = 0;
		boolean wrap = false;
		while (true) {
			int width = 0;
			wrap = false;
			for (indexBegin = indexEnd; indexEnd < textLength; indexEnd++) {
				if (text.charAt(indexEnd) == '\n') {
					indexEnd++;
					wrap = true;
					break;
				}
				width += metrics.charWidth(text.charAt(indexEnd));
				if (width > maxWidth) {
					break;
				}
			}

			if (wrap) {
				resultVector.addElement(text.substring(indexBegin, indexEnd - 1));
			} else {
				resultVector.addElement(text.substring(indexBegin, indexEnd));
			}
			if (indexEnd >= textLength) {
				break;
			}
		}
		return resultVector.toArray(new String[resultVector.size()]);
	}

}
