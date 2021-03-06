package com.zds.scf.web.common.util;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * xml工具类
 * @author miklchen
 *
 */
public class XMLUtil {

	private static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);

	public static Map<String, String> doXMLParse(InputStream is) throws JDOMException, IOException{
		Reader re = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(re);
		String tmp = "";
		StringBuilder sb = new StringBuilder();
		while((tmp=br.readLine())!=null){
			sb.append(tmp);
		};
		String result = sb.toString();
		logger.info("=======================================================================================================================================================");
		logger.info(result);
		return doXMLParse(result);
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws org.jdom.JDOMException
	 * @throws java.io.IOException
	 */
	public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String, String> m = new TreeMap<String, String>();
		InputStream in = XMLUtil.string2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 获取xml编码字符集
	 * @param strxml
	 * @return
	 * @throws java.io.IOException
	 * @throws org.jdom.JDOMException
	 */
	public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
		InputStream in = XMLUtil.string2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		in.close();
		return (String)doc.getProperty("encoding");
	}
	
	public static InputStream string2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}
