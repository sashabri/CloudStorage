package com.example.cloudstorage.repository;

import com.example.cloudstorage.model.UserFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileInfoRepository extends JpaRepository<UserFileInfo, Integer> {

}