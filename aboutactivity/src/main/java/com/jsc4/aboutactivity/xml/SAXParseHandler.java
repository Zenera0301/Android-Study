package com.jsc4.aboutactivity.xml;

import android.text.TextUtils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXParseHandler extends DefaultHandler {


    public static final String ITEM = "item";
    private List<WebURL> mWebURLs;
    WebURL mWebURL;

    boolean mIsItem = false;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        mWebURLs = new ArrayList<>();//文档开始的时候，要新建一个ArrayList
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

    }

    // 元素开始解析的时候
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        mWebURL = new WebURL();
        if(TextUtils.equals(localName, ITEM)){//localName是item，attributes是属性（id，web等）
            for(int i=0;i<attributes.getLength();++i){
                if(TextUtils.equals(attributes.getLocalName(i), "id")){
                    mWebURL.setID(Integer.parseInt(attributes.getValue(i)));
                }
            }
            mIsItem = true;
        }
        mIsItem = false;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(TextUtils.equals(localName, ITEM)){
            mWebURLs.add(mWebURL);//添加到链表中
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String content = String.valueOf(ch, start, length);//读出来百度，淘宝等字样

        if(mIsItem){
            mWebURL.setContent(content);
            mIsItem = false;
        }

    }

    public List<WebURL> getXMLList(){
        return mWebURLs;
    }
}
