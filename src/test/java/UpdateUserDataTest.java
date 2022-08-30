import POJO.Client;
import POJO.CreationUserPojo;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class UpdateUserDataTest {

    CreationUserPojo creationUserPojo;
    CreationUserPojo newEmail;
    String token;

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
                .body(newEmail)
                .delete("/auth/user");
    }

    @Test
    @DisplayName("Обновление пользовательских данных под авторизированным пользователем")
    public void update_user_data_with_authorization() {
        //создание пользователя
        Client.createUser(creationUserPojo);

        //получение токена
        token = Client.getToken(creationUserPojo);

        given()
                .header("authorization", token)
                .log().all()
                .get("/auth/user")
                .then()
                .log().all();


        newEmail = new CreationUserPojo("Kesha@mail.ru", "", "");
        given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(newEmail)
                .when()
                .log().all()
                .patch("/auth/user")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("Обновление пользовательских данных под НЕавторизированным пользователем")
    public void update_user_data_without_authorization() {
        //создание пользователя
        Client.createUser(creationUserPojo);

        //получение токена
        token = Client.getToken(creationUserPojo);

        given()
                .header("authorization", token)
                .log().all()
                .get("/auth/user")
                .then()
                .log().all();


        newEmail = new CreationUserPojo("Maria@mail.ru", "", "");
        given()
                .contentType(ContentType.JSON)
                .body(newEmail)
                .when()
                .log().all()
                .patch("/auth/user")
                .then()
                .log().all()
                .statusCode(401)
                .and()
                .assertThat().body("message", Matchers.is("You should be authorised"));
    }
}
