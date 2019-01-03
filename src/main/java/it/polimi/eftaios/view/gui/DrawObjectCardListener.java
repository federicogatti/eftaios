package it.polimi.eftaios.view.gui;

import it.polimi.eftaios.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe listener che implementa il metodo per inoltrare al controller il
 * comando da parte dell'utente di pescare una carta oggetto.
 * 
 * @author Jacopo Giuliani
 *
 */

public class DrawObjectCardListener implements ActionListener {

  private Controller controller;

  public DrawObjectCardListener(Controller controller) {

    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    controller.drawObjectCard();
  }
}
