package com.example.administrator.androidtestdemo.manager;

import com.example.administrator.androidtestdemo.bean.Student;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXForHandler extends DefaultHandler {

    private List<Student> list;
    private Student student;
    //用于存储读取的临时变量
    private String tempString;

    /**
     * 解析到文档开始调用，一般做初始化操作
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        list = new ArrayList<>();
        super.startDocument();
    }

    /**
     * 解析到文档末尾调用，一般做回收操作
     *
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    /**
     * 每读到一个元素就调用该方法
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("student".equals(qName)) {
            //读到student标签
            student = new Student();
        } else if ("name".equals(qName)) {
            //获取name里面的属性
            String sex = attributes.getValue("sex");
            student.setSex(sex);
        }
        super.startElement(uri, localName, qName, attributes);
    }

    /**
     * 读到元素的结尾调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("student".equals(qName)) {
            list.add(student);
        }
        if ("name".equals(qName)) {
            student.setName(tempString);
        } else if ("nickName".equals(qName)) {
            student.setNickName(tempString);
        }
        super.endElement(uri, localName, qName);
    }

    /**
     * 读到属性内容调用
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //sax引擎扫描到的内容保存到tempString中
        tempString = new String(ch, start, length).trim();
        super.characters(ch, start, length);
    }

    /**
     * 获取该List
     *
     * @return
     */
    public List<Student> getStudents() {
        return list;
    }
}
