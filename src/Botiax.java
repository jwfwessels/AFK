
public class Botiax extends EventDrivenRobot {

    private Direction turnDirection = Direction.CLOCKWISE;
    private Direction aimDirection = Direction.CLOCKWISE;
    private Direction moveDirection = Direction.FORWARD;

    @Override
    public void beforeFirstStep() {
        issueCommand(IMMEDIATELY, INDEFINITELY, Action.MOVE, moveDirection);
        double rand = Math.random();
        long temp = (long) (rand * 100) + 40;
        System.out.println(rand);
        issueCommand(0, temp, Action.TURN, (rand > 0.5?Direction.CLOCKWISE:Direction.ANTICLOCKWISE));
    }

    @Override
    public void hitWall(HitWallEvent e) {
        if (getTick() % 100 == 0) {
            cancelCommands(Action.MOVE, Action.TURN);
            double rand = Math.random();
            long temp = (long) (rand * 100) + 50;
            moveDirection = (moveDirection == Direction.FORWARD ? Direction.BACKWARD : Direction.FORWARD);
            issueCommand(0, temp, Action.MOVE, moveDirection);
            issueCommand(temp/2, temp/2, Action.TURN, turnDirection);
            issueCommand(temp, temp, Action.TURN, turnDirection);
            moveDirection = (moveDirection == Direction.FORWARD ? Direction.BACKWARD : Direction.FORWARD);
            issueCommand(temp, INDEFINITELY, Action.MOVE, moveDirection);
        }
    }

    @Override
    public void visibleBot(VisibleBotEvent e) {
        if (getTick() % 20 == 0) {
            cancelCommands(Action.TURN, Action.MOVE, Action.AIM_HORIZONTAL);
            if(e.getTargetingInfo().bearing < 0){
                aimDirection = Direction.ANTICLOCKWISE;
                issueCommand(0, (long)e.getTargetingInfo().bearing, Action.TURN, aimDirection);
            }
            else if(e.getTargetingInfo().bearing > 0){
                aimDirection = Direction.CLOCKWISE;
                issueCommand(0, (long)e.getTargetingInfo().bearing, Action.AIM_HORIZONTAL, aimDirection);
            }
            if(Math.abs(e.getTargetingInfo().bearing) < 10){
                issueCommand(0, 9, Action.ATTACK, Direction.FORWARD);
                issueCommand(9, INDEFINITELY, Action.MOVE, moveDirection);
            }
        }
    }

    @Override
    public void gotHit(GotHitEvent e) {
        cancelCommands(Action.MOVE, Action.TURN);
        issueCommand(0, 120, Action.TURN, turnDirection);
        issueCommand(IMMEDIATELY, INDEFINITELY, Action.MOVE, moveDirection);
    }
}
