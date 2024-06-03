// TwitterController.java
package OOPS.Twitter.controller;

import OOPS.Twitter.*;
import OOPS.Twitter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.jar.JarOutputStream;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        ResponseEntity<String> response=userService.login(loginRequest.getEmail(),loginRequest.getPassword());
        if(response.getStatusCode()==HttpStatus.OK){
            return ResponseEntity.ok(response.getBody());
        }
        else{
            Error_Msg e=new Error_Msg();
            e.setError(response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        ResponseEntity<?> response=userService.signup(signUpRequest.getEmail(),signUpRequest.getName(),signUpRequest.getPassword());
        if(response.getStatusCode().equals(HttpStatus.CREATED))
        {
            return ResponseEntity.ok(response.getBody());
        }
        else{

            Error_Msg e=new Error_Msg();
            e.setError((String) response.getBody());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam("userID") int userId) {
        ResponseEntity<?> userDetails = userService.getUserDetail(userId);
        if(userDetails.getStatusCode()==HttpStatus.OK)
            return ResponseEntity.ok(userDetails.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError((String) userDetails.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getUserFeed().getBody());
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        ResponseEntity<String> response=postService.createPost(postRequest.getPostBody(),postRequest.getUserID());
        if(response.getStatusCode()==HttpStatus.CREATED)
            return ResponseEntity.ok(response.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError(response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPostById(@RequestParam("postID") int postId) {
        ResponseEntity<?> response=postService.getPostById(postId);
        if(response.getStatusCode()==HttpStatus.OK)
            return ResponseEntity.ok(response.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError((String) response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PatchMapping("/post")
    public ResponseEntity<?> editPost(@RequestBody PostEditRequest postEditRequest) {
        ResponseEntity<String> response=postService.editPost(postEditRequest.getPostID(),postEditRequest.getPostBody());
        if(response.getStatusCode()==HttpStatus.OK)
            return ResponseEntity.ok(response.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError(response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }

    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestParam("postID") int postId) {
        ResponseEntity<String> response=postService.deletePost(postId);
        if(response.getStatusCode()==HttpStatus.OK)
            return ResponseEntity.ok(response.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError(response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }

    }

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
        ResponseEntity<String> response=commentService.createComment(commentRequest.getCommentBody(), commentRequest.getPostID(), commentRequest.getUserID());
        if(response.getStatusCode()==HttpStatus.CREATED)
            return ResponseEntity.ok(response.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError(response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getCommentById(@RequestParam("commentID") int CommentId) {
        ResponseEntity<?> response=commentService.getCommentById(CommentId);
        if(response.getStatusCode()==HttpStatus.OK)
            return ResponseEntity.ok(response.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError((String) response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PatchMapping("/comment")
    public ResponseEntity<?> editComment(@RequestBody CommentEditRequest commentEditRequest) {
        ResponseEntity<?> response=commentService.editComment(commentEditRequest.getCommentID(),commentEditRequest.getCommentBody());
        if(response.getStatusCode()==HttpStatus.OK)
            return ResponseEntity.ok(response.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError((String) response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam("commentID") int commentId) {
        ResponseEntity<?> response=commentService.deleteComment(commentId);
        if(response.getStatusCode()==HttpStatus.OK)
            return ResponseEntity.ok(response.getBody());
        else{
            Error_Msg e=new Error_Msg();
            e.setError((String) response.getBody());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDetails>> getAllUsers() {
        List<UserDetails> users = userService.getAllUsers().getBody();
        return ResponseEntity.ok(users);
    }

}
