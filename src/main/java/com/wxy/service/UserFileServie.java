package com.wxy.service;

import com.wxy.entity.User;
import com.wxy.entity.UserFile;

import java.util.List;

public interface UserFileServie {
    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);

    UserFile findById(Integer id);

    void updateDownloadCount(UserFile userFile);

    void deleteById(UserFile userFile);
}
