package com.vzt.framework.core.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/** 
 * Logger adapter to enable custom logging for tested logs
 * @author prokarma
 * @verion 1.0
 */
public class TestStatusLogger extends TestListenerAdapter {
  
    private static final Logger logger = LoggerFactory.getLogger(TestStatusLogger.class);
    
    /**
     * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
     */
    @Override
    public void onTestFailure(ITestResult tr) {
        logger.info("Failed test {}({}) {}", tr.getMethod().getMethodName(), tr.getParameters(),tr.getThrowable());
    }
   
    /**
     * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
     */
    @Override
    public void onTestSkipped(ITestResult tr) {
        logger.info("Skipped test {}({}) {} ", tr.getMethod().getMethodName(), tr.getParameters(),tr.getThrowable());
    }
   
    /**
     * @see org.testng.TestListenerAdapter#onTestSuccess(org.testng.ITestResult)
     */
    @Override
    public void onTestSuccess(ITestResult tr) {
        logger.info("Success test {}({})", tr.getMethod().getMethodName(), tr.getParameters());
    }
}