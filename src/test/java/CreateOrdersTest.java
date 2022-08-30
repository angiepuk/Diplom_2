import POJO.Client;
import POJO.CreationOrderPOJO;
import POJO.CreationUserPojo;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;

public class CreateOrdersTest {
    CreationUserPojo creationUserPojo;
    String token;
    ArrayList <String> id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";
    }

    @Before
    public void create_credentials() {
        creationUserPojo = CreationUserPojo.getRandomCredentials();
    }

    @After
    public void delete_credentials(){
        given()
                .header("authorization", token)
                .contentType(ContentType.JSON)
                .body(creationUserPojo)
                .delete("/auth/user");
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void create_order_with_authorization(){
        //регистрация пользователя
        Client.createUser(creationUserPojo);

        //авторизация пользователя-получение токена
        token = Client.getToken(creationUserPojo);

        //получение id для того, чтобы положить их в создание заказа в качестве ингредиентов
        id = given()
                .header("authorization", token)
                .log().all()
                .get("/ingredients")
                .then()
                .log().all()
                .extract().path("data._id");

        //создание заказа
        CreationOrderPOJO allIngredients = new CreationOrderPOJO(id);
        given()
                .header("authorization", token)
                .contentType(ContentType.JSON)
                .log().all()
                .body(allIngredients)
                .post("/orders")
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void create_order_without_authorization(){
        //регистрация пользователя
        Client.createUser(creationUserPojo);

        //авторизация пользователя-получение токена
        token = Client.getToken(creationUserPojo);

        //получение id для того, чтобы положить их в создание заказа в качестве ингредиентов
        id = given()
                .header("authorization", token)
                .log().all()
                .get("/ingredients")
                .then()
                .log().all()
                .extract().path("data._id");

        //создание заказа
        CreationOrderPOJO allIngredients = new CreationOrderPOJO(id);
        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(allIngredients)
                .post("/orders")
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    public void create_order_with_ingredients(){
        //регистрация пользователя
        Client.createUser(creationUserPojo);

        //авторизация пользователя-получение токена
        token = Client.getToken(creationUserPojo);

        //получение id для того, чтобы положить их в создание заказа в качестве ингредиентов
        id = given()
                .header("authorization", token)
                .log().all()
                .get("/ingredients")
                .then()
                .log().all()
                .extract().path("data._id");

        //создание заказа

        ArrayList <String> someIngredients = new ArrayList<>();

        someIngredients.add(id.get(1));
        someIngredients.add(id.get(3));
        someIngredients.add(id.get(5));

        CreationOrderPOJO burger = new CreationOrderPOJO(someIngredients);
        given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .log().all()
                .body(burger)
                .post("/orders")
                .then()
                .log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void create_order_without_ingredients(){
        //регистрация пользователя
        Client.createUser(creationUserPojo);

        //авторизация пользователя-получение токена
        token = Client.getToken(creationUserPojo);

        //получение id для того, чтобы положить их в создание заказа в качестве ингредиентов
        id = given()
                .header("authorization", token)
                .log().all()
                .get("/ingredients")
                .then()
                .log().all()
                .extract().path("data._id");

        //создание заказа
        CreationOrderPOJO allIngredients = new CreationOrderPOJO(null);
        given()
                .header("authorization", token)
                .contentType(ContentType.JSON)
                .log().all()
                .body(allIngredients)
                .post("/orders")
                .then()
                .log().all()
                .statusCode(400)
                .and()
                .assertThat().body("message", Matchers.is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с некорректным хэшем")
    public void create_order_with_incorrect_hash(){
        //регистрация пользователя
        Client.createUser(creationUserPojo);

        //авторизация пользователя-получение токена
        token = Client.getToken(creationUserPojo);

        //получение id для того, чтобы положить их в создание заказа в качестве ингредиентов
        id = given()
                .header("authorization", token)
                .log().all()
                .get("/ingredients")
                .then()
                .log().all()
                .extract().path("data._id");

        //создание заказа

        ArrayList <String> someIngredients = new ArrayList<>();

        someIngredients.add(id.get(1));
        someIngredients.add("incorrecthash0");


        CreationOrderPOJO burger = new CreationOrderPOJO(someIngredients);
        given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .log().all()
                .body(burger)
                .post("/orders")
                .then()
                .log().all()
                .statusCode(500);
    }
}

