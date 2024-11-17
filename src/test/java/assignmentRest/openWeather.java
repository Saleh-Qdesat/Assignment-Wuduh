package assignmentRest;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import java.util.HashMap;

public class openWeather {

    private static String baseURL = "https://api.openweathermap.org";

    @Test
    public void checkStatusCode(){
        HashMap<String,String> queries = new HashMap<>();
        queries.put("q", "London");
        queries.put("appid", "b87eae039d0f11a63a0766484e15cbaa");

        given().baseUri(baseURL)
                .queryParams(queries)
                .when().get("/data/2.5/weather")
                .then().statusCode(200);

    }

    @Test
    public void checkResponseTime(){
        HashMap<String,String> queries = new HashMap<>();
        queries.put("q", "London");
        queries.put("appid", "b87eae039d0f11a63a0766484e15cbaa");

        given().baseUri(baseURL)
                .queryParams(queries)
                .when().get("/data/2.5/weather")
                .then().time(lessThan(2000L));
    }

    @Test
    public void testSchemaValidation(){
        HashMap<String,String> queries = new HashMap<>();
        queries.put("q", "London");
        queries.put("appid", "b87eae039d0f11a63a0766484e15cbaa");

        given().baseUri(baseURL)
                .queryParams(queries)
                .when().get("/data/2.5/weather")
                .then().assertThat().body(matchesJsonSchemaInClasspath("weather-schema.json"));
    }

    @Test
    public void checkDataVerification(){
        HashMap<String,String> queries = new HashMap<>();
        queries.put("q", "London");
        queries.put("appid", "b87eae039d0f11a63a0766484e15cbaa");

        given().baseUri(baseURL)
                .queryParams(queries)
                .when().get("/data/2.5/weather")
                .then()
                .body("name", equalTo("London"), "cod", equalTo(200), "coord.lon", equalTo(-0.1257F)
                        , "coord.lat", equalTo(51.5085F) , "sys.country", equalTo("GB"));
    }



}
