package it.LeMarane.Sito.Data.Model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author alex
 */
public interface Post {

    int getID();

    String getTitle();

    void setTitle(String title);

    String getText();

    void setText(String text);

    Date getDate();

    void setDate(Date date);

    boolean isDirty();

    void setDirty(boolean dirty);
    
    void copyFrom(Post post);
    
    /*====================
     RELAZIONI
     =====================*/
    
    Admin getAuthor();
    
    void setAuthor(Admin author);
    
    List<Image> getImages();
    
    void setImages(List<Image> images);
    
    void addImage(Image image);
    
    void removeLastImage();
    
    void removeFirstImage();
    
    Image getImage(int position_image);
    
    void removeImage(int position_image);
}
