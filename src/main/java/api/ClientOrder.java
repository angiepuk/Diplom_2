package api;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.CreationOrder;
import pojo.CreationUserCredential;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;

public class ClientOrder {

    public static ArrayList<String> id;
    public static String token;

    @Step("Создание заказа")
    public static Response createOrder(CreationUserCredential creationUserCredential) {

        id = given()
                .get("/ingredients")
                .then()
                .extract().path("data._id");

        token = given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/login")
                .then()
                .extract().path("accessToken");

        CreationOrder allIngredients = new CreationOrder(id);
        return given()
                .header("authorization", token)
                .contentType(ContentType.JSON)
                .body(allIngredients)
                .post("/orders");
    }

    @Step("Создание заказа без авторизации")
    public static Response createOrderWithoutAutho(CreationUserCredential creationUserCredential) {

        id = given()
                .get("/ingredients")
                .then()
                .extract().path("data._id");

        token = given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/login")
                .then()
                .extract().path("accessToken");

        CreationOrder allIngredients = new CreationOrder(id);
        return given()
                .contentType(ContentType.JSON)
                .body(allIngredients)
                .post("/orders");
    }

    @Step("Создание заказа с ингридиентами")
    public static Response createOrderWithIngred(CreationUserCredential creationUserCredential) {

        id = given()
                .get("/ingredients")
                .then()
                .extract().path("data._id");

        token = given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/login")
                .then()
                .extract().path("accessToken");

        ArrayList <String> someIngredients = new ArrayList<>();

        someIngredients.add(id.get(1));
        someIngredients.add(id.get(3));
        someIngredients.add(id.get(5));

        CreationOrder burger = new CreationOrder(someIngredients);
        return given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(burger)
                .post("/orders");
    }
    @Step("Создание заказа без ингридиентов")
    public static Response createOrderWithoutIngred(CreationUserCredential creationUserCredential) {

        id = given()
                .get("/ingredients")
                .then()
                .extract().path("data._id");

        token = given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/login")
                .then()
                .extract().path("accessToken");

        CreationOrder burger = new CreationOrder(null);
        return given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(burger)
                .post("/orders");
    }
    @Step("Создание заказа с неверным хешем ингредиентов")
    public static Response createOrderWithIncorHash(CreationUserCredential creationUserCredential) {

        id = given()
                .get("/ingredients")
                .then()
                .extract().path("data._id");

        token = given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/login")
                .then()
                .extract().path("accessToken");

        ArrayList <String> someIngredients = new ArrayList<>();

        someIngredients.add(id.get(1));
        someIngredients.add("incorrecthash0");


        CreationOrder burger = new CreationOrder(someIngredients);
        return given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(burger)
                .post("/orders");
    }
    @Step("Получение заказов с авторизацией")
    public static Response getOrderWithAutho(CreationUserCredential creationUserCredential) {

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
                .log().all()
                .get("/orders");

}
    @Step("Получение заказов без авторизации")
    public static Response getOrderWithoutAutho() {

        return given()
                .contentType(ContentType.JSON)
                .log().all()
                .get("/orders");
    }
}