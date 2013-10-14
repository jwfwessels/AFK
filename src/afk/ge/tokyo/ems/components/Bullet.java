package afk.ge.tokyo.ems.components;

/**
 *
 * @author daniel
 */
public class Bullet
{
    public float rangeLeft = 0;
    public float damage = 0;
    public Controller parent = null;

    public Bullet()
    {
    }

    public Bullet(float rangeLeft, float damage, Controller parent)
    {
        this.rangeLeft = rangeLeft;
        this.damage = damage;
        this.parent = parent;
    }
    
}
