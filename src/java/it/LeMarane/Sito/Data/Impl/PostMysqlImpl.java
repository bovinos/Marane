package it.LeMarane.Sito.Data.Impl;

import it.LeMarane.Sito.Data.Model.Admin;
import it.LeMarane.Sito.Data.Model.Image;
import it.LeMarane.Sito.Data.Model.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alex
 */
public class PostMysqlImpl implements Post {

    private int ID;
    private String title;
    private String text;
    private Date date;
    protected SitoDataLayerMysqlImpl dataLayer; // per le query 
    protected boolean dirty;

    private Admin author;                  // relazione
    private List<Image> images;            // relazione

    public PostMysqlImpl(SitoDataLayerMysqlImpl dataLayer) {
        this.dataLayer = dataLayer;
        this.ID = 0;
        this.title = "";
        this.text = "";
        this.date = null;
        this.dirty = false;
        this.author = null;
        this.images = null;
    }

    public PostMysqlImpl(SitoDataLayerMysqlImpl dataLayer, ResultSet rs) throws SQLException {
        this(dataLayer);
        this.ID = rs.getInt("ID");
        this.title = rs.getString("title");
        this.text = rs.getString("text");
        this.date = rs.getDate("date");
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        this.dirty = true;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        this.dirty = true;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
        this.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /*====================
     RELAZIONI
     =====================*/
    @Override
    public Admin getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(Admin author) {
        this.author = author;
        this.dirty = true;
    }

    @Override
    public List<Image> getImages() {
        return images;
    }

    @Override
    public void setImages(List<Image> images) {
        this.images = images;
        this.dirty = true;
    }

    @Override
    public void addImage(Image image) {
        this.images.add(image);
        this.dirty = true;
    }

    @Override
    public void removeLastImage() {
        this.images.remove(this.images.size() - 1);
        this.dirty = true;
    }

    @Override
    public void removeFirstImage() {
        this.images.remove(0);
        this.dirty = true;
    }

    @Override
    public Image getImage(int position_image) {
        return this.images.get(position_image);
    }

    @Override
    public void removeImage(int position_image) {
        this.images.remove(position_image);
        this.dirty = true;
    }

    @Override
    public void copyFrom(Post post) {
        this.ID = post.getID();
        this.date = post.getDate();
        this.text = post.getText();
        this.title = post.getTitle();
        this.author = null;
        this.images = null;
        this.dirty = true;
    }

}
