package com.trello.api.tests;

import com.trello.api.payloads.BoardPayload;
import com.trello.api.payloads.CardPayload;
import com.trello.api.payloads.ListPayload;
import com.trello.api.utils.ExtentReportManager;
import com.trello.api.utils.Log4jUtil;
import com.trello.api.utils.RestAssuredUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CardsTest extends BaseTest {

    private String boardId;
    private String listId;
    private String createdCardId;

    @BeforeClass
    public void setup() {
        // Create a board to work with cards
        BoardPayload boardPayload = new BoardPayload("Test Board for Cards");
        Response boardResponse = RestAssuredUtil.post("/1/boards", boardPayload);

        if (boardResponse.getStatusCode() != 200) {
            Log4jUtil.error("Board creation failed. Status code: " + boardResponse.getStatusCode() +
                    ". Response body: " + boardResponse.asString());
            Assert.fail("Board creation failed. Cannot proceed with card tests.");
        }

        boardId = boardResponse.jsonPath().getString("id");
        Log4jUtil.info("Test board created successfully with ID: " + boardId);

        // Create a list to work with cards
        ListPayload listPayload = new ListPayload("Test List for Cards", boardId);
        Response listResponse = RestAssuredUtil.post("/1/lists", listPayload);

        if (listResponse.getStatusCode() != 200) {
            Log4jUtil.error("List creation failed. Status code: " + listResponse.getStatusCode() +
                    ". Response body: " + listResponse.asString());
            Assert.fail("List creation failed. Cannot proceed with card tests.");
        }

        listId = listResponse.jsonPath().getString("id");
        Log4jUtil.info("Test list created successfully with ID: " + listId);
    }

    // Test to create a card in the created list
    @Test(priority = 1)
    public void testCreateCard() {
        ExtentReportManager.createTest("Create Card Test");
        CardPayload payload = new CardPayload("Test Card", listId);

        Response response = RestAssuredUtil.post("/1/cards", payload);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Test Card");

        createdCardId = response.jsonPath().getString("id");
        Log4jUtil.info("Card created successfully with ID: " + createdCardId);
        ExtentReportManager.logPass("Card created successfully");
    }

    // Test to get the details of the created card
    @Test(priority = 2)
    public void testGetCard() {
        ExtentReportManager.createTest("Get Card Test");

        Response response = RestAssuredUtil.get("/1/cards/" + createdCardId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("id"), createdCardId);

        Log4jUtil.info("Card retrieved successfully");
        ExtentReportManager.logPass("Card retrieved successfully");
    }

    // Test to update the name of the created card
    @Test(priority = 3)
    public void testUpdateCard() {
        ExtentReportManager.createTest("Update Card Test");
        CardPayload payload = new CardPayload("Updated Card Name", listId);

        Response response = RestAssuredUtil.put("/1/cards/" + createdCardId, payload);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Updated Card Name");

        Log4jUtil.info("Card updated successfully");
        ExtentReportManager.logPass("Card updated successfully");
    }

    // Test to delete the created card
    @Test(priority = 4)
    public void testDeleteCard() {
        ExtentReportManager.createTest("Delete Card Test");

        Response response = RestAssuredUtil.delete("/1/cards/" + createdCardId);

        Assert.assertEquals(response.getStatusCode(), 200);

        Log4jUtil.info("Card deleted successfully");
        ExtentReportManager.logPass("Card deleted successfully");
    }

    // Clean up by deleting the board created in setup
    @Test(priority = 5)
    public void cleanup() {
        ExtentReportManager.createTest("Cleanup Test");

        Response response = RestAssuredUtil.delete("/1/boards/" + boardId);

        Assert.assertEquals(response.getStatusCode(), 200);

        Log4jUtil.info("Test board deleted successfully");
        ExtentReportManager.logPass("Test board deleted successfully");
    }

    // Negative test case for attempting to get a deleted card
    @Test(priority = 6)
    public void testNegativeScenario_GetDeletedCard() {
        ExtentReportManager.createTest("Negative Scenario - Get Deleted Card Test");

        Response response = RestAssuredUtil.get("/1/cards/" + createdCardId);

        Assert.assertEquals(response.getStatusCode(), 404);

        Log4jUtil.info("Deleted card not found as expected");
        ExtentReportManager.logPass("Negative scenario passed: Deleted card not found");
    }
}
