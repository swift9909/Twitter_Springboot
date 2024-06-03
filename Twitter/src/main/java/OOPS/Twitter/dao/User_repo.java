package OOPS.Twitter.dao;

import OOPS.Twitter.model.User_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface User_repo extends JpaRepository<User_Entity, Integer>{
    Optional<User_Entity> findByEmail(String email);
    Optional<User_Entity> findByName(String name);
}
