package me.tianjx98.Jscrapy.utils;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * @ClassName XPathTool
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-10-22 20:27
 */
public class XPathTool {
    public static final NodeList EMPTY = new NodeList() {
        @Override
        public Node item(int index) {
            return null;
        }

        @Override
        public int getLength() {
            return 0;
        }
    };
    private static final XPath XPATH = XPathFactory.newInstance().newXPath();
    Document doc;

    public XPathTool(String html) throws ParserConfigurationException {
        TagNode tagNode = new HtmlCleaner().clean(html);
        doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
    }

    public NodeList select(String xpathQuery) throws XPathExpressionException {
        Object value = XPATH.evaluate(xpathQuery, doc, XPathConstants.NODESET);
        if (value instanceof NodeList) {
            return (NodeList) value;
        }
        return EMPTY;
    }

}
