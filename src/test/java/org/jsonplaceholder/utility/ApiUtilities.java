package org.jsonplaceholder.utility;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class ApiUtilities {
    public ApiUtilities(PropertiesRead propertiesRead){
    }

    /**
     *
     */
    public Response sendPostRequest(String bodyRequest, String URL){
        return  given()
                .contentType(ContentType.JSON)
                .and()
                .body(bodyRequest)
                .when()
                .post(URL)
                .then()
                .extract().response();
    }

    /**
     *
     */
    public Response sendGetRequest(String URL){
        return given().
                contentType(ContentType.JSON).
                when().
                get(URL).
                then().
                extract().
                response();
    }

    /**
     *
     */
    public Response sendGetRequestQuery(String URL, String key, String value, String schemaFile){
        InputStream schema = getClass().getClassLoader().getResourceAsStream(schemaFile);

        assert schema != null;
        return given().
                contentType(ContentType.JSON).
                param(key, value).
                when().
                get(URL).
                then().
                and().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema)).
                extract().
                response();
    }
}

