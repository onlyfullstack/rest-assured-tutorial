package onlyfullstack.inputBodyWays;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import onlyfullstack.models.Employee;
import org.hamcrest.Matchers;
import org.springframework.core.io.ClassPathResource;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HttpPostBodyWays {

    @Test
    public void postBodyWithJsonString() {
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

    @Test
    public void postBodyWithMap() throws IOException {
        // We can pass the request as json string
        // String expectedJsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/post-request-request-employee.json")));

        Map<String, Object>  employeeJsonAsMap = new HashMap<>();
        employeeJsonAsMap.put("firstName", "Jack");
        employeeJsonAsMap.put("lastName", "Ma");
        employeeJsonAsMap.put("salary", 1000);
        employeeJsonAsMap.put("email", "jack@gmail.com");

        RestAssured.given()
                .baseUri("http://localhost:8088")
                .body(employeeJsonAsMap)
                .contentType(ContentType.JSON)
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo(employeeJsonAsMap.get("firstName")))
                .body("lastName", Matchers.equalTo(employeeJsonAsMap.get("lastName")))
                .body("salary", Matchers.equalTo(employeeJsonAsMap.get("salary")))
                .body("email", Matchers.equalTo(employeeJsonAsMap.get("email")));
    }

    @Test
    public void postBodyWithObject() {
        Employee inputBody = new Employee("only", "fullstack", 2000l, "onlyfullstack@abc.com");

        RestAssured.given()
                .baseUri("http://localhost:8088")
                .body(inputBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/employees")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo("only"))
                .body("lastName", Matchers.equalTo("fullstack"))
                .body("salary", Matchers.equalTo(2000))
                .body("email", Matchers.equalTo("onlyfullstack@abc.com"));
    }

    @Test
    public void postBodyWithJsonFromFile() throws IOException {
        // We can pass the request as json string
        // String expectedJsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/post-request-request-employee.json")));

        ObjectMapper mapper = new ObjectMapper();
        Employee request = mapper.readValue(new ClassPathResource("post-request-input-employee.json").getFile(), Employee.class);

        Employee response = RestAssured.given()
                .baseUri("http://localhost:8088")
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post("/employees")
                .then()
                .extract()
                .as(Employee.class);
        Assert.assertEquals(response, request);
                /*.then()  // BDD approach with comparing individual fields
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo("Sachin"))
                .body("lastName", Matchers.equalTo("Tendulkar"))
                .body("salary", Matchers.equalTo(2000))
                .body("email", Matchers.equalTo("sachin@abc.com"));*/
    }
}
