package afk.ge.tokyo.ems.components;

/**
 *
 * @author jwfwessels
 */
public class Lifetime
{
    public float maxLife = 0;
    public float life = 0;

    public Lifetime()
    {
    }

    public Lifetime(float life)
    {
        this.maxLife = life;
        this.life = life;
    }
}
