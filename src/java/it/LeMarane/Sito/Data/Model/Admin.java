package it.LeMarane.Sito.Data.Model;

/**
 *
 * @author alex
 */
public interface Admin {
    
    int getID();
    
    String getUsername();
    
    void setUsername(String username);
    
    String getPassword();
    
    void setPassword(String password);
    
    boolean isDirty();
    
    void setDirty(boolean dirty);    
}
