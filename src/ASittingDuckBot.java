public class ASittingDuckBot extends EventDrivenBot {
	
	@Override
	public void beforeProcessEvents() {
		if (getTick() % 100 == 0) {
//			println(String.format("commands {%d, %d, %d, %d, %d}", 
//					countCommands(Action.MOVE), 
//					countCommands(Action.TURN), 
//					countCommands(Action.ATTACK), 
//					countCommands(Action.AIM_HORIZONTAL), 
//					countCommands(Action.AIM_VERTICAL)
//				)
//			);
//			println(String.format("hp %3.0f", getHp()));
//			println(String.format("pos %s", getPos().toString()));
//			println(String.format("rot %s", getRot().toString()));
//			println();
		}
	}

	@Override
	public void gotHit(GotHitEvent e) {
		println(String.format("gotHit %s", e.getSource()));
	}

	@Override
	public void hitWall(HitWallEvent e) {
		println("hitWall");
	}

}
