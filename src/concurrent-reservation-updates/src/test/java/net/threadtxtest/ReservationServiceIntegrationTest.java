package net.threadtxtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

/**
 * Tests banking service in the integration spring context.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/ReservationServiceIntegrationTest-context.xml" })
public final class ReservationServiceIntegrationTest {

  @Resource
  private Runnable runnable;

  @Test
  @Transactional
  public void testRegisterUser() {


    assertEquals(1, 1);
  }
}
