package com.myprojects.myportfolio.core.repositories;

import com.myprojects.myportfolio.core.dao.FeedbackDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "FeedbackRepository")
public interface FeedbackRepository extends BaseRepository<FeedbackDao, Integer> {
    Optional<FeedbackDao> findByIp(String ip);
}
