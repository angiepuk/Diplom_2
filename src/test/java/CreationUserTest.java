import api.ClientDelete;
import api.ClientRegister;
import pojo.CreationUserCredential;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreationUserTest {
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
    @DisplayName("Создание уникального пользователя")
    public void create_new_user() {
        ClientRegister clientRegister = new ClientRegister();
        Response createUniqueUser = clientRegister.createUser(new
                CreationUserCredential(email, password, name));
        createUniqueUser.then().assertThat().statusCode(200).and().body("success", Matchers.is(true));
    }


    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    public void create_similar_user() {
        //регистрация
        ClientRegister clientRegister = new ClientRegister();
        Response createFirstUser = clientRegister.createUser(new
                CreationUserCredential(email, password, name));
        Response creationSimilar = clientRegister.createUser(new
                CreationUserCredential(email, password, name));
        creationSimilar.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", Matchers.is("User already exists"));

    }

    @Test
    @DisplayName("Создание нового пользователя без одного поля")
    public void create_user_without_one_field() {
        ClientRegister clientRegister = new ClientRegister();
        Response creationWithoutField = clientRegister.createUser(new CreationUserCredential(email, password, null));
                creationWithoutField.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", Matchers.is("Email, password and name are required fields"));
    }

}