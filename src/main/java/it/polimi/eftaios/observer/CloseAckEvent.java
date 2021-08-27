package it.polimi.eftaios.observer;

public class CloseAckEvent extends Event {

  private static final long serialVersionUID = 1L;

  public CloseAckEvent() {
    super("CloseAckEvent", false);
  }

}
