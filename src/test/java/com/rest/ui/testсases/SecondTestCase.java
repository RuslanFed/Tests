package com.rest.ui.testсases;

import com.rest.ui.POJO.Repository;
import com.rest.ui.POJO.Item;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static io.restassured.RestAssured.given;

public class SecondTestCase {


    private static final String SELENIDE_URL = "https://github.com/search?q=stars:%3E1&s=stars&type=Repositories";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://api.github.com/search/repositories";
    }

    /**
     * Найти при помощи запроса и на UI репозиторий с наибольшим
     * количеством звёз
     * Убедиться, что в обеих случаях это один и тот же репозиторий
     */
    @Test
    public void validateRestAndUi() {
        Repository uiResult = getUiResult();
        Repository restResult = getRestResult();
        Assert.assertEquals(uiResult, restResult);
    }

    private Repository getUiResult() {
        Selenide.open(SELENIDE_URL);
        SelenideElement element = $("ul.repo-list > div:nth-child(1)");
        Repository repository = new Repository();
        repository.setName(element.$("h3 > a").text());
        repository.setStars(element.$("div.col-2 > a.muted-link").text());
        repository.setMainLang(element.$("div.d-table-cell.col-2.text-gray.pt-2").text());
        repository.setDesc(element.$("p.col-9").text());
        repository.setLicense(element.$("div.d-flex > p").text());
        return repository;
    }

    private Repository getRestResult() {
        JsonPath jsonPath = given()
                .param("q", "stars:>1")
                .get()
                .body()
                .jsonPath();
        List<Item> returnedCollection = jsonPath.getList("items", Item.class);
        Item restItem = returnedCollection.get(0);
        String stars = restItem.getStars().substring(0, 3) + "k";

        Repository repository = new Repository();
        repository.setStars(stars);
        repository.setLicense(restItem.getLicense().getSpdx());
        repository.setDesc(restItem.getDesc());
        repository.setMainLang(restItem.getMainLang());
        repository.setName(restItem.getName());
        return repository;
    }

}
