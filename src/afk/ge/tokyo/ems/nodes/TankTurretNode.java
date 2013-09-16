package afk.ge.tokyo.ems.nodes;

import afk.ge.tokyo.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.TankTurret;
import afk.ge.tokyo.ems.components.Velocity;

/**
 *
 * @author Daniel
 */
public class TankTurretNode extends Node
{
    public Controller controller;
    public TankTurret turret;
    public State state;
    public Velocity velocity;
    public Parent offset;
}
