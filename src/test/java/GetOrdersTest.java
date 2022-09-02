import static io.restassured.RestAssured.given;
/*
public class GetOrdersTest {
    CreationUserPojo creationUserPojo;
    String token;
    ArrayList <String> id;

    public String getToken() {
        return token;
    }

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
    @DisplayName("Получение заказов авторизированного пользователя")
    public void get_order_with_authorization(){
        //регистрация пользователя
        Client.createUser(creationUserPojo);

        //авторизация пользователя-получение токена
        token = Client.getToken(creationUserPojo);

        //получение id для того, чтобы положить их в создание заказа в качестве ингредиентов
        id = given()
                .header("authorization", token)
                .get("/ingredients")
                .then()
                .extract().path("data._id");

        //создание заказа
        ArrayList<String> someIngredients = new ArrayList<>();

        someIngredients.add(id.get(1));
        someIngredients.add(id.get(3));
        someIngredients.add(id.get(5));

        CreationOrderPOJO burger = new CreationOrderPOJO(someIngredients);


        given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(burger)
                .post("/orders")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .body(burger)
                .post("/orders")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .header("authorization", token)
                .log().all()
                .get("/orders")
                .then()
                .log().all();
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void get_order_without_authorization(){
        //регистрация пользователя
        Client.createUser(creationUserPojo);

        //авторизация пользователя-получение токена
        token = Client.getToken(creationUserPojo);


        //получение id для того, чтобы положить их в создание заказа в качестве ингредиентов
        id = given()
                .header("authorization", token)
                .get("/ingredients")
                .then()
                .extract().path("data._id");

        //создание заказа
        ArrayList<String> someIngredients = new ArrayList<>();

        someIngredients.add(id.get(1));
        someIngredients.add(id.get(3));
        someIngredients.add(id.get(5));

        CreationOrderPOJO burger = new CreationOrderPOJO(someIngredients);

        given()
                .contentType(ContentType.JSON)
                .body(burger)
                .post("/orders")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .body(burger)
                .post("/orders")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .get("/orders")
                .then()
                .log().all()
                .statusCode(401)
                .assertThat().body("message", Matchers.is("You should be authorised"));
    }
}
*/