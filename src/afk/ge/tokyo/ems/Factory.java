package afk.ge.tokyo.ems;

/**
 *
 * @author Daniel
 */
public interface Factory<R extends FactoryRequest>
{
    public Entity create(R request);
}
