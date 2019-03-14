/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown;

import lombok.extern.slf4j.Slf4j;
import org.crown.common.spring.SpringProfileUtil;
import org.crown.framework.listener.CopyConfigToResourcesListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @see tomcat 调优请添加 {@link WebServerFactoryCustomizer}
 */
@Slf4j
@SpringBootApplication
public class CrownApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CrownApplication.class);

        /*
         * 尝试直接替换 confs 文件，失败则 通过监听器去处理替换 confs 文件
         */
        if (!SpringProfileUtil.tryCopyFromConfs()) {
            application.addListeners(new CopyConfigToResourcesListener());
        }
        ConfigurableApplicationContext applicationContext = application.run(args);

        /*
        * 打印部分环境配置参数
        */
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        String activeProfile = SpringProfileUtil.getEnvProperty(env, "spring.profiles.active", String.class, "local");
        boolean devtools = SpringProfileUtil.getEnvProperty(env, "spring.devtools.restart.enabled", Boolean.class, false);
        log.info("环境 {} 启动程序 {} 热部署： {}", activeProfile, applicationContext.getApplicationName(), devtools);
    }

}
