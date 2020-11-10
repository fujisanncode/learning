package ink.fujisann.learning.code.controller.web;

import ink.fujisann.learning.code.pojo.MongoBlog;
import ink.fujisann.learning.code.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客
 *
 * @author hulei
 * @date 2020-11-10 23:12:23:12
 */
@RequestMapping("/blog")
@RestController
@Api(tags = {"博客"})
@Slf4j
public class BlogController {

    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }
    
    @ApiOperation("保存博客")
    @PostMapping("/save")
    private void save(@RequestBody MongoBlog mongoBlog) {
        blogService.save(mongoBlog);
    }

    @ApiOperation("查询全部博客")
    @GetMapping("/findAll")
    private List<MongoBlog> findAll() {
        return blogService.findAll();
    }
}
