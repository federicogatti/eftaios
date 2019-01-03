package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.command.Command;
import it.polimi.eftaios.model.command.SedativeCommand;
import it.polimi.eftaios.view.gui.SedativeCardImage;

import java.awt.Image;

public class SedativeCard extends ObjectCard {

  private static final long serialVersionUID = 1L;

  public SedativeCard() {
    super(true, true);
    name = "Carta Sedativo";
  }

  @Override
  public Command action() {
    return new SedativeCommand();
  }

  @Override
  public Image getImage() {
    return new SedativeCardImage().getImage();
  }
}
