package api;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.CreationUserCredential;

import static io.restassured.RestAssured.given;

public class ClientRegister {
    //здесь у меня типа только метод ручки register
    public static Response createUser(CreationUserCredential creationUserCredential){
        return given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/register");
    }

}

