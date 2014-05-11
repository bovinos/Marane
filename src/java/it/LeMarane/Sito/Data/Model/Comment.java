package it.LeMarane.Sito.Data.Model;

import java.util.Date;

/**
 *
 * @author alex
 */
public interface Comment {

    int getID();
    
    String getAuthor();
    
    void setAuthor(String author);
    
    String getText();

    void setText(String text);

    Date getDate();

    void setDate(Date date);

    boolean isDirty();

    void setDirty(boolean dirty);
    
    /*====================
            RELAZIONI
     =====================*/
    
    Admin getAdmin();
    
    void setAdmin(Admin admin);
    
    boolean isPostedByAdmin();
    
    Post getPost();
    
    void setPost(Post post);

}
