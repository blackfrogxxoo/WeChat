package org.wxc.fuckwechat.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jUtils {

	// TODO 将XML文件流转化成List<HashMap<String,String>>
	public static List<HashMap<String, Object>> xml2MsgList(InputStream is) {
		List<HashMap<String, Object>> msgList = new ArrayList<HashMap<String, Object>>();

		SAXReader reader = new SAXReader();
		// 2.解析xml获取代表整个文档的dom对象
		try {
			Document dom = reader.read(is);
			// 3.获取根节点
			Element root = dom.getRootElement();
			System.out.println("root节点为" + root.getName());
			Element frommsg = root.element("frommsg");

			// 遍历节点item及其子节点
			for (Iterator i = frommsg.elementIterator("item"); i.hasNext();) {
				Element item = (Element) i.next();
				// item的子节点： fromid msg time
				Element fromid = item.element("fromid");
				Element msg = item.element("msg");
				Element time = item.element("time");

				String fromidStr = fromid.getText();
				String msgStr = msg.getText();
				long timeLong = Long.parseLong(time.getText());

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("fromid", fromidStr);
				map.put("msg", msgStr);
				map.put("time", timeLong);
				msgList.add(map);
			}

			System.out.println(msgList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msgList;
	}
}
