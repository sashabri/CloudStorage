package com.example.cloudstorage.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "user_files_info")
public class UserFileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "data", columnDefinition = "bitea")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    public UserFileInfo() {
    }

    public Integer getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setData(byte[] info) {
        this.data = info;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFileInfo that = (UserFileInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(fileName, that.fileName) && Arrays.equals(data, that.data) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fileName, user);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
