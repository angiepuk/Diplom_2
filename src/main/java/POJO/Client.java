package POJO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Client {

    public static Response createUser(CreationUserPojo creationUserPojo){
        return given()
                .contentType(ContentType.JSON)
                .body(creationUserPojo)
                .when()
                .post("/auth/register");
    }
    public static Response authorizationUser(CreationUserPojo creationUserPojo){
        return given()
                .contentType(ContentType.JSON)
                .body(creationUserPojo)
                .when()
                .post("/auth/login");
    }

    public static Response deleteUser(CreationUserPojo creationUserPojo) {
        return given()
                .contentType(ContentType.JSON)
                .body(creationUserPojo)
                .delete("/auth/user");
    }

    public static String getToken(CreationUserPojo creationUserPojo){
        return given()
                .contentType(ContentType.JSON)
                .body(creationUserPojo)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().path("accessToken");
    }
 }

