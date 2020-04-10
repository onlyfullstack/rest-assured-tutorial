package onlyfullstack.authorization;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import onlyfullstack.models.Student;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OAuth2AuthorizationTest {

    @Test
    public void callOAuth2ProtectedApi() throws JSONException {
        String accessToken = getAccessTokenFromOAuth2();
        Student request = new Student(1l, "Elon Musk", "elonmusk@gmail.com", "pccoe");

        Student response = RestAssured.given()
                .auth()
                .oauth2(accessToken)
                .baseUri("http://localhost:8088")
                .pathParam("student_rollNumber", "2")
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .get("/students/{student_rollNumber}")
                .then()
                .extract()
                .as(Student.class);
        System.out.println("Student api Response - " + response);
        Assert.assertEquals(response, request);
    }

    @Test
    public String getAccessTokenFromOAuth2() throws JSONException {

        Response response =
                RestAssured.given()
                        .baseUri("http://localhost:8088/")
                        .auth().preemptive().basic("rest-assured", "password")
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("grant_type", "password")
                        .formParam("username", "onlyfullstack")
                        .formParam("password", "secret")
                        .when()
                        .post("/oauth/token");

        System.out.println("OAuth Response - " + response.getBody().asString());
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        String accessToken = jsonObject.get("access_token").toString();
        String tokenType = jsonObject.get("token_type").toString();
        System.out.println("Oauth Token with type " + tokenType + "   " + accessToken);
        return accessToken;
    }
}
