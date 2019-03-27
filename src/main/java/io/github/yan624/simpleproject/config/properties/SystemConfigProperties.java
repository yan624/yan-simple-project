package io.github.yan624.simpleproject.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:必须写上@Data，因为属性注入需要set方法
 *
 * @Auther: Zhu Chongyan
 * @Date: 2018/8/8 22:44
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(prefix = "io.github.yan624")
@Data
public class SystemConfigProperties {

}
