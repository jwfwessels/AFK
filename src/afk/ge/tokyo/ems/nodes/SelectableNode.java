package afk.ge.tokyo.ems.nodes;

import afk.ge.ems.Node;
import afk.ge.tokyo.ems.components.BBoxComponent;
import afk.ge.tokyo.ems.components.Selectable;
import afk.ge.tokyo.ems.components.State;

/**
 *
 * @author Daniel
 */
public class SelectableNode extends Node
{
    public Selectable selectable;
    public State state;
    public BBoxComponent bbox;
}
