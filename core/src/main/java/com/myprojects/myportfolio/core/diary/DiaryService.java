package com.myprojects.myportfolio.core.diary;

import com.myprojects.myportfolio.core.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private UserService userService;

    public Slice<Diary> findAll(Specification specification, Pageable pageable){

        Slice<Diary> diaries = this.diaryRepository.findAll(specification, pageable);

        return diaries;
    }

    public Diary findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<Diary> diary = this.diaryRepository.findById(id);
        return diary.orElseThrow(() -> new NoSuchElementException("Impossible to found any diary with id: " + id));
    }

    public Diary save(Diary diary){
        Validate.notNull(diary, "Mandatory parameter is missing: diary.");

        if(diary.getId()!=null) {
            Optional<Diary> actual = this.diaryRepository.findById(diary.getId());
            Validate.isTrue(!actual.isPresent(), "It already exists a diary with id: " + diary.getId());
        }

        if(diary.getUser()==null || diary.getUser().getId()==null){
            diary.setUser(userService.getCurrentLoggedInUser());
        }

        Diary createdDiary = this.diaryRepository.save(diary);
        return createdDiary;
    }

    public Diary update(Diary diaryToUpdate){
        Validate.notNull(diaryToUpdate, "Mandatory parameter is missing: diary.");
        Validate.notNull(diaryToUpdate.getId(), "Mandatory parameter is missinge: id diary.");

        return this.diaryRepository.save(diaryToUpdate);
    }

    public void delete(Diary diaryToDelete){
        Validate.notNull(diaryToDelete, "Mandatory parameter is missing: diary.");

        this.diaryRepository.delete(diaryToDelete);
    }
    
}
