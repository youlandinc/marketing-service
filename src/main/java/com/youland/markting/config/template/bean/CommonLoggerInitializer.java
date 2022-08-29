/**
** Youland.com copyright
*/
package com.youland.markting.config.template.bean;

import com.youland.markting.config.template.enums.LogTypeEnum;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class CommonLoggerInitializer implements InitializingBean {

	@Getter
    private Map<LogTypeEnum, Logger> loggerCache = new ConcurrentHashMap<>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for(LogTypeEnum e: LogTypeEnum.values()) {
			Logger subLog = LoggerFactory.getLogger(e.getCode());
			if(subLog != null) {
				loggerCache.put(e, subLog);
			}
		}
		loggerCache = Collections.unmodifiableMap(loggerCache);
	}
	
}
