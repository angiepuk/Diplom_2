package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.CreationUserCredential;

import static io.restassured.RestAssured.given;

public class ClientUpdate {
    public static String token;

    public static Response updateUser(CreationUserCredential creationUserCredential, CreationUserCredential newCreds) {

        token = given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/login")
                .then()
                .extract().path("accessToken");

        return given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(newCreds)
                .patch("/auth/user");
    }

    public static Response updateUserWithoutToken(CreationUserCredential newCreds){
        return given()
                .contentType(ContentType.JSON)
                .body(newCreds)
                .patch("/auth/user");
    }
}
