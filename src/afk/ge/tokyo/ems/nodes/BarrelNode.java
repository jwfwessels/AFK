package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Barrel;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.components.Weapon;

/**
 *
 * @author Daniel
 */
public class BarrelNode extends Node
{
    public Controller controller;
    public Barrel barrel;
    public State state;
    public Velocity velocity;
    public Weapon weapon;
    public Parent parent;
}
