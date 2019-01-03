package it.polimi.eftaios.observer;

public class ClientIDGrantEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final int clientID;

  public ClientIDGrantEvent(int clientID) {
    super("ClientIDGrantEvent", false);
    this.clientID = clientID;
  }

  public int getClientID() {
    return clientID;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
