import POJO.Client;
import POJO.CreationUserPojo;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
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
    @DisplayName("Авторизация пользователя под существующими кредами")
    public void authorization_user() {
        //регистрация
        Client.createUser(creationUserPojo);

        //авторизация под этим пользователем
        Response authorization = Client.authorizationUser(creationUserPojo);
        authorization.then().statusCode(200).and().assertThat().body("accessToken", Matchers.startsWith("Bearer"));
    }

    @Test
    @DisplayName("Авторизация пользователя с несуществующими кредами")
    public void authorization_user_with_incorrect_fields() {
        //регистрация
        Client.createUser(creationUserPojo);

        //авторизация под пользователем с неправильным именем и паролем
        CreationUserPojo creationUserPojo = new CreationUserPojo("incorrect@yandex.ru", "Vot", "Unknown");
        Response authorization = Client.authorizationUser(creationUserPojo);
        authorization.then().statusCode(401).and().assertThat().body("message", Matchers.is("email or password are incorrect"));
    }
}
