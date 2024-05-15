package TheMovieDatabase;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.*;


import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import static io.restassured.RestAssured.baseURI;

public class API_Test {

    RequestSpecification reqSpec;
    String authenticity_token;
    String userName = "";
    String password = "";
    String url = "https://api.themoviedb.org/3/account";
    int id;
    String url1="https://api.themoviedb.org/3/genre/";
    String url2="https://api.themoviedb.org/3/movie/";


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
    public void Login() {
        Map<String, Object> body = new HashMap<>();

        userName = "TestTitans_Meeer.t";
        password = "123456789";
        body.put("username", userName);
        body.put("password", password);
        body.put("authenticity_token", authenticity_token);


        given()
                .contentType(ContentType.HTML)
                .body(body)

                .when()
                .post("/login")


                .then()
                .statusCode(200)

        ;
    }

    @Test(dependsOnMethods = "PreLogin")
    public void GetAccountDetails() {

        id =
                given()
                        .spec(reqSpec)


                        .when()
                        .get(url)


                        .then()
                        .statusCode(200)
                        .extract().path("id")
        ;
        System.out.println("id = " + id);

    }

    @Test(dependsOnMethods = "GetAccountDetails")
    public void AddMovietoFavorites() {
        Map<String, Object> body1 = new HashMap<>();
        String media_type = "movie";
        String media_id = "6210c6bb9824c8001be3df40";
        Boolean favorite = true;

        body1.put("media_type", media_type);
        body1.put("media_id", media_id);
        body1.put("favorite", favorite);

        given()
                .spec(reqSpec)
                .body(body1)

                .when()
                .post(url + "/" + id + "/" + "favorite")


                .then()
                .statusCode(201)

        ;

    }

    @Test(dependsOnMethods = "AddMovietoFavorites")
    public void AddtoWatchlist() {
        Map<String, Object> body2 = new HashMap<>();
        String media_type = "movie";
        String media_id = "622fd845109dec00461f0189";
        Boolean watchlist = true;

        body2.put("media_type", media_type);
        body2.put("media_id", media_id);
        body2.put("watchlist", watchlist);

        given()
                .spec(reqSpec)
                .body(body2)

                .when()
                .post(url + "/" + id + "/" + "watchlist")


                .then()
                .statusCode(201)
                .body("status_message", equalTo("The item/record was updated successfully."))
        ;

    }

    @Test(dependsOnMethods = "AddtoWatchlist")
    public void GetFavoriteMovies(){

        given()
                .spec(reqSpec)

                .when()
                .get(url + "/" + id + "/" + "favorite"+"/movies")

                .then()
                .statusCode(200)
                ;
    }

    @Test(dependsOnMethods = "GetFavoriteMovies")
    public void GetFavoriteTV(){

        given()
                .spec(reqSpec)

                .when()
                .get(url + "/" + id + "/" + "favorite"+"/tv")


                .then()
                .statusCode(200)
                ;
    }

    @Test(dependsOnMethods = "GetFavoriteTV")
    public void GetRatedMovies(){

        given()
                .spec(reqSpec)


                .when()
                .get(url + "/" + id + "/" + "rated"+"/movies")

                .then()
                .statusCode(200)
                ;

    }

    @Test(dependsOnMethods = "GetRatedMovies")
    public void GetRatedTV(){

        given()
                .spec(reqSpec)


                .when()
                .get(url + "/" + id + "/" + "rated"+"/tv")

                .then()
                .statusCode(200)
                ;

    }

    @Test(dependsOnMethods = "GetRatedTV")
    public void GetWatchlistMovies(){

        given()
                .spec(reqSpec)

                .when()
                .get(url + "/" + id + "/" + "watchlist"+"/movies")


                .then()
                .statusCode(200)
                ;

    }

    @Test(dependsOnMethods = "GetWatchlistMovies")
    public void GetWatchlistTV(){

        int page=
        given()
                .spec(reqSpec)

                .when()
                .get(url + "/" + id + "/" + "watchlist"+"/tv")


                .then()
                .statusCode(200)
                .extract().path("page")
        ;

        Assert.assertTrue(page==1);

    }

    @Test(dependsOnMethods = "GetWatchlistTV")
    public void GetMovieGenres(){

        int id=
                given()
                        .spec(reqSpec)

                        .when()
                        .get(url1 + "movie" + "/"+"list")


                        .then()
                        .statusCode(200)
                        .extract().path("genres.id[0]")
                ;

        Assert.assertTrue(id==28);

    }

    @Test(dependsOnMethods = "GetMovieGenres")
    public void GetNowPlayingMovies(){

        String dates=
        given()

                .spec(reqSpec)

                .when()
                .get("https://api.themoviedb.org/3/movie/now_playing")

                .then()
                .statusCode(200)
                .extract().path("dates.maximum")
                ;

        Assert.assertEquals(dates,"2024-05-22");
    }

    @Test(dependsOnMethods = "GetNowPlayingMovies")
    public void GetPopularMovies(){

        int id=
        given()
                .spec(reqSpec)


                .when()
                .get(url2+"popular")


                .then()
                .statusCode(200)
                .extract().path("results.id[0]")
                ;

        Assert.assertEquals(id,823464);

    }

    @Test(dependsOnMethods = "GetPopularMovies")
    public void GetTopRatedMovies(){

    }




}
