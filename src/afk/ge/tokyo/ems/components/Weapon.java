package afk.ge.tokyo.ems.components;

/**
 *
 * @author daniel
 */
public class Weapon {
    
    public float range, damage;
    public float fireInterval, timeSinceLastFire;
    public int ammo;

    public Weapon(float range, float damage, float fireInterval, float timeSinceLastFire, int ammo)
    {
        this.range = range;
        this.damage = damage;
        this.fireInterval = fireInterval;
        this.timeSinceLastFire = timeSinceLastFire;
        this.ammo = ammo;
    }
    
    
}
