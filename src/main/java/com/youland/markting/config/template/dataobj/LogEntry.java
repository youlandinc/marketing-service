/**
** Youland.com copyright
*/
package com.youland.markting.config.template.dataobj;

import lombok.Data;

/**
* yeqiu 2022年7月26日 下午4:00:29
* 日志块模型
*/
@Data
public class LogEntry {

	/**
	 * 链路ID
	 */
	private String traceId;
	
	/**
	 * 日志内容
	 */
	private String logContent;
	
	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**
	 * 请求耗时
	 */
	private String costTime;
	
	/**
	 * 错误堆栈
	 */
	private Throwable stacks;
	
}
