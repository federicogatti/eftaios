package it.polimi.eftaios.observer;

import java.io.Serializable;

public abstract class Event implements Serializable {

  private static final long serialVersionUID = 1L;
  private final String msg;
  private final boolean toAll;

  public Event(String msg, boolean toAll) {
    this.msg = msg;
    this.toAll = toAll;
  }

  public String getMsg() {
    return msg;
  }

  public boolean toAll() {
    return toAll;
  }

  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
