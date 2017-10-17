package edu.rutgers.moses.lawtester;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.rutgers.moses.member.Member;
import edu.rutgers.moses.util.Const;

import java.io.*;
import java.util.*;

public class TestAgent {
  private Map<String, String> args = new HashMap<>();
  private String controllerHost;
  private int controllerPort;
  private long deadline;
  private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
  private List<MessageEntry> inbox = new ArrayList<>();
  private String law;
  private Member member;
  private String name;
  private String password;
  private TestAgentReceiver receiver;

  public TestAgent(
      Map<String, String> args,
      String controllerHost,
      int controllerPort,
      long deadline,
      String law,
      String name,
      String password) throws IOException {
    this.law = law;
    this.controllerPort = controllerPort;
    this.deadline = deadline;
    this.args = args;
    this.controllerHost = controllerHost;
    this.name = name;
    this.password = password;
    this.receiver = new TestAgentReceiver(this);
  }

  public String getFullName() {
    return this.name + "@" + this.controllerHost;
  }

  public Member getMember() {
    return this.member;
  }

  public long getDeadline() {
    return this.deadline;
  }

  public List<MessageEntry> getInbox() {
    return new ArrayList<>(this.inbox);
  }

  public TestAgent addMessageEntry(MessageEntry messageEntry) {
    this.inbox.add(messageEntry);
    return this;
  }

  public synchronized TestAgent clearInbox() {
    this.inbox.clear();
    return this;
  }

  public TestAgent setArg(String key, String value) {
    this.args.put(key, value);
    return this;
  }

  public synchronized long init() {
    long time = System.currentTimeMillis();
    this.name += "~" + UUID.randomUUID().toString();
    this.member = new Member(
        this.law, Const.IMM_LAW,
        this.controllerHost, this.controllerPort, this.name);
    this.member.adopt(this.password, this.gson.toJson(this.args));
    this.receiver.setAlive(true);
    new Thread(this.receiver).start();
    return time;
  }

  public synchronized long kill() {
    long time = System.currentTimeMillis();
    this.receiver.setAlive(false);
    this.member.close();
    return time;
  }

  public SendDelegate send(String message) {
    return new SendDelegate(this, message);
  }

  public ReceivesDelegate receives(String message) {
    return new ReceivesDelegate(this, message);
  }
}
