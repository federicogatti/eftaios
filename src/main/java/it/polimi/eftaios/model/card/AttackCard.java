package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.command.AttackCommand;
import it.polimi.eftaios.model.command.Command;
import it.polimi.eftaios.view.gui.AttackCardImage;

import java.awt.Image;

public class AttackCard extends ObjectCard {

  private static final long serialVersionUID = 1L;

  public AttackCard() {
    super(true, true);
    name = "Carta Attacco";
  }

  @Override
  public Command action() {
    return new AttackCommand();
  }

  @Override
  public Image getImage() {
    return new AttackCardImage().getImage();
  }
}
