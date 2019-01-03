package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.command.AdrenalinCommand;
import it.polimi.eftaios.model.command.Command;
import it.polimi.eftaios.view.gui.AdrenalineCardImage;

import java.awt.Image;

public class AdrenalinCard extends ObjectCard {

  private static final long serialVersionUID = 1L;

  public AdrenalinCard() {
    super(false, true);
    name = "Carta Adrenalina";
  }

  @Override
  public Command action() {
    return new AdrenalinCommand();
  }

  @Override
  public Image getImage() {
    return new AdrenalineCardImage().getImage();
  }

}
