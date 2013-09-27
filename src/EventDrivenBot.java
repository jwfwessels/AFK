import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

import afk.bot.london.TankRobot;
import afk.ge.tokyo.ems.components.TargetingInfo;

/**
 * This is a base class for a bot which processes events from it's environment
 * and then issues commands based on the inputs received.
 * 
 * @author jheyns
 */
public abstract class EventDrivenBot extends TankRobot {
	
	public static long IMMEDIATELY = 0;
	public static long INDEFINITELY = 999999999;
	
	private long tick;
	private ArrayList<Command> commands;
	
	public EventDrivenBot() {
		tick = 0;
		commands = new ArrayList<Command>();
	}
	
	/**
	 * Override to provide initial state or instructions.
	 */
	public void beforeFirstStep() { }
	
	@Override
	public final void run() {
		if (tick == 0) {
			try {
				beforeFirstStep();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			beforeProcessEvents();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		processEvents();
		try {
			beforeExecuteAction();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		executeAction();
		tick++;
	}
	
	/**
	 * Override to do something before events are processed.
	 */
	public void beforeProcessEvents() { }

	public final void processEvents() {
		if (events.didHit != null && events.didHit.size() > 0) {
			for (UUID target: events.didHit) {
				try {
					didHit(new DidHitEvent(tick, target));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (events.gotHit != null && events.gotHit.size() > 0) {
			for (UUID source: events.gotHit) {
				try {
					gotHit(new GotHitEvent(tick, source));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if (events.hitWall) {
			try {
				hitWall(new HitWallEvent(tick));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		List<TargetingInfo> visibleBots = events.visibleBots;
		if (visibleBots != null && visibleBots.size() > 0) {
			for (int i = 0; i < visibleBots.size(); i ++) {
				try {
					visibleBot(new VisibleBotEvent(tick, visibleBots.get(i)));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public abstract class BotEvent {
		private long tick;
		public BotEvent(long tick){
			this.tick = tick;
		}
		public long getTick() {
			return tick;
		}
	}
	
	@Deprecated
	public void didHit(DidHitEvent e) { } // Does not work yet
	public class DidHitEvent extends BotEvent {
		private UUID target;
		public DidHitEvent(long tick, UUID target) {
			super(tick);
			this.target = target;
		}
		public UUID getTarget() {
			return target;
		}
	}
	
	public void gotHit(GotHitEvent e) { }
	public class GotHitEvent extends BotEvent {
		private UUID source;
		public GotHitEvent(long tick, UUID source) {
			super(tick);
			this.source = source;
		}
		public UUID getSource() {
			return source;
		}
	}
	
	public void hitWall(HitWallEvent e) { }
	public class HitWallEvent extends BotEvent {
		public HitWallEvent(long tick) {
			super(tick);
		}
	}
	
	public void visibleBot(VisibleBotEvent e) { }
	public class VisibleBotEvent extends BotEvent {
		private TargetingInfo info;
		public VisibleBotEvent(long tick, TargetingInfo info) {
			super(tick);
			this.info = info;
		}
		public TargetingInfo getTargetingInfo() {
			return info;
		}
	}
	
	/**
	 * Override to do something before the actions which is currently set is executed.
	 */
	public void beforeExecuteAction() { }

	public final void executeAction() {
		// Select the command to execute
		Direction move = null;
		Direction turn = null;
		Direction attack = null;
		Direction aimHorizontal = null;
		Direction aimVertical = null;
		
		ArrayList<Command> remove = new ArrayList<Command>();
		for (Command command: commands) {
			if (command.delay <= 0 && command.duration > 0) {
				switch (command.action) {
					case MOVE:
						move = command.direction;
						break;
						
					case TURN: 
						turn = command.direction;
						break;
					
					case ATTACK: 
						attack = command.direction;
						break;
					
					case AIM_HORIZONTAL:
						aimHorizontal = command.direction;
						break;
					
					case AIM_VERTICAL:
						aimVertical = command.direction;
						break;
				}
				command.duration--;
				if (command.duration <= 0) {
					remove.add(command);
				}
			}
			command.delay--;
		}
		commands.removeAll(remove);
		
		// Issue commands
		if (move == Direction.FORWARD) {
			moveForward();
		}
		else if (move == Direction.BACKWARD) {
			moveBackwards();
		}
		
		if (turn == Direction.CLOCKWISE) {
			turnClockwise();
		}
		else if (turn == Direction.ANTICLOCKWISE) { 
			turnAntiClockwise();
		}
		
		if (attack == Direction.FORWARD) {
			attack();
		}
		
		if (aimHorizontal == Direction.CLOCKWISE) {
			aimClockwise(); 
		}
		else if (aimHorizontal == Direction.ANTICLOCKWISE) {
			aimAntiClockwise();
		}
		
		if (aimVertical == Direction.UP) {
			aimUp();
		}
		else if (aimVertical == Direction.DOWN) {
			aimDown();
		}
	}
	
	public enum Action {
		MOVE,
		TURN,
		ATTACK,
		AIM_HORIZONTAL,
		AIM_VERTICAL
	}
	
	public enum Direction {
		FORWARD,
		BACKWARD,
		CLOCKWISE,
		ANTICLOCKWISE,
		UP,
		DOWN
	}
	
	private class Command {
		long delay;
		long duration;
		Action action;
		Direction direction;
		public Command(long delay, long duration, Action action, Direction direction) {
			this.delay = delay;
			this.duration = duration;
			this.action = action;
			this.direction = direction;
		}
	}
	
	public void issueCommand(long delay, long duration, Action action, Direction direction) {
		Command command = new Command(delay, duration, action, direction);
		commands.add(command);
		Collections.sort(commands, new Comparator<Command>() {
			@Override
			public int compare(Command left, Command right) {
				int result = 0;
				if (left.delay < right.delay) {
					result = -1;
				}
				else if (left.delay == right.delay) {
					result = 0;
				}
				else if (left.delay > right.delay) {
					result = 1;
				}
				return result;
			}
		});
	}
	
	public void cancelCommands(Action... actions) {
		if (actions != null) {
			ArrayList<Command> remove = new ArrayList<Command>();
			for (Command command: commands) {
				for (int i = 0; i < actions.length; i++) {
					if (command.action == actions[i]) {
						remove.add(command);
					}
				}
			}
			commands.removeAll(remove);
		}
	}
	
	public int countCommands(Action action) {
		int result = 0;
		for (Command command: commands) {
			if (command.action == action) {
				result++;
			}
		}
		return result;
	}
	
	public float getHp() {
		return events.hp;
	}
	
	public Vec3 getPos() {
		return events.pos;
	}
	
	public Vec4 getRot() {
		return events.rot;
	}
	
	public long getTick() {
		return tick;
	}
	
	public void println() {
		println("");
	}
	
	public void println(String message) {
		System.out.println(String.format("%d %s[%s]: %s", getTick(), this.getClass().getSimpleName(), getId().toString().substring(0, 4), message));
	}
	
}
