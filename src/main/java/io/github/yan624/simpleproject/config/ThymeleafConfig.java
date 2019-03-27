package io.github.yan624.simpleproject.config;

import io.github.yan624.common.condition.base.LinuxCondition;
import io.github.yan624.common.condition.base.WindowsCondition;
import io.github.yan624.utils.format.LogPrintingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.*;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@ComponentScan
@Slf4j
public class ThymeleafConfig {

    @Bean
    public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("utf-8");
        //不知道这个缓存和下面的模板解析器的缓存是什么关系，但是应该不是同一个意思
        //我在模板解析器设置false，在这行代码之上打印了一下还是true
        viewResolver.setCache(false);
        viewResolver.setViewNames(new String[]{"templates/thymeleaf/*", ".html", ".xhtml"});
        return viewResolver;
    }

    /**
     * 模板解析器
     *
     * @return
     */
    @Conditional(WindowsCondition.class)
    @Primary//springboot貌似内置一个模板解析器，所以将这个bean定义为首选
    @Bean
    public SpringResourceTemplateResolver windowsTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setOrder(1);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //不缓存页面，即服务器开启时修改thymeleaf页面会有变化。
        templateResolver.setCacheable(false);
        log.info(new LogPrintingTemplate()
                .type("thymeleaf页面缓存禁用(￣▽￣)~*")
                .print());
        templateResolver.setCharacterEncoding("utf-8");
        return templateResolver;
    }

    @Conditional(LinuxCondition.class)
    @Primary//springboot貌似内置一个模板解析器，所以将这个bean定义为首选
    @Bean
    public SpringResourceTemplateResolver linuxTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setOrder(1);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //缓存页面
        templateResolver.setCacheable(true);
        log.info(new LogPrintingTemplate()
                .type("thymeleaf页面缓存开启(￣▽￣)~*")
                .print());
        templateResolver.setCharacterEncoding("utf-8");
        return templateResolver;
    }

    /**
     * 模版引擎
     *
     * @return
     */
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
}
