package APIAutomation.com.org.appliedselenium;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.*;


public class WeatherGetParam {
	
	@Test
	public void Test_02() {
		Response response = given().
							parameter("q", "London").
							parameter("appid", "439d4b804bc8187953eb36d2a8c26a0").				
							when().
							get("https://samples.openweathermap.org/data/2.5/weather");
		System.out.println(response.getStatusCode());
	}
	
	//Verify the Weather description using JSON
	@Test
	public void Test_03() {
		String description = given().
							parameter("q", "Noida").
							parameter("appid", "d92c5d0fdd65d76425a73fe54845a4c9").				
							when().
							get("http://api.openweathermap.org/data/2.5/weather").
							then().
							contentType(ContentType.JSON). //check the content type is JSON
							extract().
							path("weather[0].description");
		System.out.println("Weather Condition is: "+description);
	}
	
	//Creating a test to assert if weather is Haze

	@Test
	public void Test_04() {
		String expectedWeather = "Haze";
		Response response = given().
							parameter("q", "Noida").
							parameter("appid", "d92c5d0fdd65d76425a73fe54845a4c9").				
							when().
							get("http://api.openweathermap.org/data/2.5/weather");
		
		String actualWeather = response.
								then().
								contentType(ContentType.JSON).
								extract().
								path("weather[0].description");
		
			if(actualWeather.equalsIgnoreCase(expectedWeather)) {
				System.out.println("Weather Condition is: "+actualWeather + " and case is PASS");
			}else {
				System.out.println("Actual weather Condition is: "+actualWeather + " and case is FAIL");
			}
	}
	
	
	//Verify the weather report generated through city ID (7279746) vs longitude and latitude 
	
	@Test
	public void Test_05() {
		Response response = given().
				parameter("id", "7279746").
				parameter("appid", "d92c5d0fdd65d76425a73fe54845a4c9").				
				when().
				get("http://api.openweathermap.org/data/2.5/weather");

		String reportUsingID = response.
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");
		System.out.println("Weather report generated through ID: "+reportUsingID);

		//Fetch the longitude and latitude using the response object variable
		
		// Cast value from float to String
		String lon = String.valueOf(response.
				then().
				contentType(ContentType.JSON).
				extract().
				path("coord.lon"));
		System.out.println("Longitude: "+lon);
		
		// Cast value from float to String
		String lat = String.valueOf(response.
				then().
				contentType(ContentType.JSON).
				extract().
				path("coord.lat"));
		System.out.println("Latitude: "+lat);
		
		String reportUsingCoord = given().
				parameter("lon", lon).
				parameter("lat", lat).	
				parameter("appid", "d92c5d0fdd65d76425a73fe54845a4c9").
				when().
				get("http://api.openweathermap.org/data/2.5/weather").
				then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");
		
		System.out.println("Weather report generated through coords: "+reportUsingCoord);
		
		Assert.assertEquals(reportUsingID, reportUsingCoord);
	}
}
