package ink.fujisann.learning.code.controller.reader;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

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

    /**
     * 操作系统名称
     */
    public static final String OS_NAME = "os.name";

    /**
     * windows系统
     */
    public static final String WINDOWS = "windows";

    @ApiOperationSupport(order = 1)
    @ApiOperation("删除图书")
    @PostMapping("/uploadBook")
    public void uploadBook(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(System.getProperty("").toLowerCase().contains("windows")) {
            String desktop = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
            File pdfFolder = new File(desktop + "\\pdf");
            if(!pdfFolder.exists()) {
                pdfFolder.mkdir();
            }
        }
        log.info(filename);
    }
}
