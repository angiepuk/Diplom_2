import api.ClientDelete;
import api.ClientRegister;
import io.restassured.http.ContentType;
import pojo.CreationUserCredential;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreationUserTest {
    public String name;
    public String password;
    public String email;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/api";
    }

    @Before
    public void createCredentials() {
        name = CreationUserCredential.creationName();
        email = CreationUserCredential.creationEmail();
        password = CreationUserCredential.creationPassword();
    }

    @After
    public void deleteCredentials() {
        CreationUserCredential creationUserCredential = new CreationUserCredential(email, password, name);
        ClientDelete.deleteUser(creationUserCredential);
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createNewUser() {
        ClientRegister clientRegister = new ClientRegister();
        Response user = clientRegister.createUser(new CreationUserCredential(email, password, name));
        user.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    public void createSimilarUser() {
        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        Response user = clientRegister.createUser(new CreationUserCredential(email, password, name));
        user.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", Matchers.is("User already exists"));

    }

    @Test
    @DisplayName("Создание нового пользователя без одного поля")
    public void createUserWithoutOneField() {
        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        Response user = clientRegister.createUser(new CreationUserCredential(email, "", name));
        user.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", Matchers.is("Email, password and name are required fields"));
    }
}