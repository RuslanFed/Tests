package com.rest.ui.testсases;


import com.codeborne.selenide.Selenide;
import com.jcabi.github.Github;
import com.jcabi.github.Repos;
import com.jcabi.github.RtGithub;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static io.restassured.RestAssured.given;

public class ThirdTestCase {

    private static final String TOKEN = "";
    private static final String REPOSITORY_NAME = "Hello-World";
    private static final String LOGIN_URL = "https://github.com/login?return_to=/" + REPOSITORY_NAME + "/settings";
    private static final String LOGIN = "";
    private static final String PASS = "";


    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://api.github.com/repos/RuslanFed/" + REPOSITORY_NAME;
    }

    /**
     * 1.Создать при помощи rest-запроса репозиторий в своём аккаунте
     * (нужно завести рандомный аккаунт для этого задания, можно
     * создать общий на всю группу)
     * 2. Перейти на github.com, залогиниться под ранее созданным
     * аккаунтом, проверить, что репозиторий создался и удалить его
     * 3. При помощи rest-запроса убедиться, что созданный в пункте 1
     * репозиторий был удалён
     */
    @Test
    public void validateRepository() {
        createRepository();
        removeRepositoryByUI();
        isRepositoryDeleted();
    }

    @Test
    public void createRepository() {
        Github github = new RtGithub(TOKEN);
        Repos.RepoCreate repoCreate = new Repos.RepoCreate(REPOSITORY_NAME, false);
        try {
            github.repos().create(repoCreate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeRepositoryByUI() {
        Selenide.open(LOGIN_URL);
        $("#login_field").setValue(LOGIN);
        $("#password").setValue(PASS);
        $("#login > form > div.auth-form-body.mt-3 > input.btn.btn-primary.btn-block").click();
        $("#options_bucket > div.Box.Box--danger > ul > li:nth-child(4) > details > summary").click();
        $("#options_bucket > div.Box.Box--danger > ul > li:nth-child(4) > details > details-dialog > div.Box-body.overflow-auto > form > p > input").setValue(REPOSITORY_NAME);
        $("#options_bucket > div.Box.Box--danger > ul > li:nth-child(4) > details > details-dialog > div.Box-body.overflow-auto > form > button").click();
    }

    @Test
    public void isRepositoryDeleted() {
        given()
                .get()
                .then()
                .assertThat()
                .statusCode(404);
    }


}
