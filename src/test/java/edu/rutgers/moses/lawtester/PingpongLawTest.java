package edu.rutgers.moses.lawtester;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PingpongLawTest {
  private static String PINGPONG_LAW_PATH = "/pingpong.law";
  private static String TEST_MESSAGE = "test";
  private static String PING_MESSAGE = "ping";
  private static String PONG_MESSAGE = "pong";
  private static String CONTROLLER_HOST = "127.0.0.1";
  private static int CONTROLLER_PORT = 5000;
  private static int DEADLINE = 5;

  private TestAgent foo = new TestAgentBuilder()
      .setLawStream(PingpongLawTest.class.getResourceAsStream(PINGPONG_LAW_PATH))
      .setControllerHost(CONTROLLER_HOST)
      .setControllerPort(CONTROLLER_PORT)
      .setDeadline(DEADLINE)
      .setName("foo")
      .build();

  private TestAgent bar = new TestAgentBuilder()
      .setLawStream(PingpongLawTest.class.getResourceAsStream(PINGPONG_LAW_PATH))
      .setControllerHost(CONTROLLER_HOST)
      .setControllerPort(CONTROLLER_PORT)
      .setDeadline(DEADLINE)
      .setName("bar")
      .build();

  @BeforeEach
  public void init() {
    foo.init();
    bar.init();
  }

  @AfterEach
  public void kill() {
    foo.kill();
    bar.kill();
  }

  @Test
  public void testNoMessagesOtherThanPingOrPong() throws Exception {
    long sendTime = foo.send(TEST_MESSAGE).to(bar);
    assert(bar.receives(null).from(foo).after(sendTime).byDeadline());
  }

  @Test
  public void testCantPongWithoutPing() throws Exception {
    long sendTime = foo.send(PONG_MESSAGE).to(bar);
    assert(bar.receives(null).from(foo).after(sendTime).byDeadline());
  }

  @Test
  public void testCantPongWithoutPing() throws Exception {
    long sendTime = foo.send(PONG_MESSAGE).to(bar);
    assert(bar.receives(null).from(foo).after(sendTime).byDeadline());
  }

  @Test
  public void testPingpong() throws Exception {
    long pingTime = foo.send(PING_MESSAGE).to(bar);
    assert(bar.receives(PING_MESSAGE).from(foo).after(pingTime).byDeadline());

    pingTime = foo.send(PING_MESSAGE).to(bar);
    assert(bar.receives(null).from(foo).after(pingTime).byDeadline());

    long pongTime = bar.send(PONG_MESSAGE).to(foo);
    assert(foo.receives(PONG_MESSAGE).from(bar).after(pongTime).byDeadline());

    pingTime = foo.send(PING_MESSAGE).to(bar);
    assert(bar.receives(PING_MESSAGE).from(foo).after(pingTime).byDeadline());
  }
}
