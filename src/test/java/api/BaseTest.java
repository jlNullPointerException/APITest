package api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {
    @BeforeMethod
    protected void beforeMethod(Method method) {
        RestAssured.reset();
    }
}

