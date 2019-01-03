package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.command.Command;
import it.polimi.eftaios.model.command.TeleportCommand;
import it.polimi.eftaios.view.gui.TeleportCardImage;

import java.awt.Image;

public class TeleportCard extends ObjectCard {

  private static final long serialVersionUID = 1L;

  public TeleportCard() {
    super(true, true);
    name = "Carta Teletrasporto";
  }

  @Override
  public Command action() {
    return new TeleportCommand();
  }

  @Override
  public Image getImage() {
    return new TeleportCardImage().getImage();
  }
}
