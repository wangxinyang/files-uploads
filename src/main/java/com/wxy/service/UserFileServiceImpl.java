package com.wxy.service;

import com.wxy.dao.UserFileDao;
import com.wxy.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserFileServiceImpl implements UserFileServie {

    @Autowired
    private UserFileDao userFileDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserFile> findByUserId(Integer id) {
        return userFileDao.findByUserId(id);
    }

    @Override
    public void save(UserFile userFile) {
        String isImg = userFile.getType().startsWith("image") ? "はい" : "いいえ";
        userFile.setIsImg(isImg);
        userFile.setDownCount(0);
        userFile.setUpdateTime(new Date());
        userFileDao.save(userFile);
    }

    @Override
    public UserFile findById(Integer id) {
        return userFileDao.findById(id);
    }

    @Override
    public void updateDownloadCount(UserFile userFile) {
        userFileDao.updateDownloadCount(userFile);
    }

    @Override
    public void deleteById(UserFile userFile) {
        userFileDao.deleteById(userFile);
    }
}
