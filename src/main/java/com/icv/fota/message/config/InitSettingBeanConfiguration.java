package com.icv.fota.message.config;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.icv.fota.message.util.HttpUtil;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class InitSettingBeanConfiguration {
    public InitSettingBeanConfiguration() throws YamlException {
//        String result = HttpUtil.sendGet("http://10.100.9.2:8888/dev-conf.yml",null);
//
//        YamlReader reader = new YamlReader(result);
//        Map<String,Object> setMap = reader.read(Map.class);
//
//        System.out.println(setMap);
    }
}
