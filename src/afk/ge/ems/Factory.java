package afk.ge.ems;

/**
 *
 * @author Daniel
 */
public interface Factory<R extends FactoryRequest>
{
    public Entity create(R request);
}
