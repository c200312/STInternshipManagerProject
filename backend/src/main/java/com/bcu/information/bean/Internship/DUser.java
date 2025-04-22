package com.bcu.information.bean.Internship;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class DUser {
    @Id
    private String id;
    private String name;

    private List<DCompany> company = new ArrayList<>();
    private List<DDiary> diary = new ArrayList<>();
    private List<DComment> comment = new ArrayList<>();
}
