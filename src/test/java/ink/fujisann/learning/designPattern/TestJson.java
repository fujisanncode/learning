package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.code.test.Book;
import ink.fujisann.learning.code.test.Chapter;
import ink.fujisann.learning.code.test.Paragraph;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest (classes = LearningApplication.class)
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
