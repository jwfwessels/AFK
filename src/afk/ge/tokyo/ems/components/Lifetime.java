package afk.ge.tokyo.ems.components;

/**
 *
 * @author jwfwessels
 */
public class Lifetime
{
    public float maxLife;
    public float life;

    public Lifetime(float life)
    {
        this.maxLife = life;
        this.life = life;
    }
}
