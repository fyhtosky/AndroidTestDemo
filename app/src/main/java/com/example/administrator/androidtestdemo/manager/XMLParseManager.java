package com.example.administrator.androidtestdemo.manager;

import android.app.Person;
import android.util.Log;
import android.util.Xml;

import com.example.administrator.androidtestdemo.bean.Student;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static com.example.administrator.androidtestdemo.manager.FileManager.TAG;

/**
 * xml的三种解析及分析
 */
public class XMLParseManager {
//    <students>
//    <student>
//        <name sex="man">小明</name>
//        <nickName>明明</nickName>
//    </student>
//    <student>
//        <name sex="woman">小红</name>
//        <nickName>红红</nickName>
//    </student>
//    <student>
//        <name sex="man">小亮</name>
//        <nickName>亮亮</nickName>
//    </student>
//</students>


/**
 *
 *  一、使用SAX解析XML
 *SAX(Simple API for XML) 使用流式处理的方式，它并不记录所读内容的相关信息。
 *它是一种以事件为驱动的XML API，解析速度快，占用内存少。使用回调函数来实现。 缺点是不能倒退。
 */


/**
 * 二、使用DOM解析XML
 *DOM(Document Object Model) 是一种用于XML文档的对象模型，可用于直接访问XML文档的各个部分。
 * 它是一次性全部将内容加载在内存中，生成一个树状结构,它没有涉及回调和复杂的状态管理。 缺点是加载大文档时效率低下
 */


    /**
     * 三、使用Pull解析XML
     *Pull内置于Android系统中。也是官方解析布局文件所使用的方式。
     * Pull与SAX有点类似，都提供了类似的事件，如开始元素和结束元素。
     * 不同的是，SAX的事件驱动是回调相应方法，需要提供回调的方法，而后在SAX内部自动调用相应的方法。
     * 而Pull解析器并没有强制要求提供触发的方法。因为他触发的事件不是一个方法，而是一个数字。它使用方便，效率高。
     */


    /**
     * 四、SAX、DOM、Pull的比较:
     *内存占用：SAX、Pull比DOM要好；
     *编程方式：SAX采用事件驱动，在相应事件触发的时候，会调用用户编好的方法，也即每解析一类XML，就要编写一个新的适合该类XML的处理类。DOM是W3C的规范，Pull简洁。
     *访问与修改:SAX采用流式解析，DOM随机访问。
     *访问方式:SAX，Pull解析的方式是同步的，DOM逐字逐句。
     */



    /**
     * 使用SAX解析XML文件
     * 输入流是指向程序中读入数据
     * @throws Throwable
     */
    public void testSAXGetPersons() throws Throwable {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("person.xml");
        SAXForHandler saxForHandler = new SAXForHandler();
        /**
         * 用工廠模式解析XML
         */
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(inputStream, saxForHandler);
        // 第二种方式解析XML
        // XMLReader xmlReader = saxParser.getXMLReader();
        // xmlReader.setContentHandler(handler);
        // xmlReader.parse(new InputSource(inputStream));
        List<Student> students = saxForHandler.getStudents();
        inputStream.close();
        for (Student student : students) {
            Log.i(TAG, student.toString());
        }
    }

    /**
     * dom解析
     * @param is
     * @return
     * @throws Exception
     */
    public List<Student> dom2xml(InputStream is) throws Exception {
        //一系列的初始化
        List<Student> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //获得Document对象
        Document document = builder.parse(is);
        //获得student的List
        NodeList studentList = document.getElementsByTagName("student");
        //遍历student标签
        for (int i = 0; i < studentList.getLength(); i++) {
            //获得student标签
            Node node_student = studentList.item(i);
            //获得student标签里面的标签
            NodeList childNodes = node_student.getChildNodes();
            //新建student对象
            Student student = new Student();
            //遍历student标签里面的标签
            for (int j = 0; j < childNodes.getLength(); j++) {
                //获得name和nickName标签
                Node childNode = childNodes.item(j);
                //判断是name还是nickName
                if ("name".equals(childNode.getNodeName())) {
                    String name = childNode.getTextContent();
                    student.setName(name);
                    //获取name的属性
                    NamedNodeMap nnm = childNode.getAttributes();
                    //获取sex属性，由于只有一个属性，所以取0
                    Node n = nnm.item(0);
                    student.setSex(n.getTextContent());
                } else if ("nickName".equals(childNode.getNodeName())) {
                    String nickName = childNode.getTextContent();
                    student.setNickName(nickName);
                }
            }
            //加到List中
            list.add(student);
        }
        return list;
    }

    /**
     * pull解析xml
     * @param is
     * @return
     * @throws Exception
     */
    public List<Student> pull2xml(InputStream is) throws Exception {
        List<Student> list = null;
        Student student = null;
        //创建xmlPull解析器
        XmlPullParser parser = Xml.newPullParser();
        ///初始化xmlPull解析器
        parser.setInput(is, "utf-8");
        //读取文件的类型
        int type = parser.getEventType();
        //无限判断文件类型进行读取
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                //开始标签
                case XmlPullParser.START_TAG:
                    if ("students".equals(parser.getName())) {
                        list = new ArrayList<>();
                    } else if ("student".equals(parser.getName())) {
                        student = new Student();
                    } else if ("name".equals(parser.getName())) {
                        //获取sex属性
                        String sex = parser.getAttributeValue(null,"sex");
                        student.setSex(sex);
                        //获取name值
                        String name = parser.nextText();
                        student.setName(name);
                    } else if ("nickName".equals(parser.getName())) {
                        //获取nickName值
                        String nickName = parser.nextText();
                        student.setNickName(nickName);
                    }
                    break;
                //结束标签
                case XmlPullParser.END_TAG:
                    if ("student".equals(parser.getName())) {
                        list.add(student);
                    }
                    break;
            }
            //继续往下读取标签类型
            type = parser.next();
        }
        return list;
    }
}
