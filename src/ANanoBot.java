public class ANanoBot extends EventDrivenBot {

	@Override
	public void beforeFirstStep() {
		issueCommand(IMMEDIATELY, INDEFINITELY, Action.ATTACK, Direction.FORWARD);
		issueCommand(IMMEDIATELY, INDEFINITELY, Action.MOVE, Direction.FORWARD);
	}
	
	@Override
	public void hitWall(HitWallEvent e) {
		cancelCommands(Action.MOVE);
		issueCommand(IMMEDIATELY, 180, Action.TURN, Direction.CLOCKWISE);
		issueCommand(180, INDEFINITELY, Action.MOVE, Direction.FORWARD);
	}
	
	@Override
	public void beforeExecuteAction() {
		
	}

}
