package edu.rutgers.moses.lawtester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MonitorLawTest {
  private static String MONITOR_LAW_PATH = "/monitor.law";

  private static String CONTROLLER_HOST = "127.0.0.1";
  private static int CONTROLLER_PORT = 5000;

  private static String TEST_MESSAGE = "test";

  private static TestAgentBuilder BASE_BUILDER = new TestAgentBuilder()
      .setLawStream(PingpongLawTest.class.getResourceAsStream(MONITOR_LAW_PATH))
      .setControllerHost(CONTROLLER_HOST)
      .setControllerPort(CONTROLLER_PORT);

  private TestAgent monitor = new TestAgentBuilder(BASE_BUILDER)
      .setName("monitor")
      .build();

  private TestAgent foo = new TestAgentBuilder(BASE_BUILDER)
      .setArg("monitor", monitor.getFullName())
      .setName("foo")
      .build();

  private TestAgent bar = new TestAgentBuilder(BASE_BUILDER)
      .setArg("monitor", monitor.getFullName())
      .setName("bar")
      .build();

  @Before
  public void initMonitor() {
    monitor.init();
  }

  @After
  public void kill() {
    monitor.kill();
    foo.kill();
    bar.kill();
  }

  @Test
  public void testAdopted() throws Exception {
    long initTime = foo.init();
    assert(monitor.receives(foo.getFullName() + " is formed").from(foo)
        .after(initTime).by(50));
  }

  @Test
  public void testAdopted() throws Exception {
    foo.init();
    long killTime = foo.kill();
    assert(monitor.receives(foo.getFullName() + " is dissolved").from(foo)
        .after(killTime).byDeadline());
  }

  @Test
  public void testSendAndReceive() throws Exception {
    foo.init();
    bar.init();
    long sendTime = foo.send(TEST_MESSAGE).to(bar);
    assert(bar.receives(TEST_MESSAGE).from(foo).after(sendTime).byDeadline());
    assert(monitor.receives(
        foo.getFullName() + " sent a message to " + bar.getFullName())
        .from(foo).thenKeep().after(sendTime).byDeadline());
    assert(monitor.receives(
        bar.getFullName() + " receives a message from " + foo.getFullName())
        .from(bar).after(sendTime).byDeadline());
  }
}
