package com.example.demo.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.FileResponse;
import com.example.demo.User;
import com.example.demo.repository.FileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileUploadController {
	private StorageService storageService;
	private FileRepository fileRepo;
    public FileUploadController(StorageService storageService,FileRepository fileRepo) {
        this.storageService = storageService;
        this.fileRepo = fileRepo;
    }

    @GetMapping()
    public String listAllFiles(@AuthenticationPrincipal User user,Model model) {
    	
    	return "uploadFile";
    	
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
//                        .path("/download/")
//                        .path(path.getFileName().toString())
//                        .toUriString())
//                .collect(Collectors.toList()));

        
    }

    @GetMapping("/download/{idFile}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable Long idFile,@AuthenticationPrincipal User user) {
    	if(user != null) {
    		log.info(idFile.toString());
        	FileResponse file = this.fileRepo.findByUser_idAndId(user.getId(), idFile);
        	if(file != null) {
	            Resource resource = storageService.loadAsResource(file.getOnDisk());
	            
	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_DISPOSITION,
	                            "attachment; filename=\"" + resource.getFilename() + "\"")	
	                    .contentType(MediaType.IMAGE_JPEG)
	                    .body(resource);
        	}
    	}
    	return null;
    }

    @PostMapping("/upload-file")
    @ResponseBody
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file,@AuthenticationPrincipal User user) {
        return uploadFileUtitlity(file, user);
    }
    private FileResponse uploadFileUtitlity(MultipartFile file, User user) {
    	
    	Date date = new Date();
    	String extention = StringUtils.getFilenameExtension(file.getOriginalFilename());
    	String filenameOnDisk = user.getId().toString()+date.getTime() +"."+extention; 
    	String filename = StringUtils.cleanPath(file.getOriginalFilename());
    	
    	storageService.store(file,filenameOnDisk);
        
    	log.info(filename);
        return this.fileRepo.save(new FileResponse(filenameOnDisk,file, user));
    }
    @PostMapping("/upload-multiple-files")
    @ResponseBody
    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@AuthenticationPrincipal User user) {
        return Arrays.stream(files)
                .map(file -> uploadFileUtitlity(file,user))
                .collect(Collectors.toList());
    }
	
}
