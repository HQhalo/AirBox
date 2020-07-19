package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;


import com.example.demo.FileResponse;

public interface FileRepository extends CrudRepository<FileResponse,Long>{
	FileResponse findByUser_idAndId(Long id,Long idFile);
	Iterable<FileResponse> findAllByUser_id(Long id);
}
