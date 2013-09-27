
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import afk.ge.tokyo.ems.components.TargetingInfo;

public class GravityBot extends EventDrivenRobot {

  private final List<GravPoint> gravPoints = new Vector<GravPoint>();
  private double midpointstrength = 0; // The strength of the gravity point in the middle of the field
  private int midpointcount = 0; // Number of turns since that strength was changed.

  @Override
  public void beforeFirstStep() {
    //issueCommand(IMMEDIATELY, INDEFINITELY, Action.ATTACK, Direction.FORWARD);
  }
  
  @Override
  public void beforeProcessEvents() {
    gravPoints.clear();

    /*
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
    */
  }
  
  public void beforeExecuteAction() {
    calculateForceAndMove();
  }
  
  public void visibleBot(VisibleBotEvent e) {
    final TargetingInfo enemy = e.getTargetingInfo();
    println(String.format("Found enemy -> %s", enemy.pos));
    gravPoints.add(new GravPoint(enemy.pos.getX(), enemy.pos.getZ(), -1000));
  }
  
  /*
  private void addGravPoints() {
    //Cycle through all the enemy bullets.
    Enumeration<BulletInfoEnemy> enemyBullets = bot.enemyBullets.elements();
    while (enemyBullets.hasMoreElements()) {
      BulletInfoEnemy bullet = enemyBullets.nextElement();
      gravPoints.add(new GravPoint(bullet.x, bullet.y, -1000));
    }

    /**
     * The next section adds a middle point with a random (positive or
     * negative) strength. The strength changes every 5 turns, and goes
     * between -1000 and 1000.
     *
    midpointcount++;
    if (midpointcount > 5) {
      midpointcount = 0;
      midpointstrength = (Math.random() * 2000) - 1000;
    }
    gravPoints.add(new GravPoint(bot.getBattleFieldWidth() / 2, bot
        .getBattleFieldHeight() / 2, midpointstrength));

    //Other examples of GravPoints
    // Corners
    gravPoints.add(new GravPoint(bot.getBattleFieldWidth(), bot
        .getBattleFieldHeight(), -1000));
    gravPoints.add(new GravPoint(0, bot.getBattleFieldHeight(), -1000));
    gravPoints.add(new GravPoint(bot.getBattleFieldWidth(), 0, -1000));
    gravPoints.add(new GravPoint(0, 0, -1000));

    // GravPoint at random positions of the walls
    gravPoints.add(new GravPoint(Math.random() * bot.getBattleFieldWidth(),
        bot.getBattleFieldHeight(), -1000));
    gravPoints.add(new GravPoint(bot.getBattleFieldWidth(), Math.random()
        * bot.getBattleFieldHeight(), -1000));
    gravPoints.add(new GravPoint(0, Math.random()
        * bot.getBattleFieldHeight(), -1000));
    gravPoints.add(new GravPoint(Math.random() * bot.getBattleFieldWidth(),
        0, -1000));

  }*/

  private void calculateForceAndMove() {
    double xforce = 0;
    double zforce = 0;
    double force;
    double ang;

    Iterator<GravPoint> i = gravPoints.iterator();
    while (i.hasNext()) {
      GravPoint gravPoint = i.next();
      force = gravPoint.power
          / Math.pow(getRange(getPos().getY(), getPos().getZ(), gravPoint.x,
              gravPoint.y), 2);
      // Find the bearing from the point to us
      ang = normaliseBearing(Math.PI
          / 2
          - Math.atan2(getPos().getZ() - gravPoint.y, getPos().getY()
              - gravPoint.x));
      // Add the components of this force to the total force in their
      // respective directions
      xforce += Math.sin(ang) * force;
      zforce += Math.cos(ang) * force;
    } //if
    println(String.format("xforce = %.3f, zforce = %.3f", xforce, zforce));

    /**
     * The following four lines add wall avoidance. They will only affect us
     * if the bot is close to the walls due to the force from the walls
     * decreasing at a power 3.
     *
    xforce += 5000 / Math.pow(getRange(bot.getX(), bot.getY(), bot
        .getBattleFieldWidth(), bot.getY()), 3);
    xforce -= 5000 / Math.pow(getRange(bot.getX(), bot.getY(), 0, bot
        .getY()), 3);
    yforce += 5000 / Math.pow(getRange(bot.getX(), bot.getY(), bot.getX(),
        bot.getBattleFieldHeight()), 3);
    yforce -= 5000 / Math.pow(getRange(bot.getX(), bot.getY(), bot.getX(),
        0), 3);
     */

    // Move in the direction of our resolved force.
    println(String.format("currPos = %s", getPos()));
    goTo(getPos().getY() - xforce, getPos().getZ() - zforce);
  }

  //Move towards an x and y coordinate
  private void goTo(double x, double z) {
    double dist = 20;
    double angle = Math.toDegrees(absbearing(getPos().getY(),getPos().getZ(), x, z));
    double r = turnTo(angle);
    //bot.setAhead(dist * r);
    println(String.format("Goto %.3f", r));
    issueCommand(IMMEDIATELY, (long)(dist * r), Action.MOVE, Direction.FORWARD);
  }

  /**
   * Turns the shortest angle possible to come to a heading, then returns the
   * direction the the bot needs to move in.
   */
  private int turnTo(double angle) {
    double ang;
    int dir;
    println(String.format("currRot = %s", getRot()));
    ang = normaliseBearing(getRot().getW() - angle);
    if (ang > 90) {
      ang -= 180;
      dir = -1;
    } else if (ang < -90) {
      ang += 180;
      dir = -1;
    } else {
      dir = 1;
    }
    //bot.setTurnLeft(ang);
    println(String.format("turnTo %.3f", ang));
    issueCommand(IMMEDIATELY, (long)ang, Action.TURN, Direction.ANTICLOCKWISE);
    return dir;
  }

  // if a bearing is not within the -pi to pi range, alters it to provide the
  // shortest angle
  private double normaliseBearing(double ang) {
    if (ang > Math.PI)
      ang -= 2 * Math.PI;
    if (ang < -Math.PI)
      ang += 2 * Math.PI;
    return ang;
  }

  // returns the distance between two x,y coordinates
  private double getRange(double x1, double z1, double x2, double z2) {
    double xo = x2 - x1;
    double zo = z2 - z1;
    double h = Math.sqrt(xo * xo + zo * zo);
    return h;
  }

  // gets the absolute bearing between to x,y coordinates
  private double absbearing(double x1, double z1, double x2, double z2) {
    double xo = x2 - x1;
    double zo = z2 - z1;
    double h = getRange(x1, z1, x2, z2);
    if (xo > 0 && zo > 0) {
      return Math.asin(xo / h);
    }
    if (xo > 0 && zo < 0) {
      return Math.PI - Math.asin(xo / h);
    }
    if (xo < 0 && zo < 0) {
      return Math.PI + Math.asin(-xo / h);
    }
    if (xo < 0 && zo > 0) {
      return 2.0 * Math.PI - Math.asin(-xo / h);
    }
    return 0;
  }

  /** Holds the x, y, and strength info of a gravity point* */
  public class GravPoint {
    public double x, y, power;

    public GravPoint(double pX, double pY, double pPower) {
      x = pX;
      y = pY;
      power = pPower;
    }
  }
  
}
