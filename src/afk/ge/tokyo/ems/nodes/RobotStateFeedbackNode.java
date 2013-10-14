package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.RobotToken;
import afk.ge.tokyo.ems.components.State;

/**
 *
 * @author Daniel
 */
public class RobotStateFeedbackNode extends Node
{
    public RobotToken robot;
    public Controller controller;
    public State state;
}
