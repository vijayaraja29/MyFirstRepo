package basics;

import java.util.Iterator;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.coursePrice());
		
		// Print No of courses returned by API
		int courseCount = js.getInt("courses.size()");
		System.out.println(courseCount);
		
		// Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		// Print Title of the first course
		String firstCourseTitle = js.getString("courses[0].title");
		System.out.println(firstCourseTitle);
		
		// Print All course titles and their respective Prices
		System.out.println("Print All course titles and their respective Prices");
		 for (int i = 0; i < courseCount; i++) {
			String CourseName = js.get("courses["+i+"].title");
			System.out.println(CourseName);
			
			System.out.println(js.get("courses["+i+"].price").toString());  			 
		}
		 
		// Print no of copies sold by RPA Course 
		System.out.println("Print no of copies sold by RPA Course");
		 for (int i = 0; i < courseCount; i++) {
			 String CourseName = js.get("courses["+i+"].title");
			 // Condition to verify the Title and get the no. of copies sold
			 if (CourseName.equalsIgnoreCase("RPA")) {
				 System.out.println(js.get("courses["+i+"].copies").toString());
				 break;
			}			 
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
	}

}
