package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Sonar;
import afk.ge.tokyo.ems.components.State;

/**
 *
 * @author Daniel
 */
public class SonarNode extends Node
{
    public Sonar sonar;
    public State state;
    public Controller controller;
}
