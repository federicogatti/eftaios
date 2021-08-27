package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.match.Match;

public class NewMatchEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final Match match;

  public NewMatchEvent(Match match) {
    super("NewMatchEvent", true);
    this.match = match;
  }

  public Match getMatch() {
    return match;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
