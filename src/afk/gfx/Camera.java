/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
     * @param aspect The aspect ratio of the scene.
     */
    void updateProjection(float w, float h);

    /**
     * Call this when you need the view matrix updated. Also updates the
     * direction and right vectors.
     */
    void updateView();
    
}
