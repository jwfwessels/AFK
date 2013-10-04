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
    public float topSpeed = 0;
    
    // specific to tanks perhaps?
    public float angularVelocity = 0;

    public Motor()
    {
    }

    public Motor(float topSpeed, float angularVelocity)
    {
        this.topSpeed = topSpeed;
        this.angularVelocity = angularVelocity;
    }
    
    
}
