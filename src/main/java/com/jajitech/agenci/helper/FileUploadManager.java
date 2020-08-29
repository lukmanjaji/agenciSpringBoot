/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.helper;

import com.jajitech.agenci.exception.MyFileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Component
@Service
@RestController
public class FileUploadManager {
    
    String path = "";
    
    public FileUploadManager()
    {
        path = System.getProperty("user.dir")+"/agenciFiles/";
    }
    
    public boolean doUpload(String type, MultipartFile toUpload)
    {
        boolean success = false;
        File f = new File(path +type + "/");
        if(!f.exists())
        {
            f.mkdirs();
        }
        try
        {
            File file = new File(path + "/"+type + "/" +toUpload.getOriginalFilename());
            toUpload.transferTo(file);
            success = true;
        }
        catch(IOException er)
        {
            er.printStackTrace();
        }
        return success;
    }
    
    public Resource loadFileAsResource(String fileName, String type) {
        try {
            String uploadingDir = path+type;
            Path fileStorageLocation = Paths.get(uploadingDir);
            Path filePath1 = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath1.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
    
    @GetMapping("/getFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        String a[] = fileName.split("_");
        Resource resource = loadFileAsResource(a[0], a[1]);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    
    
    
    
}
