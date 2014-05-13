package it.LeMarane.Sito.Data.Impl;

import it.LeMarane.Sito.Data.Model.Admin;
import it.LeMarane.Sito.Data.Model.Comment;
import it.LeMarane.Sito.Data.Model.Image;
import it.LeMarane.Sito.Data.Model.Post;
import it.LeMarane.Sito.Data.Model.SitoDataLayer;
import it.univaq.f4i.iw.framework.data.DataLayerException;
import it.univaq.f4i.iw.framework.data.DataLayerMysqlImpl;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author alex
 */
public class SitoDataLayerMysqlImpl extends DataLayerMysqlImpl implements SitoDataLayer {

    // PreparedStatement per le SELECT (corrispondono alle get di SitoDataLayer.java)
    private PreparedStatement sAdminByID, sAdminByUsernameAndPassword, sAdmins;
    private PreparedStatement sPostbyID, sPostbyTitle, sPosts;
    private PreparedStatement sCommentbyID, sComments;
    private PreparedStatement sImagebyID, sImages;
    private PreparedStatement sPost_ImageByPostID, sPost_ImageByImageID;

    // PreparedStatemente per le INSERT (corrispondono alle store di SitoDataLayer.java)
    private PreparedStatement iPost, iComment, iImage;

    // PreparedStatement per le UPDATE (corrispondono alle edit di SitoDataLayer.java)
    private PreparedStatement ePost, eComment;

    // PreparedStatement per le DELETE (corrispondono alle delete di SitoDataLayer.java)
    private PreparedStatement dPost, dComment, dImage;

    // Per DB e connessione
    public SitoDataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }

    public void init() throws DataLayerException {
        try {
            // Per aprire la connessione
            super.init();
            // Precompiliamo tutte le query utilizzate nella classe
            this.sAdminByID = connection.prepareStatement("SELECT * FROM admin WHERE ID=?");
            this.sAdminByUsernameAndPassword = connection.prepareStatement("SELECT ID FROM admin WHERE username=? AND password=?");
            this.sAdmins = connection.prepareStatement("SELECT ID FROM admin");
            this.sPostbyID = connection.prepareStatement("SELECT * FROM post WHERE ID=?");
            this.sPostbyTitle = connection.prepareStatement("SELECT ID FROM post WHERE title=?");
            this.sPosts = connection.prepareStatement("SELECT ID FROM post");
            this.sCommentbyID = connection.prepareStatement("SELECT * FROM comment WHERE ID=?");
            this.sComments = connection.prepareStatement("SELECT ID FROM comment");
            this.sImagebyID = connection.prepareStatement("SELECT * FROM image WHERE ID=?");
            this.sImages = connection.prepareStatement("SELECT ID FROM image");
            this.sPost_ImageByPostID = connection.prepareStatement("SELECT imageID FROM post_image WHERE postID=?");
            this.sPost_ImageByImageID = connection.prepareStatement("SELECT postID FROM post_image WHERE imageID=?");

            //notare l'ultimo paametro extra di questa chiamata a
            //prepareStatement: lo usiamo per assicurarci che il JDBC
            //restituisca la chiave generata automaticamente per il
            //record inserito
            this.iPost = connection.prepareStatement("INSERT INTO post (title, text, date, adminID) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            this.iComment = connection.prepareStatement("INSERT INTO comment (author, text, date, adminID, postID), VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            this.iImage = connection.prepareStatement("INSERT INTO image (URL, description, name, banner)", Statement.RETURN_GENERATED_KEYS);

            this.ePost = connection.prepareStatement("UPDATE post SET title=?, text=?, date=?, adminID=? WHERE ID=?");
            this.eComment = connection.prepareStatement("UPDATE comment SET author=?, text=?, date=?, adminID=?, postID=? WHERE ID=?");

        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Post createPost() {
        return new PostMysqlImpl(this);
    }

    @Override
    public Comment createComment() {
        return new CommentMysqlImpl(this);
    }

    @Override
    public Image createImage() {
        return new ImageMysqlImpl(this);
    }

    @Override
    public void deletePost(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteComment(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteImage(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editPost(Post edited_post) {

        try {
            // se non è stato modificato nulla allora non fare nulla
            if (!edited_post.isDirty()) {
                return;
            }
            // altrimenti fai l'update sul DB
            // this.ePost = connection.prepareStatement("UPDATE post SET title=?, text=?, date=?, adminID=? WHERE ID=?");
            ePost.setString(1, edited_post.getTitle());
            ePost.setString(2, edited_post.getText());
            ePost.setDate(3, new java.sql.Date(edited_post.getDate().getTime()));
            ePost.setInt(4, edited_post.getAuthor().getID());
            ePost.setInt(5, edited_post.getID());
            ePost.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editComment(Comment edited_comment) {
        try {
            // se non è stato modificato, non fare nulla
            if (!edited_comment.isDirty()) {
                return;
            }
            // Altrimenti fail l'update sul DB
            // this.eComment = connection.prepareStatement("UPDATE comment SET author=?, text=?, date=?, adminID=?, postID=? WHERE ID=?");
            eComment.setString(2, edited_comment.getText());
            eComment.setDate(3, new java.sql.Date(edited_comment.getDate().getTime()));
            eComment.setInt(5, edited_comment.getPost().getID());
            if (edited_comment.isPostedByAdmin()) {
                eComment.setString(1, edited_comment.getAdmin().getUsername());
                eComment.setInt(4, edited_comment.getAdmin().getID());
            } else {
                eComment.setString(1, edited_comment.getAuthor());
                eComment.setNull(4, java.sql.Types.INTEGER);
            }
            eComment.setInt(6, edited_comment.getID());
            eComment.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Admin getAdmin(int ID) {
        ResultSet rs = null;
        Admin result = null;
        try {
            // this.sAdminByID = connection.prepareStatement("SELECT * FROM admin WHERE ID=?");
            sAdminByID.setInt(1, ID);
            rs = sAdminByID.executeQuery();
            if (rs.next()) { // se la select ha restituito almeno una riga
                result = new AdminMysqlImpl(this, rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public Admin getAdmin(String username, String password) {
        ResultSet rs = null;
        Admin result = null;
        try {
            // this.sAdminByUsernameAndPassword = connection.prepareStatement("SELECT ID FROM admin WHERE username=? AND password=?");
            sAdminByUsernameAndPassword.setString(1, username);
            sAdminByUsernameAndPassword.setString(2, password);
            rs = sAdminByID.executeQuery();
            if (rs.next()) { // se la select ha restituito almeno una riga
                result = new AdminMysqlImpl(this, rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public List<Admin> getAdmins() {
        List<Admin> result = new ArrayList();
        ResultSet rs = null;
        try {
            // this.sAdmins = connection.prepareStatement("SELECT ID FROM admin");
            rs = sAdmins.executeQuery();
            while (rs.next()) { // finchè non consumo tutti i risultati
                result.add(getAdmin(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public List<Post> getPosts(String titolo) {
        List<Post> result = new ArrayList();
        ResultSet rs = null;
        try {
            // this.sPostbyTitle = connection.prepareStatement("SELECT ID FROM post WHERE title=?");
            sPostbyTitle.setString(1, titolo);
            rs = sPostbyTitle.executeQuery();
            while (rs.next()) {
                result.add(getPost(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public Post getPost(int ID) {
        Post result = null;
        ResultSet rs = null;
        try {
            // this.sPostbyID = connection.prepareStatement("SELECT * FROM post WHERE ID=?");
            sPostbyID.setInt(1, ID);
            rs = sPostbyID.executeQuery();
            if (rs.next()) {
                result = new PostMysqlImpl(this, rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public List<Post> getPosts() {
        List<Post> result = new ArrayList();
        ResultSet rs = null;
        try {
            // this.sPosts = connection.prepareStatement("SELECT ID FROM post");
            rs = sPosts.executeQuery();
            while (rs.next()) {
                result.add(getPost(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public Comment getComment(int ID) {
        Comment result = null;
        ResultSet rs = null;
        try {
            // this.sCommentbyID = connection.prepareStatement("SELECT * FROM comment WHERE ID=?");
            sCommentbyID.setInt(1, ID);
            rs = sCommentbyID.executeQuery();
            if (rs.next()) {
                result = new CommentMysqlImpl(this, rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public List<Comment> getComments() {
        List<Comment> result = new ArrayList();
        ResultSet rs = null;
        try {
            // this.sComments = connection.prepareStatement("SELECT ID FROM comment");
            rs = sComments.executeQuery();
            while (rs.next()) {
                result.add(getComment(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public Image getImage(int ID) {
        Image result = null;
        ResultSet rs = null;
        try {
            // this.sImagebyID = connection.prepareStatement("SELECT * FROM image WHERE ID=?");
            sImagebyID.setInt(1, ID);
            rs = sImagebyID.executeQuery();
            if (rs.next()) {
                result = new ImageMysqlImpl(this, rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public List<Image> getImages() {
        List<Image> result = new ArrayList();
        ResultSet rs = null;
        try {
            // this.sImages = connection.prepareStatement("SELECT ID FROM image");
            rs = sImages.executeQuery();
            while (rs.next()) {
                result.add(getImage(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public void storePost(Post post) {
        ResultSet rs = null;
        int ID = post.getID();
        if (ID > 0) { // post esiste già nel DB => faccio l'update
            editPost(post);
        } else { // post non esiste nel DB => faccio l'insert
            try {
                // this.iPost = connection.prepareStatement("INSERT INTO post (title, text, date, adminID) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                iPost.setString(1, post.getTitle());
                iPost.setString(2, post.getText());
                // System.currentTimeMillis() / 1000L <= per ottenere il TIMESTAMP corrente
                iPost.setDate(3, new java.sql.Date(System.currentTimeMillis() / 1000L));
                iPost.setInt(4, post.getAuthor().getID());
                if (iPost.executeUpdate() == 1) { // allora ha inserito 1 riga
                    rs = iPost.getGeneratedKeys(); // per ottenere le chiavi generate automaticamente 
                    // che hanno l'auto increment
                    // restituisce un ResultSet contenente
                    // tutte le chiavi generate
                    if (rs.next()) { // se ha restituito la chiave
                        ID = rs.getInt(1);
                    }
                }
                if(ID > 0){ // ho eseguito il blocco di codice ID = rs.getInt(1);
                    post.copyFrom(getPost(ID)); // aggiorna il post a quello appena salvato
                }
                post.setDirty(false); // considera il post pulito
            } catch (SQLException ex) {
                Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if(rs != null) try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void storeComment(Comment comment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void storeImage(Image image) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Post> getPosts(int imageID) {
        ResultSet rs = null;
        List<Post> result = new ArrayList();
        try {
            // this.sPost_ImageByImageID = connection.prepareStatement("SELECT postID FROM post_image WHERE imageID=?");
            this.sPost_ImageByImageID.setInt(1, imageID);
            rs = this.sPost_ImageByImageID.executeQuery();
            while (rs.next()) {
                result.add(getPost(rs.getInt("postID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public List<Post> getPosts(Image image) {
        return getPosts(image.getID());
    }

    @Override
    public List<Image> getImages(int postID) {
        ResultSet rs = null;
        List<Image> result = new ArrayList();
        try {
            // this.sPost_ImageByPostID = connection.prepareStatement("SELECT imageID FROM post_image WHERE postID=?");
            this.sPost_ImageByPostID.setInt(1, postID);
            rs = this.sPost_ImageByPostID.executeQuery();
            while (rs.next()) {
                result.add(getImage(rs.getInt("imageID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SitoDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    @Override
    public List<Image> getImages(Post post) {
        return getImages(post.getID());
    }

}
