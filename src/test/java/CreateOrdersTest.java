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

public class CreateOrdersTest {
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
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuthorization() {

        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name)); //зарегистрироваться
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.authorizationUser(new CreationUserCredential(email, password, name)); //залогиниться
        ClientOrder clientOrder = new ClientOrder();
        Response order = clientOrder.createOrder(new CreationUserCredential(email, password, name));
        order.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorization() {

        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.authorizationUser(new CreationUserCredential(email, password, name));
        ClientOrder clientOrder = new ClientOrder();
        Response order = clientOrder.createOrderWithoutAutho(new CreationUserCredential(email, password, name));
        order.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    public void createOrderWithIngredients() {
        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.authorizationUser(new CreationUserCredential(email, password, name));
        ClientOrder clientOrder = new ClientOrder();
        Response order = clientOrder.createOrderWithIngred(new CreationUserCredential(email, password, name));
        order.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients(){
        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.authorizationUser(new CreationUserCredential(email, password, name));
        ClientOrder clientOrder = new ClientOrder();
        Response order = clientOrder.createOrderWithoutIngred(new CreationUserCredential(email, password, name));
        order.then()
                .log().all()
                .statusCode(400)
                .and()
                .assertThat().body("message", Matchers.is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с некорректным хэшем")
    public void createOrderWithIncorrectHash() {
        ClientRegister clientRegister = new ClientRegister();
        clientRegister.createUser(new CreationUserCredential(email, password, name));
        ClientAuth clientAuth = new ClientAuth();
        clientAuth.authorizationUser(new CreationUserCredential(email, password, name));
        ClientOrder clientOrder = new ClientOrder();
        Response order = clientOrder.createOrderWithIncorHash(new CreationUserCredential(email, password, name));
        order.then()
                .log().all()
                .statusCode(500);
    }
}

