package afk.ge.tokyo.ems.components;

/**
 *
 * @author daniel
 */
public class Weapon {
    
    public float range = 0, damage = 0, speed = 0;
    public float fireInterval = 0, timeSinceLastFire = 0;
    public int ammo = 0;

    public Weapon()
    {
    }

    public Weapon(float range, float damage, float speed, float fireInterval, int ammo)
    {
        this.range = range;
        this.damage = damage;
        this.speed = speed;
        this.fireInterval = fireInterval;
        this.timeSinceLastFire = fireInterval;
        this.ammo = ammo;
    }
    
    
}
