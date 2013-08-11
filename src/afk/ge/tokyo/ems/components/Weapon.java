package afk.ge.tokyo.ems.components;

/**
 *
 * @author daniel
 */
public class Weapon {
    
    public float range, damage, speed;
    public float fireInterval, timeSinceLastFire;
    public int ammo;

    public Weapon(float range, float damage, float speed, float fireInterval, float timeSinceLastFire, int ammo)
    {
        this.range = range;
        this.damage = damage;
        this.speed = speed;
        this.fireInterval = fireInterval;
        this.timeSinceLastFire = timeSinceLastFire;
        this.ammo = ammo;
    }
    
    
}
