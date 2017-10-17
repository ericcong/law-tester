package edu.rutgers.moses.lawtester;

import edu.rutgers.moses.member.Answer;
import edu.rutgers.moses.util.Const;

public class TestAgentReceiver implements Runnable {
  private boolean alive = true;
  private TestAgent testAgent;

  public boolean getAlive() {
    return this.alive;
  }

  public synchronized TestAgentReceiver setAlive(boolean alive) {
    this.alive = alive;
    return this;
  }

  public TestAgentReceiver(TestAgent testAgent) {
    this.testAgent = testAgent;
  }

  public void run() {
    Answer answer;
    while(getAlive() &&
        (answer = this.testAgent.getMember().generic_receive_lg()) != null) {
      if (answer.p_type == Const.SPLD) {
        String payload = answer.s_payload;
        this.testAgent.addMessageEntry(new MessageEntry(
            answer.source,
            payload.substring(1, payload.length() - 1),
            System.currentTimeMillis()));
      } else {
        System.out.println("Message type is unsupported.");
      }
    }
  }
}
