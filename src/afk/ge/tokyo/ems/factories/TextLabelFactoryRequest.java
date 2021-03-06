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
