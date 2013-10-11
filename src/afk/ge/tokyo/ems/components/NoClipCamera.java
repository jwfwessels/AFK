package afk.ge.tokyo.ems.components;

/**
 *
 * @author Daniel
 */
public class NoClipCamera
{
    public float normalSpeed = 0;
    public float sprintSpeed = 0;
    public float sensitivity = 0;

    public NoClipCamera()
    {
    }

    public NoClipCamera(float normalSpeed, float sprintSpeed, float sensitivity)
    {
        this.normalSpeed = normalSpeed;
        this.sprintSpeed = sprintSpeed;
        this.sensitivity = sensitivity;
    }
    
}
