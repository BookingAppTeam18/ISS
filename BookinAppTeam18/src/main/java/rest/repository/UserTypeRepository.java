package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.domain.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByName(String name);
}
