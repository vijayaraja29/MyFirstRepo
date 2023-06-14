package basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;
import groovy.util.logging.Log;

public class RestBasics {

	public static void main(String[] args) {

		/*
		 * Rest assured works on three principles - given, when, then given - all input
		 * details when - Submit the API then - Validate the response
		 */

		// Basic Rest assured validation
		/*
		 * RestAssured.baseURI = "https://rahulshettyacademy.com";
		 * given().log().all().queryParam("key", "qaclick123").header("Content-Type",
		 * "application/json") .body("{\r\n" + "  \"location\": {\r\n" +
		 * "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n" + "  },\r\n" +
		 * "  \"accuracy\": 50,\r\n" + "  \"name\": \"Rahul New Learning\",\r\n" +
		 * "  \"phone_number\": \"(+91) 983 893 3937\",\r\n" +
		 * "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n" +
		 * "    \"shoe park\",\r\n" + "    \"shop\"\r\n" + "  ],\r\n" +
		 * "  \"website\": \"http://google.com\",\r\n" +
		 * "  \"language\": \"French-IN\"\r\n" + "}\r\n" +
		 * "").when().post("maps/api/place/add/json").then().assertThat().log().all().
		 * statusCode(200);
		 */

		// Payload calling from other Class and adding more response validation
		/*
		 * RestAssured.baseURI = "https://rahulshettyacademy.com";
		 * given().log().all().queryParam("key", "qaclick123").header("Content-Type",
		 * "application/json")
		 * .body(payload.allPlace()).when().post("maps/	api/place/add/json").then().
		 * assertThat().log().all().statusCode(200) .body("scope",
		 * equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)");
		 */

		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.allPlace()).when().post("maps/api/place/add/json").then().log().all().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		System.out.println(response);

		JsonPath js = new JsonPath(response);
		String placeID = js.getString("place_id");
		System.out.println(placeID);

		// Add place
		String newAddress = "50 Govindasalai main road, USA";
		
		given().log().all().queryParam("key", "qaclick123")
				.body("{\r\n" + "\"place_id\":\"" + placeID + "\",\r\n"
						+ "\"address\":\""+newAddress+"\",\r\n" + "\"key\":\"qaclick123\"\r\n" + "}\r\n"
						+ "")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.header("Server", "Apache/2.4.52 (Ubuntu)").body("msg", equalTo("Address successfully updated"));
		
		// Get Place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		System.out.println(getPlaceResponse);
		
		
		// ReusableMethods - Keep JsonPath there are it can be reused for all 
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		
		
		String actualAddress = js1.getString("address");
		System.out.println("Actual Address::" + actualAddress);
		
		Assert.assertEquals(newAddress, actualAddress);
		
		
		
		
		
		
	}

}
