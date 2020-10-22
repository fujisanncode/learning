package ink.fujisann.learning.code.controller.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 书架
 *
 * @author hulei
 * @version 2020/10/22
 */
@Slf4j
@RestController
@RequestMapping("/shelf")
public class ShelfController {

    @PostMapping("/uploadBook")
    public void uploadBook(MultipartFile file){
        String filename = file.getOriginalFilename();
        log.info(filename);
    }
}
