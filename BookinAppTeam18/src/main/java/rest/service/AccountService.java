package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.Account;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccountDTO;
import rest.repository.AccountRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class AccountService implements IService<AccountDTO> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Collection<AccountDTO> findAll(){
        List<AccountDTO> accountDTOList = new ArrayList<>();
        Collection<Account> accounts = accountRepository.findAll();
        for(Account account : accounts){
            accountDTOList.add(new AccountDTO(account));
        }
        return accountDTOList;
    }

    @Override
    public AccountDTO findOne(Long id){
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new AccountDTO(account.get());
    }

    @Override
    public AccountDTO insert(AccountDTO accountDTO) throws Exception {
        Account account = new Account(accountDTO);
        try {
            accountRepository.save(account);
            accountRepository.flush();
            return accountDTO;
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
            StringBuilder sb = new StringBuilder(1000);
            for (ConstraintViolation<?> error : errors) {
                sb.append(error.getMessage() + "\n");
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
        }
    }



    @Override
    public AccountDTO update(AccountDTO accountDTO) throws Exception {
        Account accountToUpdate = new Account(accountDTO);
        try {
            findOne(accountDTO.getId()); // this will throw ResponseStatusException if student is not found
            accountRepository.save(accountToUpdate);
            accountRepository.flush();
            return accountDTO;
        } catch (RuntimeException ex) {
            Throwable e = ex;
            Throwable c = null;
            while ((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException) ) {
                e = (RuntimeException) c;
            }
            if ((c != null) && (c instanceof ConstraintViolationException)) {
                ConstraintViolationException c2 = (ConstraintViolationException) c;
                Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
                StringBuilder sb = new StringBuilder(1000);
                for (ConstraintViolation<?> error : errors) {
                    sb.append(error.getMessage() + "\n");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
            }
            throw ex;
        }
    }

    @Override
    public AccountDTO delete(Long id) {
        Account account = new Account(findOne(id)); // this will throw StudentNotFoundException if student is not found
        accountRepository.delete(account);
        accountRepository.flush();
        return new AccountDTO(account);
    }

    public Collection<AccommodationDTO> findFavourite(Long id){
        Collection<Accommodation> favouriteAccommodations = accountRepository.findFavouriteAccommodation(id);
        Collection<AccommodationDTO> favouriteAccommodationDTOs = null;
        for(Accommodation accommodation : favouriteAccommodations){
            favouriteAccommodationDTOs.add(new AccommodationDTO(accommodation));
        }
        return favouriteAccommodationDTOs;
    }



//    public AccommodationDTO addInFavourites(AccommodationDTO accommodationDTO){
//        return null;
//    }


    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
        accountRepository.flush();
    }

}
