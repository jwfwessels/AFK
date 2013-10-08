package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Helicopter;
import afk.ge.tokyo.ems.components.Motor;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Velocity;

/**
 *
 * @author daniel
 */
public class HelicopterNode extends Node
{
    public Controller controller;
    public Helicopter helicopter;
    public State state;
    public Velocity velocity;
    public Motor motor;
}
