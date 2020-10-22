package ink.fujisann.learning.base.configure.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置类 <br/>
 * 开启swagger2 <br/>
 * 开启knife界面(代替swagger-ui的界面) <br/>
 *
 * @author hulei
 * @date 2020/10/22
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger {

  @Bean
  public Docket swaggerSpringMvcPlugin() {
    List<Parameter> pars = new ArrayList<Parameter>();
    ParameterBuilder lanPara = new ParameterBuilder();
    lanPara
        .name("Accept-Language")
        .description("use lan")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false);
    pars.add(lanPara.build());
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("ink.fujisann.learning.code.controller"))
        .build()
        .globalOperationParameters(pars);
  }
}
