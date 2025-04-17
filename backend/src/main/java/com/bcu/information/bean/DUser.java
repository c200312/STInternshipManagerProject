package com.bcu.information.bean;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class DUser {
    @Id
    private String id;
    private String name;

    private List<DCompany> company = new ArrayList<>();
    private List<DDiary> diary = new ArrayList<>();
    private List<DComment> comment = new ArrayList<>();

    public DUser() {
    }

    public DUser(List<DComment> comment, List<DCompany> company, List<DDiary> diary, String id, String name) {
        this.comment = comment;
        this.company = company;
        this.diary = diary;
        this.id = id;
        this.name = name;
    }

    public List<DComment> getComment() {
        return comment;
    }

    public void setComment(List<DComment> comment) {
        this.comment = comment;
    }

    public List<DCompany> getCompany() {
        return company;
    }

    public void setCompany(List<DCompany> company) {
        this.company = company;
    }

    public List<DDiary> getDiary() {
        return diary;
    }

    public void setDiary(List<DDiary> diary) {
        this.diary = diary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
