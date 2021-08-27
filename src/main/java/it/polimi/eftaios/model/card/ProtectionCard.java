package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.command.Command;
import it.polimi.eftaios.model.command.ProtectionCommand;
import it.polimi.eftaios.view.gui.DefenseCardImage;

import java.awt.Image;

public class ProtectionCard extends ObjectCard {

  private static final long serialVersionUID = 1L;

  public ProtectionCard() {
    super(true, false);
    name = "Carta Difesa";
  }

  @Override
  public Command action() {
    return new ProtectionCommand();
  }

  @Override
  public Image getImage() {
    return new DefenseCardImage().getImage();
  }

}
