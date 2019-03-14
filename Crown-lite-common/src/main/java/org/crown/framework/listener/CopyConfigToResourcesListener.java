package org.crown.framework.listener;

import lombok.extern.slf4j.Slf4j;
import org.crown.common.spring.SpringProfileUtil;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 从 confs 文件夹拷贝文件到 resources
 */
@Slf4j
@Component
public class CopyConfigToResourcesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        SpringProfileUtil.copyFromConfs(event.getEnvironment());
    }

}