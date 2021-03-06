package it.LeMarane.Sito.Data.Impl;

import it.LeMarane.Sito.Data.Model.Admin;
import it.LeMarane.Sito.Data.Model.Comment;
import it.LeMarane.Sito.Data.Model.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author alex
 */
public class CommentMysqlImpl implements Comment {

    private int ID;
    private String author;
    private String text;
    private Date date;
    protected SitoDataLayerMysqlImpl dataLayer; // per le query
    protected boolean dirty;

    private Admin admin;  // relazione
    private int adminID;    // chiave esterna
    private Post post;       // relazione
    private int postID;      // chiave esterna

    public CommentMysqlImpl(SitoDataLayerMysqlImpl dataLayer) {
        this.dataLayer = dataLayer;
        this.ID = 0;
        this.author = "";
        this.text = "";
        this.date = null;
        this.dirty = false;
        this.admin = null;
        this.adminID = 0;
        this.post = null;
        this.postID = 0;
    }

    public CommentMysqlImpl(SitoDataLayerMysqlImpl dataLayer, ResultSet rs) throws SQLException {
        this(dataLayer);
        this.ID = rs.getInt("ID");
        this.author = rs.getString("author");
        this.text = rs.getString("text");
        this.date = rs.getDate("date");
        this.adminID = rs.getInt("adminID"); // e se è null?
        this.postID = rs.getInt("postID");
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
        this.dirty = true;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        this.dirty = true;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
        this.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public void copyFrom(Comment comment) {
        this.ID = comment.getID();
        this.author = comment.getAuthor();
        this.date = comment.getDate();
        this.text = comment.getText();
        this.admin = null;
        this.adminID = comment.getAdmin().getID();
        this.post = null;
        this.postID = comment.getPost().getID();
        this.dirty = true;
    }

    /*====================
     RELAZIONI
     =====================*/
    @Override
    public Admin getAdmin() {

        if (this.admin == null && this.adminID > 0) {
            this.admin = this.dataLayer.getAdmin(this.adminID);
        }

        return this.admin;
    }

    @Override
    public void setAdmin(Admin admin) {
        this.admin = admin;
        this.adminID = admin.getID();
        this.dirty = true;
    }

    @Override
    public boolean isPostedByAdmin() {
        return this.admin == null;
    }

    @Override
    public Post getPost() {

        if (this.postID != 0) {
            this.post = this.dataLayer.getPost(this.postID);
        }

        return this.post;
    }

    @Override
    public void setPost(Post post) {
        this.post = post;
        this.postID = post.getID();
        this.dirty = true;
    }
}
