package edu.rutgers.moses.lawtester;

public class MessageEntry {
  private long arrivalTime;
  private Object message;
  private String senderFullName;

  public MessageEntry(String senderFullName, Object message, long arrivalTime) {
    this.senderFullName = senderFullName;
    this.message = message;
    this.arrivalTime = arrivalTime;
  }

  public String getSenderFullName() {
    return this.senderFullName;
  }

  public Object getMessage() {
    return this.message;
  }

  public long getArrivalTime() {
    return this.arrivalTime;
  }
}
