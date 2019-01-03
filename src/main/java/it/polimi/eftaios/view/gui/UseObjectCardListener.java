package it.polimi.eftaios.view.gui;

import it.polimi.eftaios.controller.Controller;
import it.polimi.eftaios.model.position.Position;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe listener che implementa il metodo per inoltrare al controller il
 * comando da parte dell'utente di usare una carta oggetto.
 * 
 * @author Jacopo Giuliani
 *
 */

public class UseObjectCardListener implements ActionListener {

  private Controller controller;
  private ViewGui gui;

  public UseObjectCardListener(Controller controller, ViewGui gui) {

    this.controller = controller;
    this.gui = gui;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    Position position = new Position('A', 1);
    String usedCard = gui
            .insertCard("Inserisci la Carta che vuoi utilizzare: ");
    if ("Carta Luci".equals(usedCard)) {
      position = gui
              .insertPosition("Inserisci il settore che vuoi illuminare: ");
    }
    controller.useObjectCard(usedCard, position);
  }
}
