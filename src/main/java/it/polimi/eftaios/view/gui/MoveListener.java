package it.polimi.eftaios.view.gui;

import it.polimi.eftaios.controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe listener che implementa il metodo per inoltrare al controller il
 * comando da parte dell'utente di spostarsi in un nuovo settore.
 * 
 * @author Jacopo Giuliani
 *
 */

public class MoveListener implements ActionListener {

  private Controller controller;
  private ViewGui gui;
  private static final Pattern PATTERN = Pattern.compile("([A-W])(.)([0-9])");

  public MoveListener(Controller controller, ViewGui gui) {

    this.controller = controller;
    this.gui = gui;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    Matcher m = PATTERN.matcher(gui.getMoveDestination());
    if (m.matches()) {
      controller.move(gui.getMoveDestination());
    } else {
      gui.writeResult("Formato settore non valido");
    }
  }
}
