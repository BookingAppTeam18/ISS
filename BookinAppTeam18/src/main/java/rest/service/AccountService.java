package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Account;
import rest.repository.AccountRepository;

import java.util.Collection;

@Service
public class AccountService implements IService<Account> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Collection<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findOne(Long id) {
        return accountRepository.findOne(id);
    }

    @Override
    public Account create(Account account) throws Exception {
        if (account.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        return accountRepository.create(account);
    }

    @Override
    public Account update(Account account) throws Exception {
        Account accountToUpdate = findOne(account.getId());
        if (accountToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }

        return accountToUpdate;
    }

    @Override
    public void delete(Long id) {
        accountRepository.delete(id);
    }

}
