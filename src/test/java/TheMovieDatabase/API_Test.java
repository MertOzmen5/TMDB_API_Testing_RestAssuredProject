package TheMovieDatabase;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;


import javax.swing.text.html.HTML;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import static io.restassured.RestAssured.baseURI;

public class API_Test {

    RequestSpecification reqSpec;
    String authenticity_token;
    Faker randomUreteci=new Faker();
    String userName="";
    String password="";

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

   // @Test(dependsOnMethods = "PreLogin")
    public void Login(){

        Map<String,Object> body=new HashMap<>();
        userName=randomUreteci.name().fullName();
        password="123456789";
        body.put("username",userName);
        body.put("password",password);
        body.put("authenticity_token",authenticity_token);


        given()
                .contentType(ContentType.HTML)
                .body(body)

                .when()
                .post("/login")


                .then()
                .statusCode(200)

                ;
    }

    @Test
    public void GetAccountDetails(){

    }


}
