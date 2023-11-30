package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rest.domain.DTO.CommentDTO;
import rest.domain.DTO.LoginDTO;
import rest.service.LogInService;

import java.util.Collection;

@RestController
@RequestMapping("/api/login")
public class LogInController {
    @Autowired
    private LogInService logInService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity logOut(Long id) {

        if(logInService.findOne(id) != null){
            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
    }

    @PostMapping(
            value = "/logIn",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity loginUser(@RequestBody LoginDTO login, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(login, HttpStatus.CREATED);
    }

}
