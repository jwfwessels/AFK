package afk.ge.tokyo.ems.components;

import java.util.UUID;

/**
 *
 * @author daniel
 */
public class Bullet
{
    public float rangeLeft = 0;
    public float damage = 0;
    public UUID parent = null;

    public Bullet()
    {
    }

    public Bullet(float rangeLeft, float damage, UUID parent)
    {
        this.rangeLeft = rangeLeft;
        this.damage = damage;
        this.parent = parent;
    }
    
}
