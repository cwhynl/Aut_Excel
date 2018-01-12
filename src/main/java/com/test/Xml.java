package com.test;

import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Xml extends Base {
	public static String readxml(String path, String name) throws Exception,
			Exception {
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(path);
		Element root = doc.getRootElement();
		List<Element> messList = root.getChildren("mess");
		Element childrenRoot = null;
		List<Element> propertyList = null;
		for (int i = 0; i < messList.size(); i++) {
			childrenRoot = messList.get(i);
			propertyList = childrenRoot.getChildren("property");
			for (int j = 0; j < propertyList.size(); j++) {
				// 获取property元素
				Element element = propertyList.get(j);
				// element.getAttributeValue("name")：获取property中name属性的值
				if (element.getAttributeValue("name").equals(name)) { // 如果name的值一致
					return element.getAttributeValue("value"); // 取得name对应的value属性值
				}
			}
		}
		return null;
	}

	public static String read(String key) {
		String s = null;
		try {
			s = readxml("xml\\data1.xml", key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String read2(String key) {
		String s = null;
		try {
			s = readxml("xml\\data2.xml", key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(read2("mailedt"));
//		String cmd = "taskkill -f -pid 6380";
		String cmd="";
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
