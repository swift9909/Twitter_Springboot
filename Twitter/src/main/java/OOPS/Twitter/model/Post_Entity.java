package OOPS.Twitter.model;



import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Post_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;
    private String postBody;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_Entity user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment_Entity> comments;

    public Post_Entity() {
    }

    public Post_Entity(String postBody, User_Entity user) {
        this.postBody = postBody;
        this.user = user;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public List<Comment_Entity> getComments() {
        return comments;
    }

    public void setComments(List<Comment_Entity> comments) {
        this.comments = comments;
    }

    public User_Entity getUser() {
        return user;
    }

    public void setUser(User_Entity user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}

