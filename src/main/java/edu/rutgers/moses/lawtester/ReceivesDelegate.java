package edu.rutgers.moses.lawtester;

import java.util.concurrent.TimeUnit;

public class ReceivesDelegate {
  private String message;
  private String senderFullName;
  private long startTime = 0;
  private TestAgent testAgent;

  public ReceivesDelegate(TestAgent testAgent, String message) {
    this.testAgent = testAgent;
    this.message = message;
  }

  public ReceivesDelegate from(TestAgent sender) {
    this.senderFullName = sender.getFullName();
    return this;
  }

  public ReceivesDelegate after(long startTime) {
    this.startTime = startTime;
    return this;
  }

  public boolean byDeadline() throws InterruptedException {
    return this.by(this.testAgent.getDeadline());
  }

  public boolean by(long deadline) throws InterruptedException {
    TimeUnit.MILLISECONDS.sleep(deadline);

    boolean result = false;
    boolean nullMessage = true;

    for (MessageEntry messageEntry : this.testAgent.getInbox()) {
      if ((this.senderFullName == null ||
          messageEntry.getSenderFullName().equals(this.senderFullName)) &&
          (this.message == null ||
              messageEntry.getMessage().equals(this.message)) &&
          messageEntry.getArrivalTime() >= this.startTime &&
          messageEntry.getArrivalTime() <= this.startTime + deadline) {
        result = true;
        nullMessage = false;
        break;
      }
    }

    return this.message == null
        ? nullMessage
        : result;
  }
}
