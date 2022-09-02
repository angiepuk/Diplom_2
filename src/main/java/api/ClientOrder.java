package api;

import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class ClientOrder {
    public static ArrayList<String> id;

    public static Response getIngredients(){
       return id = given()
               //.header("authorization", token) как будет работать без токена? и будет ли?
               .get("/ingredients")
               .then()
               .log().all()
               .extract().path("data._id");
    }
}
