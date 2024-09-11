package com.trello.api.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReports() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
        extent.attachReporter(spark);
    }

    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void logPass(String message) {
        test.pass(message);
    }

    public static void logFail(String message) {
        test.fail(message);
    }

    public static void flushReports() {
        extent.flush();
    }
}
