package com.vzt.framework.core.actions;

import com.vzt.framework.core.annotations.Data;

/**
 * Control data for test retrieved from the test data.
 * @author prokarma
 * @version 1.0
 */
public class TestControl {

    @Data(name="TST_Cntrl_TC_IDS")
    private String testCaseIDs;

    /**
     * @return the testCaseIDs
     */
    public String getTestCaseIDs() {
        return testCaseIDs;
    }
}
