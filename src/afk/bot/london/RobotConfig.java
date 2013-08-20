/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot.london;

/**
 *
 * @author Jessica
 */
public class RobotConfig 
{
    String botName = "";
    String botInstanceType;
    // Colour defaults to red
    float botColourR;
    float botColourG;
    float botColourB;
    
    
    String botModel;
    
    public RobotConfig(String instanceType)
    {
        botInstanceType = instanceType;
        
        botName = botInstanceType;
        
        botColourR = 1;
        botColourG = 0;
        botColourB = 0;
        
        botModel = "Default";
    }
    
    public void setName(String name)
    {
        botName = name;
    }
    
    public String getName()
    {
        return botName;
    }
    
    public String getInstanceType()
    {
        return botInstanceType;
    }
    
    public float getColourR()
    {
        return botColourR;
    }
    
    public void setColourR(int r)
    {
        botColourR = r / 255;
    }
    
    public float getColourG()
    {
        return botColourG;
    }
    
    public void setColourG(int g)
    {
        botColourG = g / 255;
    }          
    
    public float getColourB()
    {
        return botColourB;
    }
    
    public void setColourB(int b)
    {
        botColourB = b / 255;
    }
}
