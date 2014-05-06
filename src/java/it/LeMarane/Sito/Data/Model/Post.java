package it.LeMarane.Sito.Data.Model;

import java.util.Date;

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

}
