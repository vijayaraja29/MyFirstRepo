package basics;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReusableMethods;
import files.payload;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		// Add Book
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().queryParam("Content-Type", "application/json")
		.body(payload.addBook(isbn, aisle))
		.when().post("Library/Addbook.php").then().log().all()
		.statusCode(200).extract().response().asString();

		System.out.println(response);

		JsonPath rawToJson = ReusableMethods.rawToJson(response);
		String id = rawToJson.get("ID");
		System.out.println(id);
	
	    // Delete Book
		String deleteResponse = given().queryParam("Content-Type", "application/json")
		.body(payload.deleteBook(id)).when().delete("Library/DeleteBook.php")
		.then().log().all().statusCode(200).extract().response().asString();		
	     System.out.println(deleteResponse);	
	}

	@DataProvider(name="BooksData")
	public Object[][] getData() {
		
		// Array = Collections of Elements
		// multidimensional Array = Collection of Arrays
		return new Object[][] {{"zxvcw","1234"},{"asdfgw","4567"},{"qwertw","7890"},{"mbvbw","8765"},{"rtyruw","4565"}};

	}

}
