package afk.ge.tokyo.ems.components;

/**
 *
 * @author daniel
 */
public class Motor
{
    // TODO:
//    public float fuelLeft;
//    public float rateOfConsumption;
    public float topSpeed;
    
    // specific to tanks perhaps?
    public float angularVelocity;

    public Motor(float topSpeed, float angularVelocity)
    {
        this.topSpeed = topSpeed;
        this.angularVelocity = angularVelocity;
    }
    
    
}
