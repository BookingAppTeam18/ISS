package rest.service;

import org.springframework.stereotype.Service;
import rest.domain.DTO.LoginDTO;

import java.util.Collection;

@Service
public class LogInService implements IService<LoginDTO> {
    @Override
    public Collection<LoginDTO> findAll() {
        return null;
    }

    @Override
    public LoginDTO findOne(Long id) {
        return null;
    }

    @Override
    public LoginDTO create(LoginDTO greeting) throws Exception {
        return null;
    }

    @Override
    public LoginDTO update(LoginDTO greeting) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
