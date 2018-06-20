package com.rest.ui.testсases;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.rest.ui.POJO.Item;
import com.rest.ui.POJO.Repository;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static io.restassured.RestAssured.given;

public class FirstTestCase {

    private static final String SELENIDE_URL = "https://github.com/search?o=desc&q=Selenide&s=stars&type=Repositories";

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://api.github.com/search/repositories";
    }

    /**
     * 1. Выполнить rest-запрос и получить список репозиториев, название
     * которых содержит Selenide, запрос должен иметь сортировку по
     * звёздам на убывание, ответ на запрос сохранить
     * 2. Перейти на github.com и выполнить поиск по тому же слову
     * 3. Проверить, что первый элемент из ответа на запрос соответствует
     * первому элементу в результатах поиска на UI, выполнить проверку
     * по названию репозитория, короткому описанию, основному языку,
     * лицензии и количеству звёзд.
     * 4. Проверить, что общее количество найденных репозиториев на UI
     * соответствует оному из запроса (519).
     */
    @Test
    public void validateRestAndUi() {
        Repository uiResult = getUiResult();
        Repository restResult = getRestResult();
        Assert.assertEquals(uiResult, restResult);
        Assert.assertEquals(uiResult.getTotalCount(), restResult.getTotalCount());
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
        String totalCount = $("#js-pjax-container > div > div.columns > div.column.three-fourths.codesearch-results > div > div.d-flex.flex-justify-between.border-bottom.pb-3 > h3").text();
        repository.setTotalCount(totalCount.replaceAll("[A-z\\s]+", ""));
        return repository;
    }

    private Repository getRestResult() {
        JsonPath jsonPath = given()
                .param("q", "Selenide")
                .get()
                .body()
                .jsonPath();
        List<Item> returnedCollection = jsonPath.getList("items", Item.class);
        Item restItem = returnedCollection.get(0);
        String stars = restItem.getStars().substring(0, 3);

        Repository repository = new Repository();
        repository.setStars(stars);
        repository.setLicense(restItem.getLicense().getSpdx());
        repository.setDesc(restItem.getDesc());
        repository.setMainLang(restItem.getMainLang());
        repository.setName(restItem.getName());
        repository.setTotalCount(jsonPath.getString("total_count"));
        return repository;
    }

}
