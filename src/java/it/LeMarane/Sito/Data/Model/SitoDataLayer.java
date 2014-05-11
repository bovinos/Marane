package it.LeMarane.Sito.Data.Model;

import java.util.List;

/**
 *
 * @author alex
 */
public interface SitoDataLayer {
    
    Post createPost();
    
    Comment createComment();
    
    Image createImage();
    
    void deletePost(int ID);
    
    void deleteComment(int ID);
    
    void deleteImage(int ID);
    
    void editPost(Post edited_post);
    
    void editComment(Comment edited_comment);
    
    Admin getAdmin(int ID);
    
    Admin getAdmin(String username, String password);
    
    List<Admin> getAdmins();
    
    List<Post> getPosts(String titolo);
    
    Post getPost(int ID);
    
    List<Post> getPosts();
    
    Comment getComment(int ID);
    
    List<Comment> getComments();
    
    Image getImage(int ID);
    
    List<Image> getImages();
    
    void storePost(Post post);
    
    void storeComment(Comment comment);
    
    void storeImage(Image image);
    
}
