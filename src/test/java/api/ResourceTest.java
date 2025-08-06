package api;

import api.common.Specifications;
import api.common.TestUtils;
import api.resource.Data;
import api.resource.Root;
import api.resource.SuccessfulUpdate;
import api.resource.Support;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ResourceTest extends BaseTest{
    private static final int PAGE_NUMBER = 2;
    private static final int RESOURCE_ID = 4;
    private static final String COMMON_PATH = "resource";

    @Test
    public void checkPageNumber() {
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

        int page = given()
                .when()
                .get(COMMON_PATH + "?page=" + PAGE_NUMBER)
                .then().log().all()
                .extract().body().jsonPath().getInt("page");

        Assert.assertEquals(page, PAGE_NUMBER);
    }

    @Test
    public void checkAmountPerPage() {
        int expectedAmount = 6;

        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

        Response response = given()
                .when()
                .get(COMMON_PATH + "?page=" + PAGE_NUMBER)
                .then().log().all()
                .extract().response();

        int perPage = response.jsonPath().getInt("per_page");
        int amountInData = response.jsonPath().getList("data", Data.class).size();

        Assert.assertEquals(perPage, expectedAmount);
        Assert.assertEquals(amountInData, perPage);
    }

    @Test
    public void checkTotalAmount() {
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

        Root response = given()
                .when()
                .get(COMMON_PATH + "?page=" + PAGE_NUMBER)
                .then().log().all()
                .extract().response().as(Root.class);

        int perPage = response.getPer_page();
        int totalPages = response.getTotal_pages();
        int total = response.getTotal();

        Assert.assertEquals(total, perPage * totalPages);

    }

    @Test
    public void checkSupport() {
        String expectedUrl = "https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral";
        String expectedText = "Tired of writing endless social media content? Let Content Caddy generate it for you.";

        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

        Support support = given()
                .when()
                .get(COMMON_PATH + "?page=" + PAGE_NUMBER)
                .then().log().all()
                .extract().response().as(Root.class).getSupport();

        String actualUrl = support.getUrl();
        String actualText = support.getText();

        Assert.assertEquals(actualUrl, expectedUrl);
        Assert.assertEquals(actualText, expectedText);
    }

    @Test
    public void checkYearSort() {
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

        List<Data> resource = given()
                .when()
                .get(COMMON_PATH + "?page=" + PAGE_NUMBER)
                .then().log().all()
                .extract().body().jsonPath().getList("data", Data.class);

        List<Integer> years = resource.stream().map(Data::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(years, sortedYears);
    }

    @Test
    public void checkPantoneValueFormat() {
        String regex = "^\\d{2}-\\d{4}$";

        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

        List<Data> resource = given()
                .when()
                .get(COMMON_PATH + "?page=" + PAGE_NUMBER)
                .then().log().all()
                .extract().body().jsonPath().getList("data", Data.class);

        Assert.assertTrue(resource.stream()
                .allMatch(x -> x.getPantone_value().matches(regex)));
    }

    @Test
    public void checkConformColorAndName() {
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

        List<Data> resource = given()
                .when()
                .get(COMMON_PATH + "?page=" + PAGE_NUMBER)
                .then().log().all()
                .extract().body().jsonPath().getList("data", Data.class);

        Map<String, String> colorAndName = resource.stream()
                .collect(Collectors.toMap(Data::getName, Data::getColor));

        Assert.assertTrue(colorAndName.entrySet().stream()
                .allMatch(x -> x.getValue()
                        .equals(TestUtils.actualColorMapping.get(x.getKey()))));
    }

    @Test
    public void patchResource() {
      Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(200));

        String expectedTime = TestUtils.getCurrentTime();
        String responseTime = given()
                .when()
                .patch(COMMON_PATH + "/" + RESOURCE_ID)
                .then().log().all()
                .extract().response().as(SuccessfulUpdate.class).getUpdatedAt();

        Assert.assertEquals(TestUtils.roundMinuteDateTime(responseTime), TestUtils.roundMinuteDateTime(expectedTime));
    }

    @Test
    public void deleteResource() {
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseStatus(204));
        given()
                .when()
                .delete(COMMON_PATH + "?id=" + RESOURCE_ID)
                .then().log().all();
    }
}
