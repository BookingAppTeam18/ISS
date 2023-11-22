package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.domain.Account;
import rest.service.AccountService;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Account>> getUsers() {
        Collection<Account> users = accountService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value="/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountById(@PathVariable("userId") Long accountId) {
        Account account = accountService.findOne(accountId);
        if(account == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

//    @GetMapping(value="/favorites/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<Accommodation>> getFavoriteAccommodations(@PathVariable("userId") Long accountId){
//        Collection<Accommodation> accommodations = accommodationService.findById(accountId);
//        return new ResponseEntity<>(accommodations, HttpStatus.OK);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody Account account) throws Exception{
        Account savedAccount = accountService.create(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long id)
            throws Exception {
        account.setId(id);
        Account updatedAccount = accountService.update(account);

        if (updatedAccount == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Account> deleteComment(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
