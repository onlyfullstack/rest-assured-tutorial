package onlyfullstack.httpMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class PostHttpMethodExample {

    //http://makeseleniumeasy.com/2019/11/19/rest-assured-tutorial-8-bdd-style-in-rest-assured/
    @Test
    public void postMethodWithoutBDDApproach() {
        String requestBody = " {\n" +
                "\"firstName\": \"Elon\",\n" +
                "\"lastName\": \"Musk\",\n" +
                "\"salary\": 3000,\n" +
                "\"email\": \"elonmusk@abc.com\"\n" +
                "}";

        RequestSpecification request = RestAssured.given();
        request.baseUri("http://localhost:8088");
        request.body(requestBody);
        request.contentType(ContentType.JSON);

        Response response = request.post("/employees");

        // Let's print response body.
        String resString = response.asString();
        System.out.println("Response Details : " + resString);

        /*
         * To perform validation on response like status code or value, we need to get
         * ValidatableResponse type of response using then() method of Response
         * interface. ValidatableResponse is also an interface.
         */
        ValidatableResponse validatableResponse = response.then();
        // It will check if status code is 201
        validatableResponse.statusCode(201);
        // It will check if status line is as expected
        validatableResponse.body("id", Matchers.notNullValue());
        validatableResponse.body("firstName", Matchers.equalTo("Elon"));
        validatableResponse.body("lastName", Matchers.equalTo("Musk"));
        validatableResponse.body("salary", Matchers.equalTo(3000));
        validatableResponse.body("email", Matchers.equalTo("elonmusk@abc.com"));
    }

    //https://jsonpath.herokuapp.com/
    @Test
    public void postMethodWithBDDApproach() {
        String requestBody = " {\n" +
                "\"firstName\": \"Elon1\",\n" +
                "\"lastName\": \"Musk1\",\n" +
                "\"salary\": 3000,\n" +
                "\"email\": \"elonmusk@abc.com\"\n" +
                "}";

        RestAssured.given()
                .baseUri("http://localhost:8088")
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo("Elon1"))
                .body("lastName", Matchers.equalTo("Musk1"))
                .body("salary", Matchers.equalTo(3000))
                .body("email", Matchers.equalTo("elonmusk@abc.com"));
    }
}
