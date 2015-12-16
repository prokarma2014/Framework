package com.vzt.framework.core.reporting;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Represents extent reporting to enable detailed graphical reports.
 * 
 * @author prokarma
 * @verion 1.0
 */
public class ExtentReporterNG implements IReporter {
    private ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        extent = new ExtentReports(outputDirectory + File.separator + "Extent.html", true);
        // extent = new ExtentReports("Extent.html", true);

        // optional
        extent.config().documentTitle("Reports").reportName("Test Automation").reportHeadline(" - Reports")
                .insertCustomStyles(".test { border:2px solid #444; }");

        // optional
        /*
         * extent .addSystemInfo("Selenium Version", "2.46") .addSystemInfo("Environment", "QA");
         */
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
                buildTestNodes(context.getPassedTests(), LogStatus.PASS);

            }
        }

        extent.flush();
    }

    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;

        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {

                Object[] values = result.getParameters();
                if (values.length != 0) {
                    String finalValue = "";
                    for (Object value : values) {
                        finalValue = finalValue + value + " , ";
                    }
                    finalValue = finalValue.substring(0, finalValue.length() - 2);
                    test = extent.startTest(result.getMethod().getMethodName() + " ( " + finalValue + " ) ");
                } else {
                    test = extent.startTest(result.getMethod().getMethodName());
                }
                if (result.getMethod().getXmlTest().getAllParameters().get("platform-type") != null) {
                    test.assignCategory(result.getMethod().getXmlTest().getAllParameters().get("platform-type"));
                }
                String message = "Test " + status.toString().toLowerCase() + "ed";

                if (result.getThrowable() != null)
                    message = result.getThrowable().getMessage();

                test.log(status, message);

                extent.endTest(test);
            }
        }
    }
}