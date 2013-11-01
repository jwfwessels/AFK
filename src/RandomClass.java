/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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