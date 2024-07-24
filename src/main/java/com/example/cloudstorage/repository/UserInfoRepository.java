package com.example.cloudstorage.repository;

import com.example.cloudstorage.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    @Query("select u from UserInfo u where u.login = :login")
    public UserInfo getByLogin(@Param("login") String login);
}
