package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Motor;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Tracks;
import afk.ge.tokyo.ems.components.Velocity;

/**
 *
 * @author daniel
 */
public class TracksNode extends Node
{
    public Controller controller;
    public Tracks tankTracks;
    public State state;
    public Velocity velocity;
    public Motor motor;
}
