package com.zhrenjie04.alex.util;

import java.util.Random;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/***
 * 
 * 得到中文首字母
 * 
 * @author AlexZhang
 * 
 */

public class PinYinUtil {

	public static void main(String[] args) {
		String str = "这是一个测试";
		System.out.println("中文首字母：" + getPYIndexStr(str, true));
	}

	/**
	 * 返回首字母
	 */
	public static String getPYIndexStr(String strChinese, boolean bUpCase) {
		try {
			StringBuffer buffer = new StringBuffer();
			// 把中文转化成byte数组
			byte b[] = strChinese.getBytes("GBK");
			for (int i = 0; i < b.length; i++) {
				if ((b[i] & 255) > 128) {
					int char1 = b[i++] & 255;
					// 左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方
					char1 <<= 8;
					int chart = char1 + (b[i] & 255);
					buffer.append(getPYIndexChar((char) chart, bUpCase));
					continue;
				}
				char c = (char) b[i];
				if (!Character.isJavaIdentifierPart(c)) {
					// 确定指定字符是否可以是 Java
					// 标识符中首字符以外的部分。
					c = 'A';
				}
				buffer.append(c);
			}
			return buffer.toString();
		} catch (Exception e) {
			System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519")
					.append(e.getMessage()).toString());
		}
		return null;
	}

	/**
	 * 得到首字母
	 */
	private static char getPYIndexChar(char strChinese, boolean bUpCase) {

		int charGBK = strChinese;

		char result;

		if (charGBK >= 45217 && charGBK <= 45252) {

			result = 'A';

		} else

		if (charGBK >= 45253 && charGBK <= 45760) {

			result = 'B';

		} else

		if (charGBK >= 45761 && charGBK <= 46317) {

			result = 'C';

		} else

		if (charGBK >= 46318 && charGBK <= 46825) {

			result = 'D';

		} else

		if (charGBK >= 46826 && charGBK <= 47009) {

			result = 'E';

		} else

		if (charGBK >= 47010 && charGBK <= 47296) {

			result = 'F';

		} else

		if (charGBK >= 47297 && charGBK <= 47613) {

			result = 'G';

		} else

		if (charGBK >= 47614 && charGBK <= 48118) {

			result = 'H';

		} else

		if (charGBK >= 48119 && charGBK <= 49061) {

			result = 'J';

		} else

		if (charGBK >= 49062 && charGBK <= 49323) {

			result = 'K';

		} else

		if (charGBK >= 49324 && charGBK <= 49895) {

			result = 'L';

		} else

		if (charGBK >= 49896 && charGBK <= 50370) {

			result = 'M';

		} else

		if (charGBK >= 50371 && charGBK <= 50613) {

			result = 'N';

		} else

		if (charGBK >= 50614 && charGBK <= 50621) {

			result = 'O';

		} else

		if (charGBK >= 50622 && charGBK <= 50905) {

			result = 'P';

		} else

		if (charGBK >= 50906 && charGBK <= 51386) {

			result = 'Q';

		} else

		if (charGBK >= 51387 && charGBK <= 51445) {

			result = 'R';

		} else

		if (charGBK >= 51446 && charGBK <= 52217) {

			result = 'S';

		} else

		if (charGBK >= 52218 && charGBK <= 52697) {

			result = 'T';

		} else

		if (charGBK >= 52698 && charGBK <= 52979) {

			result = 'W';

		} else

		if (charGBK >= 52980 && charGBK <= 53688) {

			result = 'X';

		} else

		if (charGBK >= 53689 && charGBK <= 54480) {

			result = 'Y';

		} else

		if (charGBK >= 54481 && charGBK <= 55289) {

			result = 'Z';

		} else {

			result = (char) (65 + (new Random()).nextInt(25));
		}

		if (!bUpCase) {

			result = Character.toLowerCase(result);
		}

		return result;

	}

	/**
	 * 将汉字转换为全拼
	 *
	 * @param src
	 * @return String
	 */
	public static String getPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，直接取出字符并连接到字符串t4后
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t4;
	}

	/**
	 * 提取每个汉字的首字母
	 *
	 * @param str
	 * @return String
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	/**
	 * 将字符串转换成ASCII码
	 *
	 * @param cnStr
	 * @return String
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff) + " ");
		}
		return strBuf.toString();
	}
}