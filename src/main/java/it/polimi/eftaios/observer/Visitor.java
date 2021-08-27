package it.polimi.eftaios.observer;

/**
 * L'interfaccia descrive tutti gli eventi che il match può generare
 * 
 * @author Federico
 *
 */
public interface Visitor {

  void visit(Event event);

  void visit(AttackEvent event);

  void visit(CurrentStateEvent event);

  void visit(ObjectAddToHandEvent event);

  void visit(ObjectCardEvent event);

  void visit(PlayerExcapedEvent event);

  void visit(DrawSectorCardEvent event);

  void visit(ChangePositionEvent event);

  void visit(DangerousStateEvent event);

  void visit(SafeStateEvent event);

  void visit(EscapeStateEvent event);

  void visit(NotifyAllPlayerEvent event);

  void visit(HandFullEvent event);

  void visit(DiscardObjectCardEvent event);

  void visit(CommandEvent event);

  void visit(ShallopDamageEvent event);

  void visit(EndMatchEvent event);

  void visit(LightEvent event);

  void visit(NewMatchEvent event);

  void visit(MoveNotPermittedExceptionEvent event);

  void visit(CanNotUseCardExceptionEvent event);

  void visit(PlayerCanNotAttackExceptionEvent event);

  void visit(InvalidCommandExceptionEvent event);

  void visit(EndTurnEvent event);

  void visit(ClientIDGrantEvent event);

  void visit(CloseConnectionEvent event);

  void visit(UseObjectCardEvent event);
}
