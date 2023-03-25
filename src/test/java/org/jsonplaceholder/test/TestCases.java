package org.jsonplaceholder.test;


import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.Random;

public class TestCases extends BaseClass {
    private Response response;
    private String email;

    /**
     *
     */
    @Test(priority = 0)
    public void getEmail(){
        Reporter.log("Test Cases - Get Email", true);
        Random random = new Random();;
        getPosts().setUserId(random.nextInt(10 - 1 + 1) + 1);
        setResponse(apiUtilities.sendGetRequest("users/" + getPosts().getUserId()));
        email = getResponse().jsonPath().getString("email");
        Reporter.log("Email - " + email, true);
    }

    /**
     *
     */
    @Test(priority = 1)
    public void getPostUser(){
        Reporter.log("Test Cases - Get Post User", true);
        setResponse(apiUtilities.sendGetRequestQuery("posts/", "userId", String.valueOf(getPosts().getUserId()), "PostUserSchema.json"));
        Assert.assertEquals(getResponse().statusCode(),200 );
    }

    /**
     *
     */
    @Test(priority = 1)
    public void postNewPost(){
        Reporter.log("Test Cases - Post new Post", true);

        Faker faker = new Faker();
        getPosts().setTitle(faker.book().title());
        getPosts().setBody(faker.book().author());

        setResponse(apiUtilities.sendPostRequest(new Gson().toJson(getPosts()).toString(), "posts"));
        Assert.assertEquals(getResponse().statusCode(),201 );

        Assert.assertEquals(getResponse().jsonPath().getString("userId"), String.valueOf(getPosts().getUserId()));
    }

    /**
     *
     * @return
     */
    public Response getResponse() {
        return response;
    }

    /**
     *
     * @param response
     */
    public void setResponse(Response response) {
        this.response = response;
    }
}
