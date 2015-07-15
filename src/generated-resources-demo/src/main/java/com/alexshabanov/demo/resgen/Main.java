package com.alexshabanov.demo.resgen;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class Main {

  public static void main(String[] args) throws Exception {
    String content = readResource("/resgenProps/sample.properties");
    System.out.println("\n===\nsample.properties content:\n" + content);

    content = readResource("/newResgenProps/compile-date.txt");
    System.out.println("\n===\ncompile-date.txt content:\n" + content);
  }

  public static String readResource(String resName) throws IOException {
    try (final InputStream inputStream = Main.class.getResourceAsStream(resName)) {
      if (inputStream == null) {
        return "<NONE>";
      }
      try (final Scanner s = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A")) {
        return s.hasNext() ? s.next() : "<NONE>";
      }
    }
  }
}
