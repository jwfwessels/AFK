package afk.ge.tokyo.ems;

/**
 *
 * @author daniel
 */
public interface ISystem
{
    public boolean start(Engine engine);
    public void update(double delta);
    public void end();
}
