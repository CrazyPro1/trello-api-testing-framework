package com.trello.api.tests;

import com.trello.api.payloads.ListPayload;
import com.trello.api.utils.ExtentReportManager;
import com.trello.api.utils.Log4jUtil;
import com.trello.api.utils.RestAssuredUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ListsTest extends BaseTest {

    private String listId;

    // Create a new list before each test method
    @BeforeMethod
    public void createList() {
        ListPayload payload = new ListPayload("Test List", boardId);
        Response response = RestAssuredUtil.post("/1/lists", payload);
        Assert.assertEquals(response.getStatusCode(), 200, "List creation failed");
        listId = response.jsonPath().getString("id");
        Log4jUtil.info("Test list created successfully with ID: " + listId);
    }

    // Test case to verify list creation
    @Test(priority = 1)
    public void testCreateList() {
        ExtentReportManager.createTest("Create List Test");
        Assert.assertNotNull(listId, "List ID should not be null after creation");
        Log4jUtil.info("List created successfully with ID: " + listId);
        ExtentReportManager.logPass("List created successfully");
    }

    // Test case to verify fetching a list
    @Test(priority = 2)
    public void testGetList() {
        ExtentReportManager.createTest("Get List Test");
        Response response = RestAssuredUtil.get("/1/lists/" + listId);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), listId);
        Log4jUtil.info("List retrieved successfully");
        ExtentReportManager.logPass("List retrieved successfully");
    }

    // Test case to verify updating a list
    @Test(priority = 3)
    public void testUpdateList() {
        ExtentReportManager.createTest("Update List Test");
        ListPayload payload = new ListPayload("Updated List Name", boardId);
        Response response = RestAssuredUtil.put("/1/lists/" + listId, payload);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Updated List Name");
        Log4jUtil.info("List updated successfully");
        ExtentReportManager.logPass("List updated successfully");
    }

    // Test case to verify archiving a list
    @Test(priority = 4)
    public void testArchiveList() {
        ExtentReportManager.createTest("Archive List Test");
        Response response = RestAssuredUtil.put("/1/lists/" + listId + "/closed", "{\"value\": true}");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("closed"));
        Log4jUtil.info("List archived successfully");
        ExtentReportManager.logPass("List archived successfully");
    }
}
