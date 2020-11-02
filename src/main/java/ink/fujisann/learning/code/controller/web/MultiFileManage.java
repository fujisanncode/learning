package ink.fujisann.learning.code.controller.web;

import ink.fujisann.learning.base.utils.common.CommonUtil;
import ink.fujisann.learning.base.utils.ftp.FtpClientImpl;
import ink.fujisann.learning.base.utils.office.ParseExcelUtil;
import ink.fujisann.learning.base.utils.smb.SmbUtil;
import ink.fujisann.learning.code.dao.PersonMapper;
import ink.fujisann.learning.code.pojo.mybatis.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;

@RestController
@RequestMapping("/multi")
@Api(value = "multi", tags = "文件服务")
@Slf4j
public class MultiFileManage {

    @Autowired
    private FtpClientImpl ftpClient;

    @Autowired
    private UserManage userManage;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonManage personManage;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @ApiOperation (value = "上传文件", notes = "上传文件")
    @PostMapping ("/upload")
    public String uploadFile(MultipartFile file) {
        String fileNameTemp = CommonUtil.getUuid().concat("_").concat(file.getOriginalFilename());
        try {
            ftpClient.uploadFile(file.getInputStream(), fileNameTemp, "/");
        } catch (IOException e) {
            log.error("upload fail");
        }
        return fileNameTemp;
    }


    @ApiOperation (value = "parse-excel", notes = "上传并解析excel，插入数据库")
    @PostMapping ("/parse-excel")
    public int parseExcel(MultipartFile file) {
        ArrayList<String> firstRow = ParseExcelUtil.parseExcel(file).get(0);
        int insertRows = userManage.insertUser(userManage.parseUserFromList(firstRow));
        return insertRows;
    }

    @ApiOperation (value = "parse-excel-batch", notes = "批量解析excel文件")
    @PostMapping ("/parse-excel-batch")
    @Transactional
    public int parseExcelBatch(MultipartFile file) {
        StopWatch stopwatch = new StopWatch("parse-excel-batch");
        stopwatch.start("parse-excel");
        ArrayList<ArrayList<String>> rows = ParseExcelUtil.parseExcel(file);
        stopwatch.stop();
        stopwatch.start("insert-person");
        // 开启批处理，关闭自动提交 (关闭当前session的自动提交仅在存在事务的情况下生效，万条数据5s)
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        for (int i = 0; i < rows.size(); i++) {
            Person person = personManage.parsePersonFromList(rows.get(i));
            personMapper.insertSelective(person);
            // // 每1000条提交一次 flush和commit都是提交数据， （分批提交，比一次性提交快）
            if (i != 0 && i % 1000 == 0) {
                log.info("flush i {}", i);
                // sqlSession.flushStatements();
                sqlSession.commit();
                stopwatch.stop();
                stopwatch.start("flush ".concat(String.valueOf(i)));
            }
        }
        // 提交
        // sqlSession.flushStatements();
        sqlSession.commit();
        sqlSession.close();
        stopwatch.stop();
        log.info("insert-person-batch cost {}ms", stopwatch.prettyPrint());
        return rows.size();
    }

    @ApiOperation (value = "下载", notes = "根据id下载文件")
    @GetMapping ("/download/{id}")
    public void downloadFile(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            // 不限制文件类型
            response.setContentType("application/octet-stream");
            // 设置响应文件名称
            String rtFileName = id.substring(33);
            response.addHeader("Content-Disposition", "attachment;filename=".concat(rtFileName));
            // 文件写入流拷贝到响应的输出流中
            IOUtils.copy(ftpClient.readFileToIn(id, "/"), out);
            //close前会flush
            //out.flush();
        } catch (IOException e) {
            log.error("download error", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("close out stream", e);
                }
            }
        }
    }

    // 根据文件id，下载文件，获取输入流.
    public MultipartFile getFile(String id) {
        InputStream inputStream = ftpClient.readFileToIn(id, "/");
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(id, id, "application/vnd.ms-excel", inputStream);
        } catch (IOException e) {
            log.error("get file error {}", e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close error {}", e);
                }
            }
        }
        return multipartFile;
    }

    @GetMapping ("/get-smb-files")
    @ApiOperation (value = "get smb file", notes = "获取smb共享文件列表")
    public void getSmbFiles(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        OutputStream outputStream = null;
        InputStream inputStream = null;
        SmbFile smbFile = SmbUtil.getSmbInstance();
        if (smbFile != null) {
            try {
                smbFile.connect();
                SmbFile[] files = smbFile.listFiles();
                inputStream = new BufferedInputStream(new SmbFileInputStream(files[0]));
                for (SmbFile file : files) {
                    String fileName = file.getName();
                    log.info(fileName);
                    response.addHeader("Content-Disposition", MessageFormat.format("attachment;filename={0}", fileName));
                    outputStream = response.getOutputStream();
                    IOUtils.copy(inputStream, outputStream);
                }
            } catch (IOException e) {
                log.error("connect to smb fail: ", e);
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    log.error("getSmbFiles in or out error", e);
                }
            }
        }
    }
}
