package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccountDTO;
import rest.service.AccountService;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //GET all users
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountDTO>> getUsers() {
        Collection<AccountDTO> users = accountService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //GET user by id
    @GetMapping(value="/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("userId") Long accountId) {
        AccountDTO accountDTO = accountService.findOne(accountId);
        if(accountDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }
    //Get favourite accommodation for specific user
    @GetMapping(value="/favorites/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getFavoriteAccommodations(@PathVariable("userId") Long accountId){
        Collection<AccommodationDTO> accommodationDTOS = accountService.findFavourite(accountId);
        return new ResponseEntity<>(accommodationDTOS, HttpStatus.OK);
    }

    //Create account
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws Exception{
        return new ResponseEntity<>(accountService.insert(accountDTO), HttpStatus.CREATED);
    }

    //Add accommodation in favourites (Post?)
    @PostMapping(value="addFavorites",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> addInFavourites(@RequestBody AccommodationDTO accommodationDTO) throws Exception{

        return new ResponseEntity<>(accountService.addInFavourites(accommodationDTO), HttpStatus.CREATED);
    }

    //Update account
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

    //Delete account
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //Delete favourite accommodation (remove from list)
    @DeleteMapping(value = "/{userId}/favourite/{favouriteId}")
    public ResponseEntity<AccommodationDTO> removeFromFavourites(@PathVariable("userId") Long userId, @PathVariable("favouriteId") Long favouriteId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
