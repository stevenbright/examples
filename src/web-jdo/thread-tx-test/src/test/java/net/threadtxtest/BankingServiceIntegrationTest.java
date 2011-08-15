package net.threadtxtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Sample spring-driven test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/integration-test-context.xml" })
@Transactional
public class BankingServiceIntegrationTest {

    /**
     * Sample test method.
     */
    @Test
    public void testDummy() {
        assertTrue(true);
    }
}
