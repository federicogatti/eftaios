package it.polimi.eftaios.view.gui;

import it.polimi.eftaios.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackListener implements ActionListener {

  private Controller controller;

  public AttackListener(Controller controller) {

    this.controller = controller;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    controller.attack();
  }
}
