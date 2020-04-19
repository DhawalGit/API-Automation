package APIAutomation.com.org.appliedselenium;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.*;

public class WeatherGetRequest {

	/// get Weather Request by city name
	@Test
	public void Test_01() {

		// Response class object contains all the information which is received by get()
		Response response = when().get(
				"https://samples.openweathermap.org/data/2.5/weather?q=London&appid=439d4b804bc8187953eb36d2a8c26a0");

		System.out.println("Status Code: " + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
