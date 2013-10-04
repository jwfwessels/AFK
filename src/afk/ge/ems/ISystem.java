package afk.ge.ems;

/**
 *
 * @author daniel
 */
public interface ISystem
{
    public boolean init(Engine engine);
    public void update(float t, float dt);
    public void destroy();
}
