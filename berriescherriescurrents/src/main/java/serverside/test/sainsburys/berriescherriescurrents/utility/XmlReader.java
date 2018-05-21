package serverside.test.sainsburys.berriescherriescurrents.utility;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader {
	
	private XPath xpath = null;
	private Document xmlDocument = null;
	/*
	 * This is a constructor for building the XML reader
	 * Parameters: fileLocation - the String of where the XML is located
	 * Throws: ParserConfigurationException, SAXException, IOException
	 */
	public XmlReader(String fileLocation) {
		try {
			XMLReader(fileLocation);
		}
		catch(Exception ex) {
			System.out.println("Error Reading XML File: " + ex.getMessage());
		}
	}
	
	/*
	 * This is a method that gets the xpath attribute of an XML Document based on another attribute of
	 * the specified XML Node Element
	 * Parameters: 
 	 * xpathString - The xpath string to determine what XML Element to get data from
 	 * desiredAttribute - The desired attribute from the XML Document
 	 * Throws XPathExpressionException
	 */
	public String getXmlElementAttribute(String xpathString, String desiredAttribute)  {
		try {
			XPathExpression expr = xpath.compile(xpathString);
			NodeList nodes = (NodeList) expr.evaluate(xmlDocument, XPathConstants.NODESET);
			Element e = (Element)nodes.item(0);
			return e.getAttribute(desiredAttribute);
		}
		catch(Exception ex) {
			System.out.println("Xpath Error: " + ex.getMessage());
		}
		return null;
	}
	
	/*
	 * This is a method for loading and reading the XML Document for the class
	 * Parameters: fileLocation - the String of where the XML is located
	 * Thorws: ParserConfigurationException, SAXException, IOException
	 */
	private void XMLReader(String fileLocation) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File(fileLocation);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		xmlDocument = dBuilder.parse(xmlFile);
		XPathFactory xPathfactory = XPathFactory.newInstance();
		xpath = xPathfactory.newXPath();

	}
}
