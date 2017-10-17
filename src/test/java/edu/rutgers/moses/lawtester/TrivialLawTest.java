package edu.rutgers.moses.lawtester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TrivialLawTest {
  private static final String TRIVIAL_LAW_PATH = "/trivial.law";
  private static final String CONTROLLER_HOST = "127.0.1.1";
  private static final int CONTROLLER_PORT = 9000;
  private static final int DEADLINE = 100;
  private static final String TEST_MESSAGE = "test message";

  private static final TestAgentBuilder BASE_BUILDER = new TestAgentBuilder()
      .setLawStream(TrivialLawTest.class.getResourceAsStream(TRIVIAL_LAW_PATH))
      .setControllerHost(CONTROLLER_HOST)
      .setControllerPort(CONTROLLER_PORT)
      .setDeadline(DEADLINE);

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
  public void testTrivialLaw() throws Exception {
    long sendTime = foo.send(TEST_MESSAGE).to(bar);
    assert(bar.receives(TEST_MESSAGE).from(foo).after(sendTime).byDeadline());
  }
}
