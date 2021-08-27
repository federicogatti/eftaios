package it.polimi.eftaios.view.gui;

import it.polimi.eftaios.controller.Controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Classe listener di un JFrame. Gestisce le operazioni da eseguire alla
 * chiusura del JFrame.
 * 
 * @author Jacopo Giuliani
 *
 */

public class CloseWindowListener extends WindowAdapter {

  private Controller controller;
  private Integer ID;
  private ViewGui gui;

  public CloseWindowListener(Controller controller, ViewGui gui, Integer ID) {

    this.controller = controller;
    this.gui = gui;
    this.ID = ID;
  }

  /**
   * Metodo che definisce le operazioni da eseguire alla chiusura della
   * finestra. Viene chiusa la connessione del client identificato da ID e viene
   * chiamato il metodo peer chiudere la finestra e liberare le risorse.
   * 
   * @param e
   *          l'evento lanciato alla chiusura della finestra.
   */

  @Override
  public void windowClosing(WindowEvent e) {
    controller.close(ID);
    gui.closeWindow();
  }
}
