package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Turret;
import afk.ge.tokyo.ems.components.Velocity;

/**
 *
 * @author Daniel
 */
public class TankTurretNode extends Node
{
    public Controller controller;
    public Turret turret;
    public State state;
    public Velocity velocity;
    public Parent offset;
}
