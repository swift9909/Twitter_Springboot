package OOPS.Twitter.dao;

import OOPS.Twitter.model.Comment_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Comment_repo extends JpaRepository<Comment_Entity, Integer> {
}
