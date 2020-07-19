package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class FileResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
    private String onDisk;
    private String type;
    private long size;
    
    @OneToOne
    private User user;
   
	public FileResponse(String onDisk,MultipartFile file, User user) {
		
		this.name = StringUtils.cleanPath(file.getOriginalFilename());
		this.onDisk = onDisk;
		this.type = file.getContentType();
		this.size = file.getSize();
		this.user = user;
	}
    
  
}
