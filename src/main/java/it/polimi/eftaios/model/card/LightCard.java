package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.command.Command;
import it.polimi.eftaios.model.command.LightCommand;
import it.polimi.eftaios.view.gui.LightsCardImage;

import java.awt.Image;

public class LightCard extends ObjectCard {

  private static final long serialVersionUID = 1L;

  public LightCard() {
    super(true, true);
    name = "Carta Luci";
  }

  @Override
  public Command action() {
    return new LightCommand();
  }

  @Override
  public Image getImage() {
    return new LightsCardImage().getImage();
  }
}
