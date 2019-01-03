package it.polimi.eftaios.view.gui;

import it.polimi.eftaios.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe listener che implementa il metodo per inoltrare al controller il
 * comando da parte dell'utente di scartare una carta oggetto.
 * 
 * @author Jacopo Giuliani
 *
 */

public class DiscardObjectCardListener implements ActionListener {

  private Controller controller;
  private ViewGui gui;

  public DiscardObjectCardListener(Controller controller, ViewGui gui) {

    this.controller = controller;
    this.gui = gui;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String card;
    card = gui.insertCard("Inserisci la Carta che vuoi scartare: ");
    controller.discardObjectCard(card);
  }
}
