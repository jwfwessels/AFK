package afk.ge.ems;

/**
 *
 * @author Daniel
 */
public interface FlagManager
{
    public boolean getFlag(Object source, int symbol);
    public void setFlag(Object source, int symbol, boolean value);
}
