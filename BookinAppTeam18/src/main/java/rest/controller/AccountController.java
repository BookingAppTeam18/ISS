package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.domain.Account;
import rest.domain.DTO.AccountDTO;
import rest.service.AccountService;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountDTO>> getUsers() {
        Collection<AccountDTO> users = accountService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value="/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("userId") Long accountId) {
        AccountDTO accountDTO = accountService.findOne(accountId);
        if(accountDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

//    @GetMapping(value="/favorites/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<Accommodation>> getFavoriteAccommodations(@PathVariable("userId") Long accountId){
//        Collection<Accommodation> accommodations = accommodationService.findById(accountId);
//        return new ResponseEntity<>(accommodations, HttpStatus.OK);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws Exception{
        return new ResponseEntity<>(accountService.create(accountDTO), HttpStatus.CREATED);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable Long id)
            throws Exception {
        accountDTO.setId(id);
        AccountDTO updatedAccount = accountService.update(accountDTO);

        if (updatedAccount == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> deleteComment(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
