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
@RequestMapping("")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Comment>> getComments() {
        Collection<Comment> comments = commentService.findAll();
        return new ResponseEntity<Collection<Comment>>(comments, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Comment>> getPageComments(@PathVariable("id") Long pageId) {
        Collection<Comment> comments = commentService.findPageComments(pageId);
        return new ResponseEntity<Collection<Comment>>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id) {
        Comment comment = commentService.findOne(id);

        if (comment == null) {
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws Exception {
        Comment savedComment = commentService.create(comment);
        return new ResponseEntity<Comment>(savedComment, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, @PathVariable Long id)
            throws Exception {
        Comment commentForUpdate = commentService.findOne(id);
        commentForUpdate.copyValues(comment);

        Comment updatedComment = commentService.update(commentForUpdate);

        if (updatedComment == null) {
            return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
    }

}