package serverside.test.sainsburys.berriescherriescurrents.utility;

public class CssQueryReader {
	public String coreUrl;
	public String coreUrlSuffix;
	public String productDescriptionArrayFromContainer;
	public String productGrid;
	public String productKcal;
	public String productKcalAlternativeRouteTableRow;
	public String productLinks;
	public String productName;
	public String productNameTextFromContainer;
	public String pricePerUnitFromContainer;
	public String productUrl;

	
	public CssQueryReader() {
		XmlReader xmlReader = new XmlReader("./data/CSSQuery/Products.xml");
		coreUrl = xmlReader.getXmlElementAttribute(buildXpath("coreUrl"),"location");
		coreUrlSuffix = xmlReader.getXmlElementAttribute(buildXpath("coreUrlSuffix"),"location");
		productDescriptionArrayFromContainer = xmlReader.getXmlElementAttribute(
				buildXpath("productDescriptionArrayFromContainer"),"query");
		productGrid = xmlReader.getXmlElementAttribute(buildXpath("productGrid"),"query");
		productKcal = xmlReader.getXmlElementAttribute(buildXpath("productKcal"),"query");
		productKcalAlternativeRouteTableRow = xmlReader.getXmlElementAttribute(
				buildXpath("productKcalAlternativeRouteTableRow"),"query");
		productLinks = xmlReader.getXmlElementAttribute(buildXpath("productLinks"),"query");
		productName = xmlReader.getXmlElementAttribute(buildXpath("productName"),"query");
		productNameTextFromContainer = xmlReader.getXmlElementAttribute(buildXpath("productNameTextFromContainer"),"query");
		pricePerUnitFromContainer = xmlReader.getXmlElementAttribute(buildXpath("pricePerUnitFromContainer"),"query");
		productUrl = xmlReader.getXmlElementAttribute(buildXpath("productUrl"),"location");
	}
	
	private String buildXpath(String xpathAttributeName) {
		return String.format("//Element[@name='%1$s']", xpathAttributeName);
	}
}
