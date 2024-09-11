package com.trello.api.tests;

import com.trello.api.payloads.BoardPayload;
import com.trello.api.utils.ConfigReader;
import com.trello.api.utils.ExtentReportManager;
import com.trello.api.utils.Log4jUtil;
import com.trello.api.utils.RestAssuredUtil;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected static RequestSpecification requestSpec;
    protected String boardId;

    // Set up the base configuration before any test suite runs
    @BeforeSuite
    public void setUp() {
        RestAssured.baseURI = ConfigReader.getProperty("base.url");
        requestSpec = RestAssured.given()
                .queryParam("key", ConfigReader.getProperty("trello.key"))
                .queryParam("token", ConfigReader.getProperty("trello.token"));

        ExtentReportManager.initReports();
    }

    // Create a new board before each test method
    @BeforeMethod
    public void createBoard() {
        BoardPayload payload = new BoardPayload("Test Board");
        Response response = RestAssuredUtil.post("/1/boards", payload);
        Assert.assertEquals(response.getStatusCode(), 200, "Board creation failed");
        boardId = response.jsonPath().getString("id");
        Log4jUtil.info("Test board created successfully with ID: " + boardId);
    }

    // Delete the created board after each test method
    @AfterMethod
    public void deleteBoard() {
        if (boardId != null) {
            Response response = RestAssuredUtil.delete("/1/boards/" + boardId);
            Assert.assertEquals(response.getStatusCode(), 200, "Board deletion failed");
            Log4jUtil.info("Test board deleted successfully");
        }
    }

    // Clean up after all test suites have run
    @AfterSuite
    public void tearDown() {
        ExtentReportManager.flushReports();
    }
}
