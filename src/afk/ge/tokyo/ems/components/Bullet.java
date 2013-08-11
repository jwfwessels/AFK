package afk.ge.tokyo.ems.components;

import afk.ge.tokyo.ems.Entity;

/**
 *
 * @author daniel
 */
public class Bullet
{
    public float rangeLeft;
    public float damage;
    public Entity parent;

    public Bullet(float rangeLeft, float damage, Entity parent)
    {
        this.rangeLeft = rangeLeft;
        this.damage = damage;
        this.parent = parent;
    }
    
}
