package com.example.sqllearning.designPattern;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.vo.test.Book;
import com.example.sqllearning.vo.test.Chapter;
import com.example.sqllearning.vo.test.Paragraph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = SqlLearningApplication.class)
@Slf4j
public class TestJson {

    @Test
    public void testJson() {
        Paragraph paragraph = new Paragraph();
        paragraph.setContent("我是中国人");
        paragraph.setCount("10");

        Chapter chapter = new Chapter();
        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(paragraph);
        chapter.setParagraphs(paragraphs);

        Book book = new Book();
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(chapter);
        book.setChapterList(chapters);

        JSONObject sfJson = JSONObject.fromObject(book);
        JSONArray chapterArr = sfJson.getJSONArray("chapterList");
        for (int i = 0; i < chapterArr.size(); i++) {
            JSONArray paragraphArr = ((JSONObject) chapterArr.get(i)).getJSONArray("paragraphs");
            for (int j = 0; j < paragraphArr.size(); j++) {
                String ct = ((JSONObject) paragraphArr.get(i)).getString("content");
                ((JSONObject) paragraphArr.get(i)).put("title", ct);
                ((JSONObject) paragraphArr.get(i)).remove("count");
                ((JSONObject) paragraphArr.get(i)).remove("content");
            }
        }
        log.info(sfJson.toString());
    }

    @Test
    public void testDemo() {
        Set a = new HashSet();
        a.add(3);
        a.add(5);
        a.add(2);
        a.add(4);
        a.add(1);
        Collections.sort(new ArrayList<>(a), new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        log.info(Arrays.toString(a.toArray()));
    }
}
