import api.ClientAuth;
import api.ClientDelete;
import api.ClientRegister;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CreationUserCredential;

public class LoginUserTest {

    public String name;
    public String password;
    public String email;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";
    }

    @Before
    public void create_credentials() {
        name = CreationUserCredential.creationName();
        email = CreationUserCredential.creationEmail();
        password = CreationUserCredential.creationPassword();
    }

    @After
    public void delete_credentials() {
        CreationUserCredential creationUserCredential = new CreationUserCredential(email, password, name);
        ClientDelete.deleteUser(creationUserCredential);
    }

    @Test
    @DisplayName("Авторизация пользователя под существующими кредами")
    public void authorization_user() {
        ClientRegister clientRegister = new ClientRegister();
        ClientAuth clientAuth = new ClientAuth();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        Response authorization  = clientAuth.authorizationUser(new CreationUserCredential(email, password, name));
        authorization.then().statusCode(200).and().assertThat().body("accessToken", Matchers.startsWith("Bearer"));
    }

    @Test
    @DisplayName("Авторизация пользователя с несуществующими кредами")
    public void authorization_user_with_incorrect_fields() {
        ClientRegister clientRegister = new ClientRegister();
        ClientAuth clientAuth = new ClientAuth();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        Response authorization =  clientAuth.authorizationUser(new CreationUserCredential("incorrect@yandex.ru", "Vot", "Unknown"));
        authorization.then().statusCode(401).and().assertThat().body("message", Matchers.is("email or password are incorrect"));
    }
}
