package com.myprojects.myportfolio.core.services;

import com.myprojects.myportfolio.core.dao.User;
import com.myprojects.myportfolio.core.dto.FileTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserServiceI extends BaseServiceI<User> {

    List<String> findAllSlugs();

    List<String> uploadFiles(List<MultipartFile> files, Integer userId, FileTypeEnum fileType, String language) ;

}
