package it.polimi.eftaios.model.map;

import it.polimi.eftaios.exception.InvalidEscapeSectorException;
import it.polimi.eftaios.exception.InvalidMapFormatException;
import it.polimi.eftaios.exception.InvalidPositionException;
import it.polimi.eftaios.exception.LoadingMapException;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.model.sector.AlienSector;
import it.polimi.eftaios.model.sector.DangerousSector;
import it.polimi.eftaios.model.sector.EscapeSector;
import it.polimi.eftaios.model.sector.HumanSector;
import it.polimi.eftaios.model.sector.SafeSector;
import it.polimi.eftaios.model.sector.Sector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Carica da file la mappa, istanzia i settori, mantiene l'associazione
 * posizione-settori e fornisce tutti i metodi di interazione e manipolazione
 * della mappa.
 *
 * @author Riccardo Mologni
 *
 */
public final class GameMap implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final byte MAX_ROW = 14;
  private static final byte MAX_COLUMN = 23;
  private Position humanStartPosition;
  private Position alienStartPosition;
  private final HashMap<Position, Sector> map = new HashMap<Position, Sector>();
  private final HashMap<Sector, Position> escapePositions = new HashMap<Sector, Position>();
  private final static Logger LOGGER = Logger
          .getLogger(GameMap.class.getName());

  public GameMap(TypeOfMap mapType) {
    LOGGER.setLevel(Level.WARNING);
    try {
      loadMapFromFile(mapType.getFileName());
    } catch (FileNotFoundException e) {
      LOGGER.log(
              Level.WARNING,
              "Error: can not found the map file. Check name and location of file.",
              e);
      throw new LoadingMapException(e);
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Error: can not load the map file.", e);
      throw new LoadingMapException(e);
    } catch (InvalidMapFormatException e) {
      LOGGER.log(Level.WARNING, "Error: Incorrect File format.", e);
      throw new LoadingMapException(e);
    } catch (URISyntaxException e) {
      LOGGER.log(Level.WARNING, "Error: Unable to find path.", e);
      throw new LoadingMapException(e);
    }
  }

  public HashMap<Position, Sector> getMap() {
    return new HashMap<Position, Sector>(map);
  }

  public Position getHumanStartPosition() {
    return new Position(humanStartPosition.getColumnIndex(),
            humanStartPosition.getRowIndex());
  }

  public Position getAlienStartPosition() {
    return new Position(alienStartPosition.getColumnIndex(),
            alienStartPosition.getRowIndex());
  }

  public Sector getSector(Position position) {
    if (map.get(position) != null) {
      return map.get(position);
    } else {
      throw new InvalidPositionException();
    }
  }

  /**
   * Ritorna i settori adiacenti a una posizione data fino alla profondità
   * specificata. I settori direttamente adiacenti al settore di partenza sono a
   * profondità 1. Quelli adiacenti ai settori adiacenti a profondità 1 sono gli
   * adiacenti a profondità 2 e cosi via. Il settore di partenza non è adiacente
   * di se steso. I settori non attraversabili non propagano l'adiacenza, ovvero
   * i settori adiacenti a tali settori non vengono aggiunti all'insieme dei
   * settori adiacenti se non già presenti. Un settore è inserito nell'insieme
   * dei settori adiacenti solo se è valido. Un settore è valido se è presente
   * nella mappa, è a una profondità minore o uguale di quella specificata ed è
   * adiacente ad almeno un settore attraversabile. I settori adiacenti trovati
   * sono ritornati in un HashSet.
   * 
   * @param start
   *          settore da cui partire a calcolare l'adiacenza
   * @param depth
   *          profondità a cui arrivare nella ricerca dei settori adiacenti
   * @return Un set di settori adiacenti validi
   */
  public HashSet<Sector> getAdjacentValidSector(Position start, int depth) {
    HashSet<Sector> adjacentSectors = new HashSet<Sector>(findAdjacent(start,
            depth));
    if (depth > 1) {
      adjacentSectors.remove(getSector(start));
    }
    return adjacentSectors;
  }

  public Position getEscapePosition(String escapeName) {
    Sector sector = new EscapeSector(escapeName);
    Position escapePosition;
    escapePosition = escapePositions.get(sector);
    if (escapePosition == null) {
      throw new InvalidEscapeSectorException();
    } else {
      return escapePosition;
    }

  }

  /**
   * Metodo ricorsivo che calcola i settori adiacenti dei settori adiacenti a
   * profondità 1 a partire da un settore di partenza fino a quando il settore
   * di partenza è non attraversabile o la profondità 0. Se la profondità è 0
   * ritorna un set di settori vuoto, altrimenti ritorna un set di settori
   * adiacenti ottenuti aggiungendo a tale set i settori adiacenti ai settori a
   * adiacenti al settore di partenza.
   * 
   * @param start
   *          settore da cui partire a calcolare l'adiacenza
   * @param depth
   *          profondità a cui arrivare nella ricerca dei settori adiacenti
   * @return Un set di settori adiacenti
   */
  private HashSet<Sector> findAdjacent(Position start, int depth) {
    HashSet<Sector> adjacentSectors = new HashSet<Sector>();
    char columnIndex = start.getColumnIndex();
    int rowIndex = start.getRowIndex();

    if (depth == 0) {
      return adjacentSectors;
    }
    addAdjacentSectors(columnIndex, rowIndex - 1, adjacentSectors, depth);
    addAdjacentSectors(columnIndex, rowIndex + 1, adjacentSectors, depth);
    addAdjacentSectors((char) (columnIndex - 1), rowIndex, adjacentSectors,
            depth);
    addAdjacentSectors((char) (columnIndex + 1), rowIndex, adjacentSectors,
            depth);
    if ((columnIndex + 1 - 'A') % 2 == 0) { // colonne pari B,D,...,V
      addAdjacentSectors((char) (columnIndex - 1), rowIndex + 1,
              adjacentSectors, depth);
      addAdjacentSectors((char) (columnIndex + 1), rowIndex + 1,
              adjacentSectors, depth);
    } else {
      addAdjacentSectors((char) (columnIndex - 1), rowIndex - 1,
              adjacentSectors, depth);
      addAdjacentSectors((char) (columnIndex + 1), rowIndex - 1,
              adjacentSectors, depth);
    }
    return adjacentSectors;
  }

  /**
   * Aggiunge un settore a un set di settori adiacenti date le sue coordinate se
   * esiste nella mappa (è possibile creare una posizione a partire dalle
   * coordinate valide e a quella posizione corrisponde un settore). Se il
   * settore è attraversabile e la profondità è maggiore di 1 aggiunge anche
   * tutti i settori ad esso adiacenti.
   * 
   * @param columnIndex
   *          coordinata delle ascisse del settore candidato nella mappa
   * @param rowIndex
   *          coordinata delle ordinate del settore candidato nella mappa
   * @param adjacentSectors
   *          set di settori adiacenti in cui eventualmente mettere il settore
   *          candidato e i suoi adiacenti
   * @param depth
   *          profondità della ricerca di settori adiacenti
   */
  private void addAdjacentSectors(char columnIndex, int rowIndex,
          HashSet<Sector> adjacentSectors, int depth) {
    Position adjacentPosition;
    Sector candidateSector;
    try {
      adjacentPosition = new Position(columnIndex, rowIndex);
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.FINE, "fail to create Position " + columnIndex + ","
              + rowIndex, e);
      return;
    }
    if (map.containsKey(adjacentPosition)) {
      candidateSector = getSector(adjacentPosition);
      adjacentSectors.add(candidateSector);
      if (candidateSector.isCrossable()) {
        adjacentSectors.addAll(findAdjacent(adjacentPosition, depth - 1));
      }
    }
  }

  /**
   * Istanzia i settori e li associa a una posizione nella mappa a partire da un
   * file di testo specificato che rispetta il seguente formato: -il file è
   * composto da 14 righe; -ogni riga è composta da 23 caratteri che
   * identificano una topologia di settore o la loro assenza e, con l'eccezione
   * dell'ultima riga, i due caratteri \n\r; -i soli caratteri ammessi sono
   * s,d,a,h,e,x. A ogni carattere è associato un settore o una posizione vuota
   * della mappa secondo la convenzione che segue. a => alienSector d =>
   * DangerousSector s => safeSector h => humanSector e => escapeSector x =>
   * posizione vuota Crea inoltra l'associazione tra i settori scialuppa e le
   * posizioni. @see it.polimi.eftaios.model.sector.Sector
   * 
   * @param path
   *          percorso del file system in cui è memorizzato il file
   * @throws IOException
   * @see java.io.IOException
   * @throws FileNotFoundException
   * @see java.io.FileNotFoundException
   * @throws URISyntaxException
   * @see java.net.URISyntaxException
   */
  private void loadMapFromFile(String path) throws IOException,
          FileNotFoundException, URISyntaxException {
    FileReader mapToLoad;
    mapToLoad = new FileReader(new File(getClass().getResource(path).toURI()));

    char c;
    int rowIndex = 1;
    int columnIndex = 1;
    byte contEscapeSector = 1;
    boolean humanStartCreated = false;
    boolean alienStartCreated = false;

    do {
      do {
        c = (char) mapToLoad.read();
        switch (c) {
        case 's':
          map.put(new Position(columnIndex, rowIndex), new SafeSector(
                  columnIndex, rowIndex));
          columnIndex++;
          break;
        case 'd':
          map.put(new Position(columnIndex, rowIndex), new DangerousSector(
                  columnIndex, rowIndex));
          columnIndex++;
          break;
        case 'a':
          if (!alienStartCreated) {
            alienStartPosition = new Position(columnIndex, rowIndex);
            map.put(alienStartPosition, new AlienSector("-A-"));
            columnIndex++;
            alienStartCreated = true;
            break;
          } else {
            throw new InvalidMapFormatException();
          }
        case 'h':
          if (!humanStartCreated) {
            humanStartPosition = new Position(columnIndex, rowIndex);
            map.put(humanStartPosition, new HumanSector("-H-"));
            columnIndex++;
            humanStartCreated = true;
            break;
          } else {
            throw new InvalidMapFormatException();
          }
        case 'e':
          Position escapePosition = new Position(columnIndex, rowIndex);
          EscapeSector escapeSector = new EscapeSector("ES"
                  + contEscapeSector++);
          map.put(escapePosition, escapeSector);
          escapePositions.put(escapeSector, escapePosition);
          columnIndex++;
          break;
        case 'x': // zona della mappa priva di settori
          columnIndex++;
          break;
        case '\n': // ignoro il carattere Carriage Return
          break;
        case '\r': // ignoro il carattere Line Feed
          break;
        default:
          throw new InvalidMapFormatException();
        }
      } while (columnIndex <= MAX_COLUMN);
      rowIndex++;
      columnIndex = 1;
    } while (rowIndex <= MAX_ROW);
    mapToLoad.close();
  }

}