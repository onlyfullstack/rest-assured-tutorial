package onlyfullstack.httpMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class PutHttpMethodExample {

    @Test
    public void putMethodWithoutBDDApproach() {
        String requestBody = " {\n" +
                "\"firstName\": \"Shivansh\",\n" +
                "\"lastName\": \"Oza\",\n" +
                "\"salary\": 5000,\n" +
                "\"email\": \"shivansh@abc.com\"\n" +
                "}";
        String requestEmployeeId = "1";
        RequestSpecification request = RestAssured.given()
                .pathParam("employee_id", requestEmployeeId);
        request.baseUri("http://localhost:8088/");
        request.body(requestBody);
        request.contentType(ContentType.JSON);

        Response response = request.put("employees/{employee_id}");

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
        validatableResponse.statusCode(200);
        // It will check if status line is as expected
        validatableResponse.body("id", Matchers.notNullValue());
        validatableResponse.body("firstName", Matchers.equalTo("Shivansh"));
        validatableResponse.body("lastName", Matchers.equalTo("Oza"));
        validatableResponse.body("salary", Matchers.equalTo(5000));
        validatableResponse.body("email", Matchers.equalTo("shivansh@abc.com"));
    }

    @Test
    public void putMethodWithBDDApproach() {
        String requestBody = " {\n" +
                "\"firstName\": \"Suresh\",\n" +
                "\"lastName\": \"Oza\",\n" +
                "\"salary\": 10000,\n" +
                "\"email\": \"suresh@abc.com\"\n" +
                "}";
        String requestEmployeeId = "2";

        RestAssured.given()
                .baseUri("http://localhost:8088")
                .pathParam("employee_id", requestEmployeeId)
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .put("/employees/{employee_id}")
                .then()
                .statusCode(200)
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo("Suresh"))
                .body("lastName", Matchers.equalTo("Oza"))
                .body("salary", Matchers.equalTo(10000))
                .body("email", Matchers.equalTo("suresh@abc.com"));
    }
}