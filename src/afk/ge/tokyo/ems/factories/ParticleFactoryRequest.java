package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;
import afk.ge.tokyo.ems.components.ParticleEmitter;
import afk.ge.tokyo.ems.components.State;

/**
 *
 * @author Daniel
 */
public class ParticleFactoryRequest implements FactoryRequest
{
    protected ParticleEmitter emitter;
    protected State state;

    public ParticleFactoryRequest(ParticleEmitter emitter, State state)
    {
        this.emitter = emitter;
        this.state = state;
    }
}
