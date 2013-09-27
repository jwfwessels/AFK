public class AShootingBot extends EventDrivenRobot {

	@Override
	public void beforeFirstStep() {
		issueCommand(IMMEDIATELY, INDEFINITELY, Action.ATTACK, Direction.FORWARD);
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
	public void didHit(DidHitEvent e) {
		println(String.format("didHit %s", e.getTarget()));
	}
	
}
