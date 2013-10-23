package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;

/**
 *
 * @author daniel
 */
public class TextLabelFactoryRequest implements FactoryRequest
{

    protected String text;
    protected Integer top, right, bottom, left;

    public TextLabelFactoryRequest(String text, int x, int y)
    {
        this(text,y,null,null,x);
    }
    
    public TextLabelFactoryRequest(String text, Integer top, Integer right,
            Integer bottom, Integer left)
    {
        this.text = text;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }
    
    public TextLabelFactoryRequest withText(String text)
    {
        return new TextLabelFactoryRequest(text, top, right, bottom, left);
    }
    
    public TextLabelFactoryRequest atPosition(int x, int y)
    {
        return new TextLabelFactoryRequest(text, x, y);
    }
    
    public TextLabelFactoryRequest atPosition(Integer top,
            Integer right, Integer bottom, Integer left)
    {
        return new TextLabelFactoryRequest(text, top, right, bottom, left);
    }
}
