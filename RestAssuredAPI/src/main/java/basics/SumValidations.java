package basics;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidations {

	// Verify if Sum of all Course prices matches with Purchase Amount
	@Test
	public void sumofCourses() {
		int sum = 0;
		JsonPath js =new JsonPath(payload.coursePrice());
		int courseCount = js.getInt("courses.size()");
		
		for (int i = 0; i < courseCount; i++) {
			
			int coursePrice = js.get("courses["+i+"].price");
			
			int courseCopy = js.get("courses["+i+"].copies");
			
			int amount = coursePrice * courseCopy;
			
			System.out.println(amount);
			
			sum = sum + amount;			
		}	
		
		System.out.println(sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);
		
	}
	
}
