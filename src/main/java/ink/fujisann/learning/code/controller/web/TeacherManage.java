package ink.fujisann.learning.code.controller.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ink.fujisann.learning.base.exception.BusinessException.Builder;
import ink.fujisann.learning.base.exception.BusinessExceptionEnum;
import ink.fujisann.learning.code.dao.TeacherTMapper;
import ink.fujisann.learning.code.mybatis.StuTeaT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(description = "查询教师表的相关接口", tags = {"teacher-manage"})
@RestController
@RequestMapping("/teacher-manage")
public class TeacherManage {

    // 异常标志位0
    private static final String EXCEPTION0 = "0";
    // 异常标志位1
    private static final String EXCEPTION1 = "1";

    @Autowired
    private TeacherTMapper teacherTMapper;

    @PostMapping("/query-teachers/{curPage}/{pageSize}")
    @ApiOperation(value = "查询", notes = "查询所有学生和老师 关联")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", name = "curPage", value = "当前页码", required = true, dataType = "Integer",
            defaultValue = "1"),
        @ApiImplicitParam(paramType = "path", name = "pageSize", value = "页容量", required = true, dataType = "Integer",
            defaultValue = "15"),
        @ApiImplicitParam(paramType = "body", name = "stus", value = "学生Id集合", required = true, dataType = "String",
            allowMultiple = true) // 集合
    })
    public PageInfo<StuTeaT> queryTeas(@PathVariable Integer curPage, @PathVariable Integer pageSize,
        @RequestBody List<String> stus) {
        PageHelper.startPage(curPage, pageSize);
        List<StuTeaT> teas = teacherTMapper.selectStuTea(stus);
        PageInfo<StuTeaT> pageArticles = new PageInfo<>(teas);
        return pageArticles;
    }

    @GetMapping("/exception/{flag}")
    @ApiOperation(value = "测试异常返回", notes = "路径参数传入0，则抛出异常")
    @ApiImplicitParam(name = "flag", value = "异常标志", paramType = "path", defaultValue = "0", required = true)
    public String exceptionDemo(@PathVariable String flag) {
        switch (flag) {
            case EXCEPTION0:
                throw new RuntimeException("runtime exception");
            case EXCEPTION1:
                throw new Builder().code(BusinessExceptionEnum.EXCEPTION_PARA.getCode())
                        .msg(BusinessExceptionEnum.EXCEPTION_PARA.getMsg()).build();
            default:
                return flag;
        }
    }
}
