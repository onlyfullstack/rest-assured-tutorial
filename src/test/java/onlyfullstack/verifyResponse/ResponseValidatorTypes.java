package onlyfullstack.verifyResponse;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import onlyfullstack.models.Employee;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResponseValidatorTypes {

    @Test
    public void validateWithJsonPath() {
        String requestEmployeeId = "2";
        RestAssured.given() //precondition is to give the base uri
                .baseUri("http://localhost:8088/")
                .pathParam("employee_id", requestEmployeeId)
                .when() //actual action is to perform the get request
                .get("employees/{employee_id}")
                .then() // will return the ValidatadleResponse from the Response object
                .statusCode(200)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body("id", Matchers.equalTo(2)) // json path of a simple object
                .body("firstName", Matchers.equalTo("only"))
                .body("lastName", Matchers.equalTo("fullstack"))
                .body("salary", Matchers.equalTo(2000))
                .body("email", Matchers.equalTo("onlyfullstack@abc.com"));
    }

    @Test
    public void validateWithExtractedObject() {
        String requestEmployeeId = "2";
        Employee expectedResponse = new Employee(2l, "only", "fullstack", 2000l, "onlyfullstack@abc.com");

        Employee actualResponse = RestAssured.given() //precondition is to give the base uri
                .baseUri("http://localhost:8088/")
                .pathParam("employee_id", requestEmployeeId)
                .when() //actual action is to perform the get request
                .get("employees/{employee_id}")
                .then() // will return the ValidatadleResponse from the Response object
                .statusCode(200)
                .extract()
                .as(Employee.class);

        Assert.assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void validateWithJsonString() throws JSONException {
        String requestEmployeeId = "2";
        String expectedJson = "{\"id\":2,\"firstName\":\"only\",\"lastName\":\"fullstack\",\"salary\":2000,\"email\":\"onlyfullstack@abc.com\"}";
        String response = RestAssured.given() //precondition is to give the base uri
                .baseUri("http://localhost:8088/")
                .pathParam("employee_id", requestEmployeeId)
                .when() //actual action is to perform the get request
                .get("employees/{employee_id}")
                .asString();

        System.out.println(response);
        JSONAssert.assertEquals(expectedJson, response, true);
    }

    @Test
    public void validateWithJsonFromFile() throws IOException, JSONException {
        String requestEmployeeId = "2";
        String expectedJsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/single-employee.json")));
        String actualJsonResponse = RestAssured.given() //precondition is to give the base uri
                .baseUri("http://localhost:8088/")
                .pathParam("employee_id", requestEmployeeId)
                .when() //actual action is to perform the get request
                .get("employees/{employee_id}")
                .asString();

        System.out.println(actualJsonResponse);
        JSONAssert.assertEquals(expectedJsonResponse, actualJsonResponse, true);
    }

    @Test
    public void validateJsonSchema() {
        RestAssured.given()
                .baseUri("http://localhost:8088")
                .get("/employees")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("get-all-employee-json-schema.json"));
    }
}
