package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Account;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccountDTO;
import rest.repository.AccountRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        return new AccountDTO(accountRepository.findOne(id));
    }

    @Override
    public AccountDTO insert(AccountDTO greeting) throws Exception {
        return null;
    }


    public AccountDTO create(AccountDTO accountDTO) throws Exception{
        if (accountDTO.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Account account = new Account(accountDTO);
        Account savedAccount = accountRepository.create(account);
        return new AccountDTO(savedAccount);
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) throws Exception {
        Account accountToUpdate = accountRepository.findOne(accountDTO.getId());
        if (accountToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }

        return new AccountDTO(accountToUpdate);
    }

    @Override
    public AccountDTO delete(Long id) {
        return null;
    }

    public Collection<AccommodationDTO> findFavourite(Long id){
        return null;
    }

    public AccommodationDTO addInFavourites(AccommodationDTO accommodationDTO){
        return null;
    }


    @Override
    public void deleteAll() {

    }

}
