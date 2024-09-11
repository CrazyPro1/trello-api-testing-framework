package com.trello.api.tests;

import com.trello.api.payloads.BoardPayload;
import com.trello.api.utils.ExtentReportManager;
import com.trello.api.utils.Log4jUtil;
import com.trello.api.utils.RestAssuredUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BoardsTest extends BaseTest {

    private String createdBoardId;

    // Test case to verify board creation
    @Test(priority = 1)
    public void testCreateBoard() {
        ExtentReportManager.createTest("Create Board Test");
        BoardPayload payload = new BoardPayload("MyDashBoard");

        Response response = RestAssuredUtil.post("/1/boards", payload);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "MyDashBoard");

        createdBoardId = response.jsonPath().getString("id");
        System.out.println(createdBoardId);
        Log4jUtil.info("Board created successfully with ID: " + createdBoardId);
        ExtentReportManager.logPass("Board created successfully");
    }

    // Test case to verify fetching a board
    @Test(priority = 2)
    public void testGetBoard() {
        ExtentReportManager.createTest("Get Board Test");

        Response response = RestAssuredUtil.get("/1/boards/" + createdBoardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), createdBoardId);

        Log4jUtil.info("Board retrieved successfully");
        ExtentReportManager.logPass("Board retrieved successfully");
    }

    // Test case to verify updating a board
    @Test(priority = 3)
    public void testUpdateBoard() {
        ExtentReportManager.createTest("Update Board Test");
        BoardPayload payload = new BoardPayload("BlackBoard");

        Response response = RestAssuredUtil.put("/1/boards/" + createdBoardId, payload);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "BlackBoard");

        Log4jUtil.info("Board updated successfully");
        ExtentReportManager.logPass("Board updated successfully");
    }

    // Test case to verify deleting a board
    @Test(priority = 4)
    public void testDeleteBoard() {
        ExtentReportManager.createTest("Delete Board Test");

        Response response = RestAssuredUtil.delete("/1/boards/" + createdBoardId);

        Assert.assertEquals(response.getStatusCode(), 200);

        Log4jUtil.info("Board deleted successfully");
        ExtentReportManager.logPass("Board deleted successfully");
    }

    // Test case to verify negative scenario of fetching a deleted board
    @Test(priority = 5)
    public void testNegativeScenario_GetDeletedBoard() {
        ExtentReportManager.createTest("Negative Scenario - Get Deleted Board Test");

        Response response = RestAssuredUtil.get("/1/boards/" + createdBoardId);

        Assert.assertEquals(response.getStatusCode(), 404);

        Log4jUtil.info("Deleted board not found as expected");
        ExtentReportManager.logPass("Negative scenario passed: Deleted board not found");
    }
}
