package com.wxy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.print.DocFlavor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserFile {

    private Integer id;
    private String oldFileName;
    private String newFileName;
    private String ext;
    private String path;
    private String size;
    private String type;
    private String isImg;
    private Integer downCount;
    private Date updateTime;
    private Integer userId;

    public void save(UserFile userFile) {
    }
}
