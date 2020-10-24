package ink.fujisann.learning.code.controller.reader;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import ink.fujisann.learning.code.pojo.shelf.Book;
import ink.fujisann.learning.code.service.ShelfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 书架
 *
 * @author hulei
 * @version 2020/10/22
 */
@Slf4j
@RestController
@RequestMapping("/shelf")
@Api(tags = "书架")
@ApiSort(1)
public class ShelfController {

    private ShelfService shelfService;

    @Autowired
    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation("上传图书")
    @PostMapping("/uploadBook")
    public void uploadBook(MultipartFile file) {
        shelfService.uploadBook(file);
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("查询图书列表")
    @GetMapping("/listBook")
    List<Book> listBook() {
        return shelfService.listBook();
    }
}
