package OOPS.Twitter.model;


import jakarta.persistence.*;

@Entity
public class Comment_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private String commentBody;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_Entity user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post_Entity post;

    public Comment_Entity() {
    }

    public Comment_Entity(String commentBody, User_Entity user, Post_Entity post) {
        this.commentBody = commentBody;
        this.user = user;
        this.post = post;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public User_Entity getUser() {
        return user;
    }

    public void setUser(User_Entity user) {
        this.user = user;
    }

    public Post_Entity getPost() {
        return post;
    }

    public void setPost(Post_Entity post) {
        this.post = post;
    }
}

