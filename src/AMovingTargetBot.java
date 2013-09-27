public class AMovingTargetBot extends EventDrivenRobot {

	private Direction direction = Direction.FORWARD;
	
	@Override
	public void beforeFirstStep() {
		issueCommand(IMMEDIATELY, INDEFINITELY, Action.MOVE, direction);
	}
	
	@Override
	public void beforeProcessEvents() {
		if (getTick() % 100 == 0) {
			println(String.format("commands {%d, %d, %d, %d, %d}", 
					countCommands(Action.MOVE), 
					countCommands(Action.TURN), 
					countCommands(Action.ATTACK), 
					countCommands(Action.AIM_HORIZONTAL), 
					countCommands(Action.AIM_VERTICAL)
				)
			);
			println(String.format("hp %2f", getHp()));
			println(String.format("pos %s", getPos().toString()));
			println(String.format("rot %s", getRot().toString()));
			println();
		}
	}

	@Override
	public void gotHit(GotHitEvent e) {
		println("gotHit");
	}

	@Override
	public void hitWall(HitWallEvent e) {
		println("hitWall");
		direction = (direction == Direction.FORWARD ? Direction.BACKWARD : Direction.FORWARD);
		cancelCommands(Action.MOVE, Action.TURN);
		issueCommand(IMMEDIATELY, INDEFINITELY, Action.MOVE, direction);
		issueCommand(IMMEDIATELY, INDEFINITELY, Action.TURN, Direction.CLOCKWISE);
	}

}
