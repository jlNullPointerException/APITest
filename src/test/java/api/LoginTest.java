package api;

import api.common.Specifications;
import api.login.ErrorLogin;
import api.login.LoginData;
import api.login.SuccessfulLogin;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LoginTest extends BaseTest{
    private static final String PATH = "login";

   @Test
    public void successful() {
       String okToken = "QpwL5tke4Pnpja7X4";
       LoginData user = new LoginData("eve.holt@reqres.in", "cityslicka");

       Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

       String responseToken = given()
               .body(user)
               .when()
               .post(PATH)
               .then().log().all()
               .extract().as(SuccessfulLogin.class).getToken();

       Assert.assertEquals(responseToken, okToken);
   }

   @Test
    public void emptyPassword() {
       String errorText = "Missing password";
       LoginData user = new LoginData("eve.holt@reqres.in", "");

       Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(400));

       String responseError = given()
               .body(user)
               .when()
               .post(PATH)
               .then().log().all()
               .extract().as(ErrorLogin.class).getError();

       Assert.assertEquals(responseError, errorText);
   }

   @Test
   public void emptyEmail() {
      String errorText = "Missing email or username";
      LoginData user = new LoginData("", "cityslicka");

      Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(400));

      String responseError = given()
              .body(user)
              .when()
              .post(PATH)
              .then().log().all()
              .extract().as(ErrorLogin.class).getError();

      Assert.assertEquals(responseError, errorText);
   }

@Test
public void emptyFields() {
   String errorText = "Missing email or username";
   LoginData user = new LoginData("", "");

   Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(400));

   String responseError = given()
           .body(user)
           .when()
           .post(PATH)
           .then().log().all()
           .extract().as(ErrorLogin.class).getError();

   Assert.assertEquals(responseError, errorText);
   }
}
