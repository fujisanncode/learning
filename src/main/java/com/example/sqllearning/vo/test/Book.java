package com.example.sqllearning.vo.test;

import java.util.List;
import lombok.Data;

@Data
public class Book {

    private List<Chapter> chapterList;
}
