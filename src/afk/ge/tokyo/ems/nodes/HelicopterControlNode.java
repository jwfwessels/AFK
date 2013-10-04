package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.HelicopterController;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.components.Weapon;

/**
 *
 * @author daniel
 */
public class HelicopterControlNode extends Node
{
    public Controller controller;
    public HelicopterController heliController;
    public Velocity velocity;
    public Weapon weapon;
}
