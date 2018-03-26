package com.jedivision.acceptance.rest.utils.support;

import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.ConnectException;

import static com.jayway.restassured.RestAssured.given;

@Component
public class RestAssureSupport {

    public String getResponseJSON(String path) throws ConnectException {
        return given()
               .when()
                    .get(path)
               .then()
                    .statusCode(HttpStatus.SC_OK)
                    .contentType(ContentType.JSON)
                    .extract().asString();
    }
}
