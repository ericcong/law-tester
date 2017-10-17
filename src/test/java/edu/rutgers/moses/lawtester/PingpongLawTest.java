package edu.rutgers.moses.lawtester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PingpongLawTest {
  private static final String PINGPONG_LAW_PATH = "/pingpong.law";
  private static final String TEST_MESSAGE = "test";
  private static final String PING_MESSAGE = "ping";
  private static final String PONG_MESSAGE = "pong";

  private static final TestAgentBuilder BASE_BUILDER = new TestAgentBuilder()
      .setLawStream(
          PingpongLawTest.class.getResourceAsStream(PINGPONG_LAW_PATH));

  private TestAgent foo =
      new TestAgentBuilder(BASE_BUILDER).setName("foo").build();
  private TestAgent bar =
      new TestAgentBuilder(BASE_BUILDER).setName("bar").build();

  @Before
  public void init() {
    foo.init();
    bar.init();
  }

  @After
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
