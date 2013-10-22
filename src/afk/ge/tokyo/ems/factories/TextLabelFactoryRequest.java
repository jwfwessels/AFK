package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;

/**
 *
 * @author daniel
 */
public class TextLabelFactoryRequest implements FactoryRequest
{
    protected String text;
    protected int x;
    protected int y;

    public TextLabelFactoryRequest(String text, int x, int y)
    {
        this.text = text;
        this.x = x;
        this.y = y;
    }
}
