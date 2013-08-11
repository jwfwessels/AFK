package afk.ge.tokyo.ems.nodes;

import afk.ge.tokyo.ems.Node;
import afk.ge.tokyo.ems.components.Bullet;
import afk.ge.tokyo.ems.components.ParentEntity;
import afk.ge.tokyo.ems.components.State;

/**
 *
 * @author Jw
 */
public class ProjectileNode extends Node
{

    public State state;
    public Bullet bullet;
    public ParentEntity relation;
}
