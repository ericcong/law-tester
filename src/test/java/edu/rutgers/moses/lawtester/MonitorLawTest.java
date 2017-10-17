package edu.rutgers.moses.lawtester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MonitorLawTest {
  private static final String MONITOR_LAW_PATH = "/monitor.law";
  private static final String TEST_MESSAGE = "test";

  private static final TestAgentBuilder BASE_BUILDER = new TestAgentBuilder()
      .setLawStream(MonitorLawTest.class.getResourceAsStream(MONITOR_LAW_PATH));

  private TestAgent monitor =
      new TestAgentBuilder(BASE_BUILDER).setName("monitor").build();

  private TestAgent foo =
      new TestAgentBuilder(BASE_BUILDER).setName("foo").build();

  private TestAgent bar =
      new TestAgentBuilder(BASE_BUILDER).setName("bar").build();

  @Before
  public void init() throws IOException {
    monitor.init();
    foo.setArg("monitor", monitor.getFullName()).init();
    bar.setArg("monitor", monitor.getFullName()).init();
  }

  @After
  public void kill() {
    foo.kill();
    bar.kill();
    monitor.kill();
  }

  @Test
  public void testAdopted() throws Exception {
    long initTime = foo.init();
    assert(monitor.receives(foo.getFullName() + " is formed")
        .after(initTime).by(100));
  }

  @Test
  public void testQuit() throws Exception {
    long killTime = foo.kill();
    assert(monitor.receives(foo.getFullName() + " is dissolved")
        .after(killTime).byDeadline());
  }

  @Test
  public void testSendAndReceive() throws Exception {
    long sendTime = foo.send(TEST_MESSAGE).to(bar);

    assert(bar.receives(TEST_MESSAGE).from(foo).after(sendTime).byDeadline());

    assert(monitor.receives(
        foo.getFullName() + " sent a message to " + bar.getFullName())
        .after(sendTime).byDeadline());

    assert(monitor.receives(
        bar.getFullName() + " receives a message from " + foo.getFullName())
        .after(sendTime).byDeadline());
  }
}
