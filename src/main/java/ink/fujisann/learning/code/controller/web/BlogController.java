package ink.fujisann.learning.code.controller.web;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import ink.fujisann.learning.code.mongo.MongoBlog;
import ink.fujisann.learning.code.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@ApiSort(3)
public class BlogController {

    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequiresPermissions({"/blog/save"})
    @ApiOperation("保存博客")
    @PostMapping("/save")
    public String save(@RequestBody MongoBlog mongoBlog) {
        return blogService.save(mongoBlog);
    }

    @RequiresPermissions({"/blog/findAll"})
    @ApiOperation("查询全部博客")
    @GetMapping("/findAll")
    public List<MongoBlog> findAll() {
        return blogService.findAll();
    }

    @RequiresPermissions({"/blog/findById"})
    @ApiOperation("按id查询博客")
    @GetMapping("/findById")
    public MongoBlog findById(@RequestParam("id") String id) {
        return blogService.findById(id);
    }

    @RequiresPermissions({"/blog/update"})
    @ApiOperation("更新指定id的博客")
    @PostMapping("/update")
    public void update(@RequestBody MongoBlog mongoBlog) {
        blogService.update(mongoBlog);
    }


}
