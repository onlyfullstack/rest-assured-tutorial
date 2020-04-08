package onlyfullstack.verifyResponse;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonPathExample {

    String exampleJson;
    JsonPath jsonPath;

    @BeforeClass
    public void loadJsonFile() throws IOException {
        // Read the json from file and store in a String
        exampleJson = new String(Files.readAllBytes(Paths.get("src/test/resources/json-path-example.json")));
        // Create a JsonPath from the input String
        jsonPath = new JsonPath(exampleJson);
    }

    @Test
    public void jsonPathExamples() {
        System.out.println("Nested Value of store.bicycle - \n" + jsonPath.get("store.bicycle"));
        System.out.println("\nAccessing the Array Element - store.book[0]- \n" + jsonPath.get("store.book[0]"));
        System.out.println("\nAccessing the inner property of an array Element - store.book[0]- \n" + jsonPath.get("store.book[0].author"));
    }
}
