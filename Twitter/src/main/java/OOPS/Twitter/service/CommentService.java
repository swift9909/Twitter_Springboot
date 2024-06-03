// CommentServiceImpl.java
package OOPS.Twitter.service;

import OOPS.Twitter.CommentCreator;
import OOPS.Twitter.CommentDetails;
import OOPS.Twitter.dao.Comment_repo;
import OOPS.Twitter.dao.Post_repo;
import OOPS.Twitter.dao.User_repo;
import OOPS.Twitter.model.Comment_Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import OOPS.Twitter.model.User_Entity;
import OOPS.Twitter.model.Post_Entity;


import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private Comment_repo commentRepo;
    @Autowired
    private Post_repo postRepo;
    @Autowired
    private User_repo userRepo;

    public ResponseEntity<String> createComment(String commentBody, int postId, int userId) {
        Optional<Post_Entity> optionalPost = postRepo.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }

        Optional<User_Entity> optionalUser = userRepo.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

        Post_Entity post = optionalPost.get();
        User_Entity user = optionalUser.get();

        Comment_Entity comment = new Comment_Entity(commentBody, user, post);
        post.getComments().add(comment);
        commentRepo.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
    }

    public ResponseEntity<?> getCommentById(int CommentId) {
        Optional<Comment_Entity> optionalComment = commentRepo.findById(CommentId);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
        Comment_Entity comment = optionalComment.get();
        CommentDetails com=new CommentDetails();
        com.setCommentID(comment.getCommentId());
        com.setCommentBody(comment.getCommentBody());

        CommentCreator cc=new CommentCreator();
        cc.setName(comment.getUser().getName());
        cc.setUserID(comment.getUser().getUserId());
        com.setCommentCreator(cc);

        return ResponseEntity.ok().body(com);
    }

    public ResponseEntity<String> editComment(int commentId, String commentBody) {
        Optional<Comment_Entity> optionalComment = commentRepo.findById(commentId);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
        Comment_Entity comment = optionalComment.get();
        comment.setCommentBody(commentBody);
        commentRepo.save(comment);
        return ResponseEntity.ok("Comment edited successfully");
    }

    public ResponseEntity<String> deleteComment(int commentId) {
        Optional<Comment_Entity> optionalComment = commentRepo.findById(commentId);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
        commentRepo.deleteById(commentId);
        return ResponseEntity.ok("Comment deleted");
    }

}
