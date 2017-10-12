package edu.rutgers.moses.lawtester;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoffeeScriptTest {
  private static final CoffeeScript coffeeScript = new CoffeeScript();

  @Test
  public void testCompiler() throws Exception {
    String source =
        "relu = (number) ->\n" +
        "  if number > 0\n" +
        "    return number\n" +
        "  else\n" +
        "    return 0\n";

    String expectedJavascript =
        "var relu;\n\n" +
        "relu = function(number) {\n" +
        "  if (number > 0) {\n" +
        "    return number;\n" +
        "  } else {\n" +
        "    return 0;\n" +
        "  }\n" +
        "};\n";

    assertEquals(expectedJavascript, coffeeScript.compile(source));
  }
}
