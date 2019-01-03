package it.polimi.eftaios.controller;

import java.io.Serializable;

/**
 * Richiesta che consente al client di chiedere l'esecuzione di un determinata
 * azione messa a disposizione dal server controller allo scopo di modificare il
 * model presente sul server. Una richiesta è costituita da una stringa che
 * identifica il comando da eseguire e un numero variabile di parametri che
 * devono essere passati al comando.
 * 
 * @author Riccardo Mologni
 *
 */
public class Request implements Serializable {

  private static final long serialVersionUID = 1L;
  private final String command;
  private final Serializable[] args;
  private final Class<?>[] parametersClass;

  public Request(String command, Serializable... arguments) {
    this.command = command;
    args = arguments;
    parametersClass = new Class<?>[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      parametersClass[i] = arguments[i].getClass();
    }
  }

  public String getCommand() {
    return command;
  }

  public Serializable[] getArgs() {
    return args;
  }

  public Class<?>[] getParametersType() {
    return parametersClass;
  }
}
