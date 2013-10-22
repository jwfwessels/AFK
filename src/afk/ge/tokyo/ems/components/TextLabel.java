package afk.ge.tokyo.ems.components;

/**
 *
 * @author Daniel
 */
public class TextLabel
{
    private String text = "";
    private boolean selected = false;
    private boolean updated = true;

    public TextLabel()
    {
    }
    
    public TextLabel(String text)
    {
        this.text = text;
    }

    public void setText(String text)
    {
        this.text = text;
        updated = true;
    }
    
    public void setSelected(boolean selected)
    {
        this.selected = selected;
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

    public boolean isSelected()
    {
        return selected;
    }
}
