package TheMovieDatabase;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import static io.restassured.RestAssured.baseURI;

public class API_Test {

    RequestSpecification reqSpec;


    @BeforeClass
    public void SetUp() {
        baseURI = "https://www.themoviedb.org";

        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5Nzg2NzI1ZjBiZmNkMjE3NzYxZmVlZGJkYTI5YTQ5MyIsInN1YiI6IjY2MzUxZTNjNDcwZWFkMDEyNTExY2IwNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xMGIVWSwV7Lq7N_Uoun82Wh1Svp-Ox7wPbeiGMJ720E")
                .setContentType(ContentType.JSON)
                .build();
    }


}
