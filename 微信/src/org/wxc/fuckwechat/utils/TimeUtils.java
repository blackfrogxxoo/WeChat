package org.wxc.fuckwechat.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	public static String getFormatTime(long time) {
		// 得到初步格式化的时间
		Date date = new Date(time);
		String formatTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatTime = sdf.format(date);
		System.out.println(formatTime);
		String currentTime = sdf.format(new Date());
		System.out.println(new Date().getTime());

		int y1 = Integer.parseInt(formatTime.substring(0, 4));
		int y2 = Integer.parseInt(currentTime.substring(0, 4));

		int M1 = Integer.parseInt(formatTime.substring(5, 7));
		int M2 = Integer.parseInt(currentTime.substring(5, 7));

		int d1 = Integer.parseInt(formatTime.substring(8, 10));
		int d2 = Integer.parseInt(currentTime.substring(8, 10));

		int day = y1 * 365 + M1 * 30 + d1;
		int today = y2 * 365 + M2 * 30 + d2;

		int m1 = Integer.parseInt(formatTime.substring(14, 16));
		int m2 = Integer.parseInt(currentTime.substring(14, 16));

		if (today - day == 0) {
			// 刚刚
			if (new Date().getTime() - time < 20000)
				return "刚刚";
			return "今天 " + removeZero(formatTime.substring(11, 16));
		} else if (today - day == 1) {
			if ("0".equals(formatTime.substring(11, 12))) {
				return "昨天 " + formatTime.substring(12, 16);
			} else
				return "昨天 " + formatTime.substring(11, 16);

			// 　以前
		} else if (today - day > 1) {
			return removeZero(formatTime.substring(5, 7)) + "月"
					+ removeZero(formatTime.substring(8, 10)) + "日";
		}

		return "未来";
	}

	public static String removeZero(String s) {
		if ("0".equals(s.substring(0, 1))) {
			return s.substring(1);
		}
		return s;
	}

}
