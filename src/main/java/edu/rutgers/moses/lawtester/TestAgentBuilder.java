package edu.rutgers.moses.lawtester;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestAgentBuilder {
  private static long DEADLINE = 100;

  private InputStream lawStream;
  private String controllerHost;
  private int controllerPort;
  private long deadline = DEADLINE;
  private Map<String, String> args = new HashMap<String, String>;
  private String name;

  public TestAgentBuilder setLawStream(InputStream lawStream) {
    this.lawStream = lawStream;
    return this;
  }

  public TestAgentBuilder setControllerHost(String controllerHost) {
    this.controllerHost = controllerHost;
    return this;
  }

  public TestAgentBuilder setControllerPort(int controllerPort) {
    this.controllerPort = controllerPort;
    return this;
  }

  public TestAgentBuilder setDeadline(long deadline) {
    this.deadline = deadline;
    return this;
  }

  public TestAgentBuilder setArg(String key, String value) {
    this.args.put(key, value);
    return this;
  }

  public TestAgentBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public InputStream getLawStream() {
    return this.lawStream;
  }

  public String getControllerHost() {
    return this.controllerHost;
  }

  public int getControllerPort() {
    return this.controllerPort;
  }

  public long getDeadline() {
    return this.deadline;
  }

  public String getArg(String key) {
    return this.args.get(key);
  }

  public Map<String, String> getArgs() {
    return this.args;
  }

  public String getName() {
    return this.name;
  }

  public TestAgent build() {
    return new TestAgent(
        lawStream, controllerHost, controllerPort, deadline, args, name);
  }
}
