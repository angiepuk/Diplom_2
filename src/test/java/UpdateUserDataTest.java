import api.ClientAuth;
import api.ClientDelete;
import api.ClientRegister;
import api.ClientUpdate;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CreationUserCredential;

public class UpdateUserDataTest {
    public String name;
    public String password;
    public String email;
    CreationUserCredential newCreds;

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
        CreationUserCredential creationUserCredential = newCreds;
        ClientDelete.deleteUser(creationUserCredential);
    }

    @Test
    @DisplayName("Обновление пользовательских данных под авторизированным пользователем")
    public void update_user_data_with_authorization() {
        ClientRegister clientRegister = new ClientRegister();
        ClientAuth clientAuth = new ClientAuth();
        ClientUpdate clientUpdate = new ClientUpdate();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        clientAuth.authorizationUser(new CreationUserCredential(email, password, name));
        newCreds = new CreationUserCredential("Uruk@mail.ru", "Nickolay", "Reptail");
        Response update = clientUpdate.updateUser(new CreationUserCredential(email, password, name), newCreds);
        update.then().statusCode(200).and().assertThat().body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Обновление пользовательских данных под НЕавторизированным пользователем")
    public void update_user_data_without_authorization() {
        ClientRegister clientRegister = new ClientRegister();
        newCreds = new CreationUserCredential(email, password, name);
        clientRegister.createUser(newCreds);
        ClientUpdate clientUpdate = new ClientUpdate();
        Response updateWithoutAuth = clientUpdate.updateUserWithoutToken(newCreds);
        updateWithoutAuth.then().assertThat()
                .statusCode(401).and().body("message", Matchers.is("You should be authorised"));
    }
}