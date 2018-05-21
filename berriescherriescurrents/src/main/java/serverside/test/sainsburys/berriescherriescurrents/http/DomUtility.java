package serverside.test.sainsburys.berriescherriescurrents.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import serverside.test.sainsburys.berriescherriescurrents.utility.CssQueryReader;
import serverside.test.sainsburys.berriescherriescurrents.utility.DocumentParser;
import serverside.test.sainsburys.berriescherriescurrents.utility.Helper;

public class DomUtility {
	private static CssQueryReader cssQueryReader = new CssQueryReader();
	private final static String publicUrl = cssQueryReader.coreUrl;
	private final static String productCoreUrl = cssQueryReader.productUrl;
	
	private JSONObject jsonData = new JSONObject();
	private JSONObject dataItem = new JSONObject();
	private JSONArray data = new JSONArray();
	
	private List<Double> amountList = new ArrayList<Double>();
	
	private static HttpUrlConnection httpUrlConnection = null;
	
	/*
	 * This method returns the completed data gathered from the target URL
	 */
	public JSONObject getCompletedData() {
		getCompletedJSONData();
		return jsonData;
	}
	
	/*
	 * This method gets the products listings to gather information on each product in the list
	 * Throws:  IOException
	 */
	public Elements getProductListings() throws IOException {
		Document htmlPage = getHtmlDom(publicUrl + cssQueryReader.coreUrlSuffix);
		Elements elements = DocumentParser.getNodesFromXmlUsingXpath(htmlPage, cssQueryReader.productGrid);
		return DocumentParser.getNodesFromElementsUsingXpath(elements, cssQueryReader.productLinks);
	}
	
	/*
	 * This is a method that calls the method to build the JSON Data for one product and clears out the 
	 * JSONObject item so it can be re-populate by the next product
	 * Parameters: Element - This is the product element gathered by the Xpath from the HTML DOM
	 * Throws:  IOException
	 */
	public void setUpJsonArray(Element product) throws IOException {
		String url = getProductUrls(product);
		Document htmlPage = getHtmlDom(productCoreUrl + url);
		populateJSON(htmlPage);
		data.put(dataItem);
		dataItem = new JSONObject();
	}
	
	/*
	 * This is a method that puts the product details and the totals into the same JSON object
	 */
	private void getCompletedJSONData() {
		jsonData.put("results", data);
		jsonData.put("total", "£" + getTotalUnitPrices());
	}
	
	/*
	 * This method sums the unit prices from the Products
	 */
	private String getTotalUnitPrices() {
		Double total = 0.00;
		for(Double values: amountList) {
			total = total + values;
		}
		return String.format("%.2f", total);
	}
	
	/*
	 * This is a method that builds the JSON array from the gathered data from each HTML DOM
	 * Parameters: Element - This is the product element gathered by the Xpath from the HTML DOM
	 * Throws:  IOException
	 */
	private void populateJSON(Document htmlPage) {
		getProductName(htmlPage);
		getProductDescription(htmlPage);
		getProductKcal(htmlPage);
		getProductsUnitPrices(htmlPage);		
	}
	
	/*
	 * This is a method that gets the HTML Dom from the target URL.
	 * Parameters: Url - The String of the target URL
	 * Throws:  IOException
	 */
	private Document getHtmlDom(String url) throws IOException {
		httpUrlConnection = new HttpUrlConnection(url);
		StringBuffer data = httpUrlConnection.getDataString();
		return DocumentParser.getHtmlOfWebPageToParse(data, "UTF-8");
	}
	
	/*
	 * This is a method that builds the JSON array from the gathered data for the Product Descriptions
	 * Parameters: productHtmlPage - This is the Document form of the Html Dom to parse
	 * Throws:  IOException
	 */
	private void getProductDescription(Document productHtmlPage) {
		Elements productDescriptionElements = DocumentParser.getNodesFromXmlUsingXpath(productHtmlPage, 
				cssQueryReader.productNameTextFromContainer );
		Elements productDescriptionArray =  DocumentParser.getNodesFromElementsUsingXpath(productDescriptionElements, 
				cssQueryReader.productDescriptionArrayFromContainer);
		String descriptionText = "";
		
		//This gets the first line of the descriptions. If the first element does not have text, we move 
		//onto the next element until we find one, then we break to of the for loop
		for (int i = 0; i < productDescriptionArray.size(); i++) {
			Element productDescription = productDescriptionArray.get(i);
			if(!productDescription.text().trim().equals("")){
				descriptionText += productDescription.text();
				break;
			}
		}
		
		descriptionText = descriptionText.trim();
		dataItem.put("description", descriptionText);
	}
	
	/*
	 * This is a method that builds the JSON array from the gathered data for the Product Name
	 * Parameters: productHtmlPage - This is the Document form of the Html Dom to parse
	 */
	private void getProductName(Document productHtmlPage) {
		Elements productName = DocumentParser.getNodesFromXmlUsingXpath(productHtmlPage,cssQueryReader.productName);
		dataItem.put("title", productName.first().text());
	}
	
	/*
	 * This is a method that builds the JSON array from the gathered data for the Product Kcal
	 * Parameters: productHtmlPage - This is the Document form of the Html Dom to parse
	 */
	private void getProductKcal(Document productHtmlPage) {
		Elements productKcalPer100Elements = null;
		Elements productDetails = DocumentParser.getNodesFromXmlUsingXpath(productHtmlPage, 
				cssQueryReader.productNameTextFromContainer);
		productKcalPer100Elements = productDetails.get(1)
				.select(cssQueryReader.productKcal);

		productKcalPer100Elements = selectProductKcalAlternativeRoute(productHtmlPage, productKcalPer100Elements);
		
		populateKcalJSONObject(productKcalPer100Elements);
	}
	
	/*
	 * This is a method that builds the JSON array based on whether the detailed information of the products
	 * contains information on the Kcal nutritional values
	 * Parameters: productHtmlPage - This is the Document form of the Html Dom to parse
	 */
	private void populateKcalJSONObject(Elements productKcalPer100Elements) {
		if (productKcalPer100Elements.size() < 1) {
			dataItem.put("kcal_per_100g", "");
		} else {
			Element productKcalPer100 = productKcalPer100Elements.first();
			dataItem.put("kcal_per_100g", productKcalPer100.text());
		}
	}
	
	/*
	 * This is a method that gets the Product URLs based on the href of each of the products
	 * Parameters: Element - these are the Html Elements containing the product summary including the URL for
	 * the detailed pages
	 */
	private String getProductUrls(Element product) {
		String productUrl = product.attr("href");
		return Helper.getLastPartOfUrl(productUrl);
	}
	
	/*
	 * This is a method that builds the JSON array from the gathered data for the Product Unit Prices as well
	 * as adding each unit price into a list to sum up in a later method
	 * Parameters: productHtmlPage - This is the Document form of the Html Dom to parse
	 */
	private void getProductsUnitPrices(Document productHtmlPage) {
		Boolean perUnit = false;
		String unitPrice = DocumentParser.getNodesFromXmlUsingXpath(productHtmlPage,
				cssQueryReader.pricePerUnitFromContainer).first().text().toLowerCase();
		
		if (unitPrice.contains("£")) {
			unitPrice= unitPrice.replace("£", "");
		}
		
		if(unitPrice.contains("/unit")) {
			perUnit = true;
		}
				
		unitPrice = String.format("%.2f",Double.parseDouble(unitPrice.replace("/unit", "").replace("â", "")));
		
		amountList.add(Double.parseDouble(unitPrice));
		
		if(perUnit) {
			unitPrice = unitPrice + "/unit";
		}
		
		dataItem.put("unit_price", "£" + unitPrice);
	}
	
	/*
	 * This is a method that determines whether the detailed products page's Kcal values jhas a different DOM
	 * structure, which requires a different CssQuery to gather the information. It also ensures that the 
	 * value returned is empty and included in the JSON data without throwing an error
	 * Parameters: productHtmlPage - This is the Document form of the Html Dom to parse
	 * 			   productKcalPer100Elements - This is the Html element containing the nutritional values from the products
	 */
	private Elements selectProductKcalAlternativeRoute(Document productHtmlPage, Elements productKcalPer100Elements) {
		if (productKcalPer100Elements.size() < 1) {
			productKcalPer100Elements = DocumentParser.getNodesFromXmlUsingXpath(productHtmlPage,
					cssQueryReader.productKcalAlternativeRouteTableRow);

			// This is set to 2 as the kcal in the second form of the cssQuery is in the
			// second tr record
			// therefore is we do not get it we do not want to proceed as it will cause an
			// index
			// out of bounds of the array error

			if (productKcalPer100Elements.size() >= 2) {
				productKcalPer100Elements = productKcalPer100Elements.get(1).select("td");
			}
		}
		return productKcalPer100Elements;
	}
}
