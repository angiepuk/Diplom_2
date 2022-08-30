import POJO.Client;
import POJO.CreationUserPojo;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreationUserTest {

    CreationUserPojo creationUserPojo;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";
    }

    @Before
    public void create_credentials() {
        creationUserPojo = CreationUserPojo.getRandomCredentials();
    }

    @After
    public void delete_credentials() {
        Client.deleteUser(creationUserPojo);
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void create_new_user() {
        //регистрация
        Response creation = Client.createUser(creationUserPojo);
        creation.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    public void create_similar_user() {
        //регистрация
        Response creation = Client.createUser(creationUserPojo);
        creation.then().assertThat().statusCode(200);

        //регистрация такого же пользователя
        Response creationSimilar = Client.createUser(creationUserPojo);
        creationSimilar.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", Matchers.is("User already exists"));

    }

    @Test
    @DisplayName("Создание нового пользователя без одного поля")
    public void create_user_without_one_field() {
        CreationUserPojo creationUserPojo = new CreationUserPojo("", "tototo1111", "Petrovka");
        Response creation = Client.createUser(creationUserPojo);
        creation.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", Matchers.is("Email, password and name are required fields"));
    }
}
