package onlyfullstack.httpMethods;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class DeleteHttpMethodExample {

    @Test
    public void deleteMethodWithoutBDDApproach() {
        String requestEmployeeId = "2";

        RequestSpecification request = RestAssured.given();
        request.baseUri("http://localhost:8088");
        request.pathParam("employee_id", requestEmployeeId);

        Response response = request.delete("employees/{employee_id}");

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
    }

    @Test
    public void deleteMethodWithBDDApproach() {
        String requestEmployeeId = "1";
        RestAssured.given() //precondition is to give the base uri
                .baseUri("http://localhost:8088/")
                .pathParam("employee_id", requestEmployeeId)
                .when() //actual action is to perform the delete request
                .delete("employees/{employee_id}")
                .then() // will return the ValidatadleResponse from the Response object
                .statusCode(200);
    }
}
