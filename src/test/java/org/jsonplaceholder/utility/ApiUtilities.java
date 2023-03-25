package org.jsonplaceholder.utility;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class ApiUtilities {
    private final PropertiesRead propertiesRead;
    public ApiUtilities(PropertiesRead propertiesRead){
        this.propertiesRead = propertiesRead;
    }

    /**
     *
     * @param bodyRequest
     * @param URL
     * @return
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
     * @param URL
     * @return
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
     * @param URL
     * @param key
     * @param value
     * @param schemaFile
     * @return
     */
    public Response sendGetRequestQuery(String URL, String key, String value, String schemaFile){
        InputStream schema = getClass().getClassLoader().getResourceAsStream(schemaFile);

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

    /**
     *
     * @param response
     * @param fieldName
     * @return
     */
    public String extractValueBody(Response response, String fieldName) {
        return response.body().jsonPath().get(fieldName).toString();
    }
}

