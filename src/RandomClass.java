public class RandomClass
{
    private int movement = 0;
    private int rotation = 0;
    private boolean turning = true;

    public boolean getTurning()
    {
        return turning;
    }
    
    public void setMovement(int m)
    {
        movement = m;
    }
    
    public void setTurning(boolean t)
    {
        turning = t;
    }
    
    public void setRotation(int r)
    {
        rotation = r;
    }
     
    public boolean turn()
    {
        if(rotation > 0)
        {
            //turnAntiClockwise();
            rotation--;
            return true;
        }
        else
        {
            rotation = (int)(Math.random()*360);
            turning = false;
            return false;
        }
    }
    
    public boolean move()
    {
        if (movement > 0)
        {
            //moveForward();
            movement--;
            return true;
        }
        else
        {
            movement = (int)(Math.random()*800);
            turning = true;
            return false;
        }
    }
}