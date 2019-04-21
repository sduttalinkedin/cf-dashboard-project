package com.philips.hsdp.cfdashboard;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(exclude = {})
public class CfDashBoardApplication {
    
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setWebApplicationType(WebApplicationType.SERVLET);
        springApplication.setAllowBeanDefinitionOverriding(Boolean.TRUE);
        springApplication.setLogStartupInfo(Boolean.FALSE);
        springApplication.run(CfDashBoardApplication.class, args);
    }
    
}
