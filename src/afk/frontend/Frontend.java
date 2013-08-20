package afk.frontend;

import afk.GameCoordinator;

/**
 *
 * @author Daniel
 */
public interface Frontend
{
    public void showMain();
    
    public void showGame(GameCoordinator game);
    
    public void showError(String message);
    
    public void showWarning(String message);
    
    public void showMessage(String message);
    
    public void showAlert(String message);
}
