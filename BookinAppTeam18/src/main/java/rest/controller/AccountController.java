package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccountDTO;
import rest.domain.DTO.AccountDetailsDTO;
import rest.service.AccountService;

import java.security.Principal;
import java.util.Collection;


@RestController
@RequestMapping("/api/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //GET all users
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountDTO>> getAccounts() {
        Collection<AccountDTO> users = accountService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //GET user by id
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value="/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("userId") Long accountId) {
        AccountDTO accountDTO = accountService.findOne(accountId);
        if(accountDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value="/userDetails/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDetailsDTO> getAccountDetailsById(@PathVariable("userId") Long accountId) {
        AccountDetailsDTO accountDetailsDTO = accountService.findAccountDetails(accountId);
        return new ResponseEntity<AccountDetailsDTO>(accountDetailsDTO, HttpStatus.OK);
    }

    //GET logged in user
    @PreAuthorize("hasAnyAuthority('OWNER','GUEST', 'ADMIN')")
    @GetMapping("/whoami")
    public ResponseEntity<AccountDTO> Account(Principal account) {
        return new ResponseEntity<>(accountService.findByEmail(account.getName()), HttpStatus.OK);
    }


    //Get favourite accommodation for specific user
    @PreAuthorize("hasAnyAuthority('GUEST')")
    @GetMapping(value="/favorites/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getFavoriteAccommodations(@PathVariable("userId") Long accountId){
        Collection<AccommodationDTO> accommodationDTOS = accountService.findFavourite(accountId);
        return new ResponseEntity<>(accommodationDTOS, HttpStatus.OK);
    }

    //Create account
    @PreAuthorize("hasAnyAuthority('OWNER','GUEST')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws Exception{
        return new ResponseEntity<>(accountService.insert(accountDTO), HttpStatus.CREATED);
    }

    //Add accommodation in favourites (Post?)
    @PreAuthorize("hasAnyAuthority('GUEST')")
    @PostMapping(value = "/addFavorites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> addInFavourites(
            @RequestParam Long userId,
            @RequestBody AccommodationDTO accommodationDTO) {
        try {
            AccommodationDTO result = accountService.addInFavourites(userId, accommodationDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate HTTP status
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping(value = "/block/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> blockAccount(@PathVariable Long id)
            throws Exception {
        AccountDTO updatedAccount = accountService.blockAccount(id);

        if (updatedAccount == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);

    }

    //Update account
    @PreAuthorize("hasAnyAuthority('OWNER','GUEST','ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('OWNER','GUEST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable("id") Long id) {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //Delete favourite accommodation (remove from list)
    @PreAuthorize("hasAnyAuthority('GUEST')")
    @DeleteMapping(value = "/{userId}/favourite/{favouriteId}")
    public ResponseEntity<AccommodationDTO> removeFromFavourites(@PathVariable("userId") Long userId, @PathVariable("favouriteId") Long favouriteId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
