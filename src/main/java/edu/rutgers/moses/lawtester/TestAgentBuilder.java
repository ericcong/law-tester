package edu.rutgers.moses.lawtester;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class TestAgentBuilder {
  private static long DEADLINE = 100;

  private Map<String, String> args = new HashMap<>();
  private String controllerHost;
  private int controllerPort = 9000;
  private long deadline = DEADLINE;
  private String law;
  private String name;
  private String password = "password";

  public TestAgentBuilder() {
    try {
      this.controllerHost = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  public TestAgentBuilder(TestAgentBuilder testAgentBuilder) {
    this.setLaw(testAgentBuilder.getLaw())
        .setControllerHost(testAgentBuilder.getControllerHost())
        .setControllerPort(testAgentBuilder.getControllerPort())
        .setDeadline(testAgentBuilder.getDeadline())
        .setArgs(testAgentBuilder.getArgs())
        .setName(testAgentBuilder.getName())
        .setPassword(testAgentBuilder.getPassword());
  }

  public TestAgentBuilder setLaw(String law) {
    this.law = law;
    return this;
  }

  public TestAgentBuilder setLawStream(InputStream lawStream) {
    String lawLine;
    StringBuilder lawBuilder = new StringBuilder();
    String lineSeparator = System.getProperty("line.separator");

    BufferedReader lawReader =
        new BufferedReader(new InputStreamReader(lawStream));

    try {
      while ((lawLine = lawReader.readLine()) != null) {
        lawBuilder.append(lawLine).append(lineSeparator);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setLaw(lawBuilder.toString());
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

  public TestAgentBuilder setArgs(Map<String, String> args) {
    this.args = new HashMap<>(args);
    return this;
  }

  public TestAgentBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public TestAgentBuilder setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getLaw() {
    return this.law;
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
    return new HashMap<>(this.args);
  }

  public String getName() {
    return this.name;
  }

  public String getPassword() {
    return this.password;
  }

  public TestAgent build() {
    try {
      return new TestAgent(
          this.args,
          this.controllerHost,
          this.controllerPort,
          this.deadline,
          this.law,
          this.name,
          this.password);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
