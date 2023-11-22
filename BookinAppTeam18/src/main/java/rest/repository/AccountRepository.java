package rest.repository;

import org.springframework.stereotype.Repository;
import rest.domain.Account;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AccountRepository implements IRepository<Account>{
    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, Account> accounts = new ConcurrentHashMap<Long, Account>();
    @Override
    public Collection<Account> findAll() {
        return this.accounts.values();
    }
    @Override
    public Account create(Account account) {
        Long id = account.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            account.setId(id);
        }

        this.accounts.put(id, account);
        return account;
    }

    @Override
    public Account findOne(Long id) {
        return this.accounts.get(id);
    }

    @Override
    public void delete(Long id) {
        this.accounts.remove(id);
    }

    @Override
    public Account update(Account account) {
        Long id = account.getId();

        if (id != null) {
            this.accounts.put(id, account);
        }

        return account;
    }

}
