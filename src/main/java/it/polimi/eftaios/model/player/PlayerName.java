package it.polimi.eftaios.model.player;

/**
 * Enumerazione dei nomi dei giocatori, sia alieni che umani. Implementa i
 * metodi per ottenere il nome di un giocatore umano o alieno.
 * 
 * @author Jacopo Giuliani
 *
 */

public enum PlayerName {
  CAPITANO("Ennio Maria Dominoni"), PILOTA("Cabal"), PSICOLOGO(
          "Silvano Porpora"), SOLDATO("Piri"), PRIMOALIENO("Piero Ceccarella"), SECONDOALIENO(
          "Vittorio Martana"), TERZOALIENO("Maria Galbani"), QUARTOALIENO(
          "Paolo Landon");
  private final String name;
  private static final int NAME_OFFSET = 3;
  private static final int HUMAN_NUMBER = 4;
  private static final int ALIEN_NUMBER = 4;
  private static final int INDEX_OFFSET = 1;

  private PlayerName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  /**
   * Metodo che riceve come parametro un intero e ritorna il nome di un
   * giocatore umano, corrispondente all'elemento dell'array PlayerName.values()
   * che ha per indice l'intero passato come parametro - INDEX_OFFSET. Se il
   * parametro intero è maggiore o uguale al numero degli umani viene lanciata
   * un'eccezione.
   * 
   * @param currentPlayer
   *          l'intero che, una volta sottratto INDEX_OFFSET, rappresenta
   *          l'indice del nome del giocatore che si vuole ottenere.
   * @throws IndexOutOfBoundsException
   * @see java.lang.IndexOutOfBoundsException.IndexOutOfBoundsException
   * 
   * @return un elemento della enum PlayerName corrispondente ad un giocatore
   *         umano
   */

  public static PlayerName getHumanName(int currentPlayer) {
    if (currentPlayer >= 1 || currentPlayer < HUMAN_NUMBER) {
      PlayerName[] values = PlayerName.values();
      return values[currentPlayer - INDEX_OFFSET];
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Metodo che riceve come parametro un intero e ritorna il nome di un
   * giocatore alieno, corrispondente all'elemento dell'array
   * PlayerName.values() che ha per indice l'intero passato come parametro +
   * NAME_OFFSET. Se il parametro intero è maggiore o uguale al numero degi
   * alieni viene lanciata un'eccezione.
   * 
   * @param currentPlayer
   *          l'intero che, una volta sommato NAME_OFFSET, rappresenta l'indice
   *          del nome del giocatore che si vuole ottenere.
   * @throws IndexOutOfBoundsException
   * @see java.lang.IndexOutOfBoundsException.IndexOutOfBoundsException
   * 
   * @return un elemento della enum PlayerName corrispondente ad un giocatore
   *         alieno.
   */

  public static PlayerName getAlienName(int currentPlayer) {

    if (currentPlayer >= 1 || currentPlayer < ALIEN_NUMBER) {
      PlayerName[] values = PlayerName.values();
      return values[currentPlayer + NAME_OFFSET];
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Metodo che riceve come parametro un intero e ritorna il nome di un
   * giocatore, corrispondente all'elemento dell'array PlayerName.values() che
   * ha per indice l'intero passato come parametro - INDEX_OFFSET. Se il
   * parametro intero è maggiore o uguale al numero degi alieni sommato al
   * numero degli umani, viene lanciata un'eccezione.
   * 
   * @param currentPlayer
   *          l'intero che, una volta sottratto INDEX_OFFSET, rappresenta
   *          l'indice del nome del giocatore che si vuole ottenere.
   * @throws IndexOutOfBoundsException
   * @see java.lang.IndexOutOfBoundsException.IndexOutOfBoundsException
   * 
   * @return un elemento della enum PlayerName corrispondente ad un giocatore.
   */

  public static PlayerName getName(int currentPlayer) {
    if (currentPlayer >= 1 || currentPlayer < ALIEN_NUMBER + HUMAN_NUMBER) {
      PlayerName[] values = PlayerName.values();
      return values[currentPlayer - INDEX_OFFSET];
    } else {
      throw new IndexOutOfBoundsException();
    }
  }
}
