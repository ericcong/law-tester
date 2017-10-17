package edu.rutgers.moses.lawtester;

public class SendDelegate {
  private String message;
  private TestAgent testAgent;

  public SendDelegate(TestAgent testAgent, String message) {
    this.testAgent = testAgent;
    this.message = message;
  }

  public long to(TestAgent receiver) {
    long time = System.currentTimeMillis();
    this.testAgent.getMember().send_lg(
        "\"" + this.message + "\"", receiver.getFullName());
    return time;
  }
}
