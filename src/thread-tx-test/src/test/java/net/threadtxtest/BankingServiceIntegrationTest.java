package net.threadtxtest;

import net.threadtxtest.service.BankingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Tests banking service in the integration spring context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/integration-test-context.xml" })
@Transactional
public class BankingServiceIntegrationTest {

    @Resource
    private BankingService bankingService;

    /**
     * Sample test method.
     */
    @Test
    public void testDummy() {
        assertTrue(true);
    }

    @Test
    public void testRegisterUser() {
        final long ids[] = {
                bankingService.registerUser("alex"),
                bankingService.registerUser("bob"),
                bankingService.registerUser("dave")
        };

        assertNotSame(ids[0], ids[1]);
        assertNotSame(ids[0], ids[2]);
        assertNotSame(ids[1], ids[2]);
    }
}
