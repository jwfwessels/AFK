package afk.frontend;

/**
 *
 * @author Daniel
 */
public interface Frontend
{
    public void showMain();
    
    public void showGame();
    
    public void showError(String message);
    
    public void showWarning(String message);
    
    public void showMessage(String message);
    
    public void showAlert(String message);
}
