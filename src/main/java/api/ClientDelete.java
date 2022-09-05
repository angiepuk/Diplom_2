package api;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.CreationUserCredential;
import static io.restassured.RestAssured.given;

public class ClientDelete {

    public static String token;

    @Step("Удаление пользователя")
    public static Response deleteUser(CreationUserCredential creationUserCredential) {
        token = given()
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .when()
                .post("/auth/login")
                .then()
                .extract().path("accessToken");

        return given()
                .header("authorization", token)
                .contentType(ContentType.JSON)
                .body(creationUserCredential)
                .delete("/auth/user");
    }

}
