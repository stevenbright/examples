package com.mysite.springjdbcsample;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for CountryServiceImpl.
 */
public class CountryServiceImplTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CountryServiceImplTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(CountryServiceImplTest.class);
    }

    public void testService() {
        assertTrue(true);
    }
}
