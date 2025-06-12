package com.bcu.information.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    private String name;
    private String url;
    private Long size;
    private String type;
} 