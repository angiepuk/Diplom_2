package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.CreationUserCredential;

import static io.restassured.RestAssured.given;

public class ClientAuth {

    public static Response authorizationUser(CreationUserCredential creationUserCredential){
        return given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/login");
    }
}
