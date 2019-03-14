package org.crown.common.spring;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.File;

@Slf4j
public class SpringProfileUtil {
    private static final String key = "spring.profiles.active";

    public static String getActiveProfile(ConfigurableEnvironment envi) {
        String activeProfile = SystemUtil.get(key);
        if (StrUtil.isNotBlank(activeProfile)) {
            return activeProfile;
        } else if (envi != null) {
            String[] activeProfiles = envi.getActiveProfiles();
            return ArrayUtil.get(activeProfiles, activeProfiles.length - 1);
        }
        return null;
    }

    public static boolean tryCopyFromConfs() {
        String activeProfile = SystemUtil.get(key);
        if (StrUtil.isNotBlank(activeProfile)) {
            copyFromConfs(activeProfile);
            return true;
        }
        return false;
    }

    public static void copyFromConfs(String activeProfile) {
        activeProfile = StrUtil.blankToDefault(activeProfile, "local");
        /*
         * 直接替换 confs 文件
         */
        if (!StrUtil.containsAnyIgnoreCase(activeProfile, "local", "dev", "test", "pro", "prod")) {
            log.error("profile not in \"local\", \"dev\", \"test\", \"pro\", \"prod\" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        log.info("拷贝环境所需文件...");
        String confsPath = StrUtil.join(File.separator, FileUtil.getWebRoot().getAbsoluteFile(), "confs");
        if (FileUtil.exist(confsPath)) {
            String classPath = ClassUtil.getClassPath();
            String confsProfilePath = StrUtil.join(File.separator, confsPath, activeProfile);
            FileUtil.copyContent(new File(confsProfilePath), new File(classPath), true);
        }
    }

    public static void copyFromConfs(ConfigurableEnvironment envi) {
        String activeProfile = getActiveProfile(envi);
        copyFromConfs(activeProfile);
    }

    public static <T> T getEnvProperty(ConfigurableEnvironment env, String key, Class<T> defaultClass, T defaultValue) {
        return Binder.get(env).bind(key, defaultClass).orElse(defaultValue);
    }
}