package TheMovieDatabase;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;


import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import static io.restassured.RestAssured.baseURI;

public class API_Test {

    RequestSpecification reqSpec;
    String authenticity_token;

    @BeforeClass
    public void SetUp() {
        baseURI = "https://www.themoviedb.org";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5Nzg2NzI1ZjBiZmNkMjE3NzYxZmVlZGJkYTI5YTQ5MyIsInN1YiI6IjY2MzUxZTNjNDcwZWFkMDEyNTExY2IwNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xMGIVWSwV7Lq7N_Uoun82Wh1Svp-Ox7wPbeiGMJ720E")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void PreLogin() {

        authenticity_token =
                given()

                        .when()
                        .get("/login")
                        .then()
                        .statusCode(200)
                        .extract().response().htmlPath().prettyPrint().substring(27436, 27480);

        System.out.println("authenticity_token = " + authenticity_token);
    }

    @Test(dependsOnMethods = "PreLogin")
    public void Login(){

    }


}
