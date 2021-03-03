package ink.fujisann.learning.code.service.impl;

import ink.fujisann.learning.base.exception.BusinessException;
import ink.fujisann.learning.base.utils.common.SystemUtil;
import ink.fujisann.learning.code.req.PageReq;
import ink.fujisann.learning.code.pojo.Book;
import ink.fujisann.learning.code.pojo.Web;
import ink.fujisann.learning.code.repository.BookRepository;
import ink.fujisann.learning.code.repository.WebRepository;
import ink.fujisann.learning.code.service.ShelfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author hulei
 * @date 2020-10-23 22:54:22:54
 */
@Service
@Slf4j
public class ShelfServiceImpl implements ShelfService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * linux系统文件访问前缀
     */
    public static final String FILE_PREFIX = "http://fujisann.ink/file/";

    /**
     * 管理员用户id
     */
    public static final String ADMIN_USER_ID = "1";

    @Override
    public void uploadBook(MultipartFile file) {
        // 计算本地和服务器的文件保存地址
        String filename = file.getOriginalFilename();
        String pdfFolderPath;
        String fullPath;
        boolean isOsWindows = SystemUtils.IS_OS_WINDOWS;
        boolean isOsLinux = SystemUtils.IS_OS_LINUX;
        if (isOsWindows) {
            String desktop = SystemUtil.getDesktopPath();
            pdfFolderPath = desktop + "\\pdf" + "\\" + ADMIN_USER_ID;
        } else if (isOsLinux) {
            pdfFolderPath = "/soft/file/" + ADMIN_USER_ID;
        } else {
            throw new BusinessException.Builder().msg("不支持当前操作系统").build();
        }

        // 如果文件根目录不存在，则创建目录，创建失败返回错误
        File pdfFolder = new File(pdfFolderPath);
        if (!pdfFolder.exists()) {
            boolean hasCreated = pdfFolder.mkdir();
            if (!hasCreated) {
                throw new BusinessException();
            }
        }

        // 如果文件存在，返回页面异常
        fullPath = isOsWindows ? pdfFolderPath + "\\" + filename : pdfFolderPath + "/" + filename;
        File saveFile = new File(fullPath);
        if (saveFile.exists()) {
            throw new BusinessException.Builder().msg("存在同名文件").build();
        }

        // 文件流保存本地
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            log.error("multipartFile 保存本地异常", e);
            throw new BusinessException.Builder().msg("文件保存异常").build();
        }

        // 如果当前是linux系统，书籍上传后记录到数据库中
        if (isOsLinux) {
            Book save = new Book();
            save.setBookName(filename);
            save.setAccessPath(FILE_PREFIX + filename);
            save.setCurPage(0);
            save.setUserId(ADMIN_USER_ID);
            bookRepository.save(save);
        }
    }

    @Override
    public List<Book> listBook() {
        return bookRepository.findBooksByUserId(ADMIN_USER_ID);
    }

    private WebRepository webRepository;

    @Autowired
    public void setWebRepository(WebRepository webRepository) {
        this.webRepository = webRepository;
    }

    @Override
    public void addWeb(Web web) {
        webRepository.save(web);
    }

    @Override
    public Page<Web> pageWeb(PageReq pageReq) {
        Pageable pageable = PageRequest.of(pageReq.getPageNum() - 1, pageReq.getPageSize());
        return webRepository.findAll(pageable);
    }
}

