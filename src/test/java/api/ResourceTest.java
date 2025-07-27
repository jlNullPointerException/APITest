package api;

import api.common.Specifications;
import api.common.TestData;
import api.resource.Data;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ResourceTest {

    int pageNumber = 2;

    @Test
    public void checkPageNumber() {

    }

    @Test
    public void checkAmountPerPage() {

    }

    @Test
    public void checkTotalAmount() {

    }

    @Test
    public void checkSupport() {

    }

    @Test
    public void checkYearSort() {
        Specifications.installSpecification(Specifications
                .requestSpec(), Specifications.responseStatus(200));

        List<Data> resource = given()
                .when()
                .get("api/resource?page=" + pageNumber)
                .then().log().all()
                .extract().body().jsonPath().getList("data", Data.class);

        List<Integer> years = resource.stream().map(Data::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(sortedYears, years);
    }

    @Test
    public void checkPantoneValueFormat() {
        Specifications.installSpecification(Specifications
                .requestSpec(), Specifications.responseStatus(200));

        String regex = "^\\d{2}-\\d{4}$";

        List<Data> resource = given()
                .when()
                .get("api/resource?page=" + pageNumber)
                .then().log().all()
                .extract().body().jsonPath().getList("data", Data.class);

        Assert.assertTrue(resource.stream()
                .allMatch(x -> x.getPantone_value().matches(regex)));
    }

    @Test
    public void checkConformColorAndName() {
        Specifications.installSpecification(Specifications
                .requestSpec(), Specifications.responseStatus(200));

        List<Data> resource = given()
                .when()
                .get("api/resource?page=" + pageNumber)
                .then().log().all()
                .extract().body().jsonPath().getList("data", Data.class);

        Map<String, String> colorAndName = resource.stream()
                .collect(Collectors.toMap(Data::getName, Data::getColor));

        Assert.assertTrue(colorAndName.entrySet().stream()
                .allMatch(x -> x.getValue()
                        .equals(TestData.actualColorMapping.get(x.getKey()))));
    }
}
