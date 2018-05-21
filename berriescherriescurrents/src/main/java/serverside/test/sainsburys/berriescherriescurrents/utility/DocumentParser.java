package serverside.test.sainsburys.berriescherriescurrents.utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public abstract class DocumentParser {
	/*
	 * The method gets the Elements of all xpaths matching the xpath argument in the document
	 * in the first argument
	 * Parameters: 
	 * htmlPage - the HTML DOM to parse
	 * xpath - the xpath string to search for within the Document
	 * returns NodeList
	 */
	public static Elements getNodesFromXmlUsingXpath(Document htmlPage, String xpath)  {
		Document doc = Jsoup.parse(htmlPage.toString(), "UTF-8");
		return doc.select(xpath);
	}
	
	/*
	 * The method gets the Elements of all xpaths matching the xpath argument in a collection of Elements
	 * in the first argument
	 * Parameters: 
	 * htmlPage - the HTML Elements to parse
	 * xpath - the xpath string to search for within the Document
	 * returns NodeList
	 */
	public static Elements getNodesFromElementsUsingXpath(Elements htmlElements, String xpath)  {
		return htmlElements.select(xpath);
	}
	
	/*
	 * The method gets the DOM of a HTML Page in a parsable format
	 * Parameters: 
	 * data - the HTML DOM in string format
	 * dataType - the parsing type for the data (e.g. UTF-8)
	 * returns Document
	 */
	public static Document getHtmlOfWebPageToParse(StringBuffer data, String dataType) {
		return Jsoup.parse(data.toString(), dataType);
	}
}
