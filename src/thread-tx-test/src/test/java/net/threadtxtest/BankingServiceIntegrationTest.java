package net.threadtxtest;

import net.threadtxtest.service.BankOperationStatus;
import net.threadtxtest.service.BankingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Tests banking service in the integration spring context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/integration-test-context.xml" })
//@Transactional
public class BankingServiceIntegrationTest {

    @Resource
    private BankingService bankingService;

    private static final int OPERATION_WAIT_ITER_TIMEOUT = 100;

    private static final int OPERATION_MAX_WAIT_ITER = 10;


    private void ensureOperationCompleted(long operationId) {
        assertEquals(BankOperationStatus.PENDING, bankingService.getOperationStatus(operationId));

        for (int i = 0; i < OPERATION_MAX_WAIT_ITER; ++i) {
            final BankOperationStatus status = bankingService.getOperationStatus(operationId);
            if (status == BankOperationStatus.SUCCEEDED) {
                // ok
                return;
            }
        }

        fail("Operation timeout, Operation ID = " + operationId);
    }


    @Test
    @Transactional
    public void testRegisterUser() {
        final Long ids[] = {
                bankingService.registerUser("alex"),
                bankingService.registerUser("bob"),
                bankingService.registerUser("cavin")
        };

        assertEquals(ids[0], bankingService.getUserIdByName("alex"));
        assertEquals(ids[1], bankingService.getUserIdByName("bob"));
        assertEquals(ids[2], bankingService.getUserIdByName("cavin"));
    }

    @Test
    public void testTxCompleted() {
        final Long ids[] = {
                bankingService.registerUser("alex"),
                bankingService.registerUser("bob"),
                bankingService.registerUser("cavin")
        };

        if ("xren".equals(System.getProperty("xren"))) {
            // TODO: this scenario does not work, enable it!
            final BigDecimal amountId0_1 = BigDecimal.valueOf(11L);
            final long operationId0_1;
            {
                operationId0_1 = bankingService.depositMoney(ids[0], amountId0_1);
                assertEquals(BigDecimal.ZERO, bankingService.getBalance(ids[0]));
                ensureOperationCompleted(operationId0_1);

                assertEquals(amountId0_1, bankingService.getBalance(ids[0]));
            }
        }

        {
            final long operationId = bankingService.depositMoney(ids[0], BigDecimal.TEN);

            // assert operation in pending status
            {
                final BankOperationStatus status = bankingService.getOperationStatus(operationId);
                assertEquals(BankOperationStatus.PENDING, status);
            }


//            final long id1 = Thread.currentThread().getId();
//            final Thread t = new Thread(new Runnable() {
//                public void run() {
//                    final long id2 = Thread.currentThread().getId();
//                    assertNotSame(id1, id2);
//
//                    final BankOperationStatus status = bankingService.getOperationStatus(operationId);
//                    assertNotNull("status shall not be null", status);
//                    assertEquals(BankOperationStatus.PENDING, status);
//                }
//            });
//
//            if (!System.getProperty("user.home").contains("replaceit")) {
//                t.start();
//            }

//            try {
//                t.join(1000);
//                if (t.isAlive()) {
//                    System.out.println("\nWARNING!!! Thread is still alive!\n");
//                }
//            } catch (InterruptedException e) {
//                fail(e.getMessage());
//            }
        }
    }
}
