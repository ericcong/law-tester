package edu.rutgers.moses.lawtester;

import javax.script.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CoffeeScript {
  private static final String COFFEESCRIPT_PATH = "/coffee-script.js";
  private CompiledScript coffeec;
  private ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

  public CoffeeScript() {
    String coffeecLine;
    StringBuilder coffeecBuilder = new StringBuilder();

    BufferedReader coffeecReader =
        new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(COFFEESCRIPT_PATH)));

    try {
      while ((coffeecLine = coffeecReader.readLine()) != null) {
        coffeecBuilder.append(coffeecLine);
      }
      coffeecBuilder.append("CoffeeScript.compile(__source__, {bare: true});");
      this.coffeec = ((Compilable) this.engine).compile(coffeecBuilder.toString());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ScriptException e) {
      e.printStackTrace();
    }
  }

  public String compile(String source) throws ScriptException {
    Bindings bindings = new SimpleBindings();
    bindings.put("__source__", source);
    return coffeec.eval(bindings).toString();
  }
}
