package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.domain.Comment;
import rest.service.CommentService;

import java.util.Collection;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Comment>> getComments() {
        Collection<Comment> comments = commentService.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @GetMapping(value = "/account/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Comment>> getAccountComments(@PathVariable("id") Long accountId) {
        Collection<Comment> comments = commentService.findAccountComments(accountId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @GetMapping(value = "/accommodation/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Comment>> getAccommodationComments(@PathVariable("id") Long accommodationId) {
        Collection<Comment> comments = commentService.findAccommodationComments(accommodationId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id) {
        Comment comment = commentService.findOne(id);

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws Exception {
        Comment savedComment = commentService.create(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable Long id)
            throws Exception {
        comment.setId(id);
        Comment updatedComment = commentService.update(comment);

        if (updatedComment == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}