package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Motor;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.TankTracks;
import afk.ge.tokyo.ems.components.Velocity;

/**
 *
 * @author daniel
 */
public class TankTracksNode extends Node
{
    public Controller controller;
    public TankTracks tankTracks;
    public State state;
    public Velocity velocity;
    public Motor motor;
}
