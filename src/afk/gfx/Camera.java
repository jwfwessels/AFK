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
 package afk.gfx;

/**
 *
 * @author Daniel
 */
public interface Camera
{

    /**
     * Moves the camera forward.
     * @param amount The amount to move by. Use a negative number for backwards.
     */
    void moveForward(float amount);

    /**
     * Move the camera to the right.
     * @param amount The amount to move by. Use a negative number to move left.
     */
    void moveRight(float amount);

    /**
     * Rotate the camera.
     * @param lateral The lateral (left-right) rotation.
     * @param vertical The vertical (up-down) rotation.
     */
    void rotate(float lateral, float vertical);

    /**
     * Call this when you need the projection matrix updated.
     * @param w The width of the scene.
     * @param h The height of the scene.
     */
    void updateProjection(float w, float h);

    /**
     * Call this when you need the view matrix updated. Also updates the
     * direction and right vectors.
     */
    void updateView();
    
}
