package onlyfullstack.verifyResponse;

import io.restassured.path.xml.XmlPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlPathExample {

    String exampleXml;
    XmlPath xmlPath;

    @BeforeClass
    public void loadJsonFile() throws IOException {
        exampleXml = new String(Files.readAllBytes(Paths.get("src/test/resources/xml-path-example.xml")));
        xmlPath = new XmlPath(exampleXml);
    }

    @Test
    public void xmlPathExamples() {
        System.out.println("Nested Value of store.bicycle - \n" + xmlPath.get("store.bicycle"));
        System.out.println("\nAccessing the Array Element - store.book[0]- \n" + xmlPath.get("store.book[0]"));
        System.out.println("\nAccessing the inner property of an array Element - store.book[0]- \n" + xmlPath.get("store.book[0].author"));
    }
}
