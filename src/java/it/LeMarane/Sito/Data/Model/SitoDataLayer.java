package it.LeMarane.Sito.Data.Model;

import java.util.List;

/**
 *
 * @author alex
 */
public interface SitoDataLayer {

    //  CREATE
    Post createPost();

    Comment createComment();

    Image createImage();

    // DELETE
    void deletePost(int ID);

    void deleteComment(int ID);

    void deleteImage(int ID);

    // EDIT
    void editPost(Post edited_post);

    void editComment(Comment edited_comment);

    // GET
    Admin getAdmin(int ID);

    Admin getAdmin(String username, String password);

    List<Admin> getAdmins();

    List<Post> getPosts(String titolo);

    Post getPost(int ID);

    List<Post> getPosts();

    /**
     * Dato l'ID di una <code>Image</code> ritorna la lista dei
     * <code>Post</code> associati a quella Image
     *
     * @param imageID
     * @return
     */
    List<Post> getPosts(int imageID);

    /**
     * Data una <code>Image</code> restituisce la lista di <code>Post</code>
     * associati a quella Image
     *
     * @param image
     * @return
     */
    List<Post> getPosts(Image image);

    Comment getComment(int ID);

    List<Comment> getComments();

    Image getImage(int ID);

    List<Image> getImages();

    /**
     * Dato l'ID di un <code>Post</code> ritorna la lista delle
     * <code>Image</code> associate a quel post
     *
     * @param postID
     * @return
     */
    List<Image> getImages(int postID);

    /**
     * Dato un <code>Post</code> ritorna la lista delle <code>Image</code>
     * associate a quel post
     *
     * @param post
     * @return
     */
    List<Image> getImages(Post post);

    // STORE
    void storePost(Post post);

    void storeComment(Comment comment);

    void storeImage(Image image);

}
