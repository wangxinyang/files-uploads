package com.wxy.controller;

import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.URLEncoder;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.wxy.entity.User;
import com.wxy.entity.UserFile;
import com.wxy.service.UserFileServie;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private UserFileServie userFileServie;

    @GetMapping("delete")
    public String delete(String id) throws FileNotFoundException {
        UserFile userFile = userFileServie.findById(Integer.parseInt(id));
        if (userFile != null) {
            // delete file from server
            String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "/static" + userFile.getPath();
            File file = new File(realPath, userFile.getNewFileName());
            if (file.exists()) {
                file.delete();
            }
            userFileServie.deleteById(userFile);
        }
        return "redirect:/file/showAll";
    }

    @GetMapping("download")
    public String download(String id, String openType, HttpServletResponse response) {
        openType = openType == null ? "attachmnet" : openType;
        UserFile userFile = userFileServie.findById(Integer.parseInt(id));
        try {
            String realPath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "/static" + userFile.getPath();
            FileInputStream fileInputStream = new FileInputStream(new File(realPath, userFile.getNewFileName()));
            // 直接ダウンロードではない、attachemntとしてダウンロードする
            response.setHeader("content-disposition", openType + ";fileName=" + URLEncoder.encode(userFile.getOldFileName(), "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(fileInputStream, outputStream);
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(outputStream);
            // update download count
            if ("attachmnet".equals(openType)) {
                userFile.setDownCount(userFile.getDownCount() + 1);
                userFileServie.updateDownloadCount(userFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/file/showAll";
    }

    // ファイルをアップロード処理
    @PostMapping("upload")
    public String upload(MultipartFile uploadFile, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        // file oldname
        String originalFilename = uploadFile.getOriginalFilename();
        // file ext
        String extension = "." + FilenameUtils.getExtension(originalFilename);
        try {
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + UUID.randomUUID().toString().replace("-", "")
                    + extension;
            // size
            long size = uploadFile.getSize();
            // type
            String type = uploadFile.getContentType();

            // create save dir
            String savePath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath() + "/static/files";
            String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String dateFileDir = savePath + "/" + dateFormat;
            File dateDirs = new File(dateFileDir);
            if (!dateDirs.exists()) {
                dateDirs.mkdirs();
            }

            // upload
            uploadFile.transferTo(new File(dateDirs, newFileName));

            // save db
            UserFile userFile = new UserFile();
            userFile.setOldFileName(originalFilename).setNewFileName(newFileName)
                    .setExt(extension).setSize(String.valueOf(size))
                    .setType(type).setPath("/files/" + dateFormat).setUserId(user.getId());
            userFileServie.save(userFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/file/showAll";
    }


    // ファイル画面が表示されます
    @GetMapping("showAll")
    public String findAll(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");
        List<UserFile> userFileList = userFileServie.findByUserId(user.getId());
        model.addAttribute("userfiles", userFileList);
        return "showAll";
    }
}
