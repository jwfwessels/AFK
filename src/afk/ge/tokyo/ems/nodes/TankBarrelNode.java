package afk.ge.tokyo.ems.nodes;

import afk.ge.tokyo.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Life;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.TankBarrel;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.components.Weapon;

/**
 *
 * @author Daniel
 */
public class TankBarrelNode extends Node
{
    public Controller controller;
    public TankBarrel barrel;
    public State state;
    public Velocity velocity;
    public Weapon weapon;
    public Parent parent;
}
