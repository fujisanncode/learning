package ink.fujisann.learning.code.controller.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import ink.fujisann.learning.code.pojo.PageReq;
import ink.fujisann.learning.code.pojo.shelf.Book;
import ink.fujisann.learning.code.pojo.shelf.Web;
import ink.fujisann.learning.code.service.ShelfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
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
@ApiSort(2)
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

    @ApiOperationSupport(order = 3)
    @ApiOperation("新增网站")
    @PostMapping("/addWeb")
    public void addWeb(@RequestBody Web web) {
        shelfService.addWeb(web);
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation("分页查询网站列表")
    @GetMapping("/pageWeb")
    public Page<Web> pageWeb(@ModelAttribute PageReq pageReq) {
        return shelfService.pageWeb(pageReq);
    }

}
