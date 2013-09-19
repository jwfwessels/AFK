package afk.ge.tokyo.ems.components;

/**
 *
 * @author Daniel
 */
public class TextLabel
{
    private String text;
    private boolean updated = true;

    public TextLabel(String text)
    {
        this.text = text;
    }

    public void setText(String text)
    {
        this.text = text;
        updated = true;
    }

    public String getText()
    {
        return text;
    }

    public boolean isUpdated()
    {
        return updated;
    }

    public void setUpdated(boolean updated)
    {
        this.updated = updated;
    }
    
    
}
