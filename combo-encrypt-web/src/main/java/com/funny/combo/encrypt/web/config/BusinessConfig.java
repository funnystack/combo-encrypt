package com.funny.combo.encrypt.web.config;


import com.funny.combo.encrypt.web.domain.SecurityKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class BusinessConfig extends WebMvcConfigurationSupport {


    @Bean
    public KeyConfig keyConfig(){
        KeyConfig config = new KeyConfig();
        SecurityKey securityKey = new SecurityKey();
        securityKey.setKey("0123456789012345");// 密钥必须是16位

        securityKey.setDepartment("b2c");//部门
        securityKey.setGroup("ds");//团队
        securityKey.setBusiness("123");//业务线

        securityKey.setPrefix('v');
        securityKey.setSequence(1);
        config.addSecurityKey(securityKey);
        return config;
    }
}
