package com.alexshabanov.sample.cassandra.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/DummyTest-context.xml" })
public class DummyTest {

  @Test
  public void shouldPass() {
    Runnable runnable = mock(Runnable.class);
    runnable.run();
    assertTrue(true);
  }
}
