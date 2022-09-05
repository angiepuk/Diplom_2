import api.ClientAuth;
import api.ClientDelete;
import api.ClientOrder;
import api.ClientRegister;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.CreationUserCredential;

public class GetOrdersTest {
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
    @DisplayName("Получение заказов авторизированного пользователя")
    public void getOrderWithAuthorization() {
        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.authorizationUser(new CreationUserCredential(email, password, name));
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.createOrderWithIngred(new CreationUserCredential(email, password, name));
        clientOrder.createOrderWithIngred(new CreationUserCredential(email, password, name));
        Response orders = clientOrder.getOrderWithAutho(new CreationUserCredential(email, password, name));
        orders.then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void getOrderWithoutAuthorization(){
        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.authorizationUser(new CreationUserCredential(email, password, name));
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.createOrderWithIngred(new CreationUserCredential(email, password, name));
        clientOrder.createOrderWithIngred(new CreationUserCredential(email, password, name));
        Response orders = clientOrder.getOrderWithoutAutho();
        orders.then()
                .statusCode(401)
                .assertThat()
                .body("message", Matchers.is("You should be authorised"));
    }
}