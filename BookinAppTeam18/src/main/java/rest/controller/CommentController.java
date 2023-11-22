package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.domain.Comment;
import rest.domain.DTO.CommentDTO;
import rest.service.CommentService;

import java.util.Collection;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //ALL comments
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDTO>> getComments() {
        Collection<CommentDTO> comments = commentService.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    //comments for specific account
    @GetMapping(value = "/account/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDTO>> getAccountComments(@PathVariable("id") Long accountId) {
        Collection<CommentDTO> comments = commentService.findAccountComments(accountId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    //comments for specific accommodation
    @GetMapping(value = "/accommodation/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDTO>> getAccommodationComments(@PathVariable("id") Long accommodationId) {
        Collection<CommentDTO> comments = commentService.findAccommodationComments(accommodationId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    //comment by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> getComment(@PathVariable("id") Long id) {
        CommentDTO comment = commentService.findOne(id);

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    //creating comment
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment) throws Exception {
        CommentDTO savedComment = commentService.create(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    //update comment
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO comment, @PathVariable Long id)
            throws Exception {
        comment.setId(id);
        CommentDTO updatedComment = commentService.update(comment);

        if (updatedComment == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    //delete comment
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}