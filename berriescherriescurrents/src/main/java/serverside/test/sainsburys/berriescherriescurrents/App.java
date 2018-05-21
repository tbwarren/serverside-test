package serverside.test.sainsburys.berriescherriescurrents;

import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import serverside.test.sainsburys.berriescherriescurrents.http.DomUtility;

public class App {

	private static DomUtility domUtility = new DomUtility();

	public static void main(String[] args) throws IOException   {
		Elements products = domUtility.getProductListings();

		for (Element product : products) {
			domUtility.setUpJsonArray(product);
		}
		
		String data = domUtility.getCompletedData().toString(4);
		System.out.println(data);

	}
}
