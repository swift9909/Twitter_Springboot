package OOPS.Twitter.dao;

import org.springframework.stereotype.Repository;
import OOPS.Twitter.model.Post_Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Post_repo extends JpaRepository<Post_Entity, Integer>{
    List<Post_Entity> findAllByOrderByDateDesc();
}
