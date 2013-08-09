package afk.ge.tokyo.ems.nodes;

import afk.ge.tokyo.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.TankController;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.components.Weapon;

/**
 *
 * @author daniel
 */
public class TankControlNode extends Node
{
    public Controller controller;
    public TankController tankController;
    public Velocity velocity;
    public Weapon weapon;
}
