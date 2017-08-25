package com.zlt.update;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 解析文档
 */
public class ParseXmlService {
    public HashMap<String, String> parseXml(InputStream inStream)
            throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();// 实例化一个文档构建器工厂

        DocumentBuilder builder = factory.newDocumentBuilder();// 通过文档构建器工厂获取一个文档构建器

        Document document = builder.parse(inStream);// 通过文档通过文档构建器构建一个文档实例

        Element root = document.getDocumentElement();// 获取XML文件根节点

        NodeList childNodes = root.getChildNodes();// 获得所有子节点
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = (Node) childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                // 版本号
                if ("version".equals(childElement.getNodeName())) {
                    hashMap.put("version", childElement.getFirstChild()
                            .getNodeValue());
                }
                // 软件名称
                else if (("name".equals(childElement.getNodeName()))) {
                    hashMap.put("name", childElement.getFirstChild()
                            .getNodeValue());
                }
                // 下载地址
                else if (("url".equals(childElement.getNodeName()))) {
                    hashMap.put("url", childElement.getFirstChild()
                            .getNodeValue());
                } else if ("versionName".equals(childElement.getNodeName())) {
                    hashMap.put("versionName", childElement.getFirstChild()
                            .getNodeValue());
                } else if (("needfix".equals(childElement.getNodeName()))) {
                    //是否需要热修复
                    hashMap.put("needfix", childElement.getFirstChild().getNodeValue());
                } else if ("patchurl".equals(childElement.getNodeName())) {
                    //热修复补丁版本
                    hashMap.put("patchurl", childElement.getFirstChild().getNodeValue());
                } else if ("patchversion".equals(childElement.getNodeName())) {
                    //热修复版本
                    hashMap.put("patchversion", childElement.getFirstChild().getNodeValue());
                } else if ("patchname".equals(childElement.getNodeName())) {
                    //热修复版本
                    hashMap.put("patchname", childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return hashMap;
    }
}
