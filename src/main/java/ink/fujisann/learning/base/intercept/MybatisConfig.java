//package ink.fujisann.learning.base.intercept;
//
//import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
///**
// * 解决pageHelper拦截器导致自定义拦截器失效的问题
// * 通过{@code @AutoConfigureAfter}指定自定义拦截器在pageHelper拦截器前执行
// *
// * @author raiRezon
// * @version 2020/11/16
// */
//@Configuration
//@AutoConfigureAfter(PageHelperAutoConfiguration.class)
//public class MybatisConfig {
//
//    private List<SqlSessionFactory> sqlSessionFactoryList;
//
//    @Autowired
//    public void setSqlSessionFactoryList(List<SqlSessionFactory> sqlSessionFactoryList) {
//        this.sqlSessionFactor/helloWithoutShiroyList = sqlSessionFactoryList;
//    }
//
//    private MybatisResult mybatisResult;
//
//    @Autowired
//    public void setMybatisResult(MybatisResult mybatisResult) {
//        this.mybatisResult = mybatisResult;
//    }
//
//    @PostConstruct
//    public void addMyInterceptor() {
//        //添加自定义的拦截器
//        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
//            sqlSessionFactory.getConfiguration().addInterceptor(mybatisResult);
//        }
//    }
//
//}
//
