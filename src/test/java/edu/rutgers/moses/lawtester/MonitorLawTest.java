package edu.rutgers.moses.lawtester;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonitorLawTest {
  private static String MONITOR_LAW_PATH = "/monitor.law";

  private static String CONTROLLER_HOST = "127.0.0.1";
  private static int CONTROLLER_PORT = 5000;

  private static int DEADLINE = 5;

  private static String TEST_MESSAGE = "test";

  private TestAgent monitor = new TestAgentBuilder()
      .setLawStream(PingpongLawTest.class.getResourceAsStream(MONITOR_LAW_PATH))
      .setControllerHost(CONTROLLER_HOST)
      .setControllerPort(CONTROLLER_PORT)
      .setDeadline(DEADLINE)
      .setName("monitor")
      .build();

  private TestAgent foo = new TestAgentBuilder()
      .setLawStream(PingpongLawTest.class.getResourceAsStream(MONITOR_LAW_PATH))
      .setControllerHost(CONTROLLER_HOST)
      .setControllerPort(CONTROLLER_PORT)
      .setDeadline(DEADLINE)
      .setArg("monitor", monitor.getFullName())
      .setName("foo")
      .build();

  private TestAgent bar = new TestAgentBuilder()
      .setLawStream(PingpongLawTest.class.getResourceAsStream(MONITOR_LAW_PATH))
      .setControllerHost(CONTROLLER_HOST)
      .setControllerPort(CONTROLLER_PORT)
      .setDeadline(DEADLINE)
      .setArg("monitor", monitor.getFullName())
      .setName("bar")
      .build();

  @BeforeEach
  public void initMonitor() {
    monitor.init();
  }

  @AfterEach
  public void kill() {
    monitor.kill();
    foo.kill();
    bar.kill();
  }

  @Test
  public void testAdopted() throws Exception {
    long initTime = foo.init();
    assert(monitor.receives(foo.getFullName() + " is formed").from(foo).after(initTime).byDeadline());
  }

  @Test
  public void testAdopted() throws Exception {
    foo.init();
    long killTime = foo.kill();
    assert(monitor.receives(foo.getFullName() + " is dissolved").from(foo).after(killTime).byDeadline());
  }

  @Test
  public void testSendAndReceive() throws Exception {
    foo.init();
    bar.init();
    long sendTime = foo.send(TEST_MESSAGE).to(bar);
    assert(bar.receives(TEST_MESSAGE).from(foo).after(sendTime).byDeadline());
    assert(monitor.receives(foo.getFullName() + " sent a message to " + bar.getFullName())
        .from(foo).thenKeep().after(sendTime).byDeadline());
    assert(monitor.receives(bar.getFullName() + " receives a message from " + foo.getFullName())
        .from(bar).after(sendTime).byDeadline());
  }
}
