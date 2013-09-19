package afk.ge.tokyo.ems.components;

import java.util.UUID;

/**
 *
 * @author daniel
 */
public class Bullet
{
    public float rangeLeft;
    public float damage;
    public UUID parent;

    public Bullet(float rangeLeft, float damage, UUID parent)
    {
        this.rangeLeft = rangeLeft;
        this.damage = damage;
        this.parent = parent;
    }
    
}
