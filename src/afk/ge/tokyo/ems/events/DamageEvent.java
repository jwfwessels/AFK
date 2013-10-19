package afk.ge.tokyo.ems.events;

import afk.ge.ems.Event;
import afk.ge.tokyo.ems.components.Controller;

/**
 *
 * @author daniel
 */
public class DamageEvent extends Event
{
    private float amount;
    private Controller from;

    public DamageEvent(float amount, Controller from)
    {
        this.amount = amount;
        this.from = from;
    }

    public float getAmount()
    {
        return amount;
    }

    public Controller getFrom()
    {
        return from;
    }
}
