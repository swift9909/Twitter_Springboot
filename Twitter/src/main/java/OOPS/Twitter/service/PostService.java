// PostServiceImpl.java
package OOPS.Twitter.service;

import OOPS.Twitter.CommentCreator;
import OOPS.Twitter.CommentDetails;
import OOPS.Twitter.PostDetails;
import OOPS.Twitter.dao.Post_repo;
import OOPS.Twitter.dao.User_repo;
import OOPS.Twitter.model.Post_Entity;
import OOPS.Twitter.model.User_Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private Post_repo postRepo;
    @Autowired
    private User_repo userRepo;

    public ResponseEntity<?> getUserFeed() {
        List<PostDetails> allPosts = new ArrayList<>();
        List<Post_Entity> posts = postRepo.findAllByOrderByDateDesc();

        for (Post_Entity post : posts) {
            PostDetails postDeets = new PostDetails();
            Date postDate = post.getDate();
            LocalDate localDate = postDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            postDeets.setDate(localDate);
            postDeets.setPostBody(post.getPostBody());
            postDeets.setPostID(post.getPostID());
            List<CommentDetails> allComs = new ArrayList<>();
            for (var c : post.getComments()) {
                CommentDetails com = new CommentDetails();
                com.setCommentID(c.getCommentId());
                com.setCommentBody(c.getCommentBody());
                CommentCreator cc = new CommentCreator();
                cc.setUserID(c.getUser().getUserId());
                cc.setName(c.getUser().getName());
                com.setCommentCreator(cc);
                allComs.add(com);
            }
            postDeets.setComments(allComs);
            allPosts.add(postDeets);
            System.out.println(postDeets.getPostBody());
        }
        return ResponseEntity.status(HttpStatus.OK).body(allPosts);
    }

    public ResponseEntity<String> createPost(String postBody, int userID) {
        User_Entity user = userRepo.findById(userID).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
        Post_Entity post = new Post_Entity(postBody, user);
        post.setDate(new Date());
        post.setComments(new ArrayList<>());
        postRepo.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }

    public ResponseEntity<?> getPostById(int postId) {
        Optional<Post_Entity> optionalPost = postRepo.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
        Post_Entity post = optionalPost.get();
        PostDetails postDeets = new PostDetails();

        Date postDate = post.getDate();
        LocalDate localDate = postDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        postDeets.setDate(localDate);
        postDeets.setPostBody(post.getPostBody());
        postDeets.setPostID(post.getPostID());

        List<CommentDetails> allComs = new ArrayList<>();
        for (var c : post.getComments()) {
            CommentDetails com = new CommentDetails();
            com.setCommentID(c.getCommentId());
            com.setCommentBody(c.getCommentBody());
            CommentCreator cc = new CommentCreator();
            cc.setUserID(c.getUser().getUserId());
            cc.setName(c.getUser().getName());

            com.setCommentCreator(cc);

            allComs.add(com);
        }
        postDeets.setComments(allComs);

        return ResponseEntity.ok().body(postDeets);
    }

    public ResponseEntity<String> editPost(int postID, String postBody) {
        Optional<Post_Entity> optionalPost = postRepo.findById(postID);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
        Post_Entity post = optionalPost.get();
        post.setPostBody(postBody);
        postRepo.save(post);
        return ResponseEntity.ok("Post edited successfully");
    }

    public ResponseEntity<String> deletePost(int postId) {
        Optional<Post_Entity> optionalPost = postRepo.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
        postRepo.deleteById(postId);
        return ResponseEntity.ok("Post deleted");
    }
}

