package it.LeMarane.Sito.Data.Model;

/**
 *
 * @author alex
 */
public interface Image {
    
    int getID();
    
    String getURL();
    
    void setURL(String URL);
    
    String getDescription();
    
    void setDescription();
    
    String getName();
    
    void setName(String name);
    
    boolean isBanner();
    
    void setBanner(boolean banner);
    
    boolean isDirty();

    void setDirty(boolean dirty);
}
