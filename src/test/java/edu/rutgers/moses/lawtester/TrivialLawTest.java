package edu.rutgers.moses.lawtester;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrivialLawTest {
  private static final String TRIVIAL_LAW_PATH = "/trivial.law";

  private static final String CONTROLLER_HOST = "127.0.0.1";
  private static final int CONTROLLER_PORT = 5000;

  private static final int DEADLINE = 5;

  private static final String TEST_MESSAGE = "test message";

  private TestAgent foo = new TestAgentBuilder()
      .setLawStream(ClassLoader.class.getResourceAsStream(TRIVIAL_LAW_PATH))
      .setControllerHost(CONTROLLER_HOST)
      .setControllerPort(CONTROLLER_PORT)
      .setDeadline(DEADLINE)
      .setName("foo")
      .build();

  private TestAgent bar = new TestAgentBuilder()
      .setLawStream(ClassLoader.class.getResourceAsStream(TRIVIAL_LAW_PATH))
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
  public void testTrivialLaw() throws Exception {
    long sendTime = foo.send(TEST_MESSAGE).to(bar);
    assert(bar.receives(TEST_MESSAGE).from(foo).after(sendTime).beforeDeadline());
  }
}
