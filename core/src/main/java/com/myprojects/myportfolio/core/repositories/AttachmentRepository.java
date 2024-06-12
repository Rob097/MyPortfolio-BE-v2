package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.Attachment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "AttachmentRepository")
public interface AttachmentRepository extends BaseRepository<Attachment, Integer> {

    Optional<Attachment> findByUrl(String url);

    @Query("SELECT a FROM Attachment a WHERE a.user.id = ?1")
    List<Attachment> findAllByUserId(Integer userId);

}
