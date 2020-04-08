package onlyfullstack.httpMethods;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class GetHttpMethodExample {

    @Test
    public void getMethodWithoutBDDApproach() {
        RequestSpecification request = RestAssured.given();
        request.baseUri("http://localhost:8088");

        Response response = request.get("/employees");

        // Let's print response body.
        String responseString = response.asString();
        System.out.println("Response Details : " + responseString);

        /*
         * To perform validation on response like status code or value, we need to get
         * ValidatableResponse type of response using then() method of Response
         * interface. ValidatableResponse is also an interface.
         */
        ValidatableResponse validatableResponse = response.then();
        // It will check if status code is 200
        validatableResponse.statusCode(200);
        // It will check if status line is as expected
        validatableResponse.header("Content-Type", "application/json;charset=UTF-8");
        validatableResponse.body("$", Matchers.hasSize(3));
        validatableResponse.body("[0].id", Matchers.equalTo(1));
        validatableResponse.body("[0].firstName", Matchers.equalTo("saurabh"));
        validatableResponse.body("[0].lastName", Matchers.equalTo("oza"));
        validatableResponse.body("[0].salary", Matchers.equalTo(1000));
        validatableResponse.body("[0].email", Matchers.equalTo("saurabh@abc.com"));
    }

    //https://jsonpath.herokuapp.com/
    @Test
    public void getMethodWithBDDApproach() {
        RestAssured.given() //precondition is to give the base uri
                .baseUri("http://localhost:8088/")
                .when() //actual action is to perform the get request
                .get("employees")
                .then() // will return the ValidatadleResponse from the Response object
                .statusCode(200)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].firstName", Matchers.equalTo("saurabh"))
                .body("[0].lastName", Matchers.equalTo("oza"))
                .body("[0].salary", Matchers.equalTo(1000))
                .body("[0].email", Matchers.equalTo("saurabh@abc.com"));
    }

    @Test
    public void getWithIdMethodWithBDDApproach() {
        String requestEmployeeId = "2";
        RestAssured.given() //precondition is to give the base uri
                .baseUri("http://localhost:8088/")
                .pathParam("employee_id", requestEmployeeId)
                .when() //actual action is to perform the get request
                .get("employees/{employee_id}")
                .then() // will return the ValidatadleResponse from the Response object
                .statusCode(200)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body("id", Matchers.equalTo(2))
                .body("firstName", Matchers.equalTo("only"))
                .body("lastName", Matchers.equalTo("fullstack"))
                .body("salary", Matchers.equalTo(2000))
                .body("email", Matchers.equalTo("onlyfullstack@abc.com"));
    }

    //https://www.liquid-technologies.com/online-json-to-schema-converter
    @Test
    public void getMethodValidateJsonSchema() {
        RestAssured.given().baseUri("http://localhost:8088/employees")
                .get().then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("get-all-employee-json-schema.json"));
    }
}
