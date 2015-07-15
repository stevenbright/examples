package com.alexshabanov.demo.resgen;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Resource reader test
 */
public class ResourceTest {

  @Test
  public void shouldReadResource() throws IOException {
    assertNotNull(Main.readResource("/resgenProps/sample.properties"));
  }
}
