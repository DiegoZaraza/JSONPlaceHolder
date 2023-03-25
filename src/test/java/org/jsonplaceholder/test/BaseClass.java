package org.jsonplaceholder.test;

import io.restassured.RestAssured;
import org.jsonplaceholder.models.Posts;
import org.jsonplaceholder.utility.ApiUtilities;
import org.jsonplaceholder.utility.PropertiesRead;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseClass {
	public static String baseURL;
	public static long timeResponse;
	public static ApiUtilities apiUtilities;
	public static PropertiesRead propertiesRead;
	private Posts posts;

	/**
	 *
	 */
	@BeforeClass
	public void setupApplication()
	{
		Reporter.log("=====Base Class Setup Started=====", true);
		propertiesRead = new PropertiesRead();
		apiUtilities = new ApiUtilities(propertiesRead);
		propertiesRead.getPropertiesFile("config.properties");

		RestAssured.baseURI = propertiesRead.returnProperties("URL");
		timeResponse = Long.parseLong(propertiesRead.returnProperties("TIME_RESPONSE"));
		posts = new Posts();
		Reporter.log("=====Base Class Setup Complete=====", true);

	}

	/**
	 *
	 */
	@AfterClass
	public void closeApplication()
	{
		Reporter.log("=====Session End=====", true);
	}

	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}
}
