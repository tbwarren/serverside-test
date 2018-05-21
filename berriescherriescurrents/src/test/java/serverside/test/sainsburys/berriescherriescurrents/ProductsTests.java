package serverside.test.sainsburys.berriescherriescurrents;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import serverside.test.sainsburys.berriescherriescurrents.http.HttpUrlConnection;
import serverside.test.sainsburys.berriescherriescurrents.utility.CssQueryReader;

public class ProductsTests {
	
	private CssQueryReader cssQueryReader = new CssQueryReader();
	
	@Test
	public void httpConnectionTest() throws IOException {
		HttpUrlConnection httpUrlConnection = new HttpUrlConnection(cssQueryReader.coreUrl + cssQueryReader.coreUrlSuffix);
		assertThat("Connection did not retrun a 200 response code", httpUrlConnection.getResponseCode()==200);
	}
	
	@Test
	public void checkXmlCssQueryExists() throws IOException {
		File xmlDoc = new File("./data/CSSQuery/Products.xml");
		assertThat("Xml File Does Not Exist", xmlDoc.exists());
	}
	
}
