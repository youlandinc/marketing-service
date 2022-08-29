
package com.youland.markting.config.template.tool;

import cn.hutool.extra.spring.SpringUtil;
import com.youland.markting.config.template.bean.CommonLoggerInitializer;
import com.youland.markting.config.template.dataobj.LogEntry;
import com.youland.markting.config.template.dataobj.TraceId;
import com.youland.markting.config.template.enums.LogTypeEnum;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogPrinter {

    private static ThreadLocal<TraceId> traceId = new ThreadLocal<>();

    private static ThreadLocal<Queue<LogEntry>> weblogEntrys = new ThreadLocal<>();

    private static ThreadLocal<Queue<LogEntry>> apiEntrys = new ThreadLocal<>();

    private static ThreadLocal<Queue<LogEntry>> salEntrys = new ThreadLocal<>();

    private static ThreadLocal<Queue<LogEntry>> bizErrorEntrys = new ThreadLocal<>();

    private static ThreadLocal<Queue<LogEntry>> traceEntrys = new ThreadLocal<>();

    private static Map<LogTypeEnum, Logger> loggerCache;

    public static void printTrace(String LogContent) {
        TraceId trace = fetchTrace();
        LogEntry entry = buildLogEntry(LogContent, trace);
        Queue<LogEntry> queueLog = traceEntrys.get();
        if (queueLog != null) {
            queueLog.add(entry);
        } else {
            queueLog = new ConcurrentLinkedQueue<>();
            queueLog.add(entry);
            traceEntrys.set(queueLog);
        }
    }

    public static void printWebBoundry(String LogContent) {
        TraceId trace = fetchTrace();
        LogEntry entry = buildLogEntry(LogContent, trace);
        Queue<LogEntry> queueLog = weblogEntrys.get();
        if (queueLog != null) {
            queueLog.add(entry);
        } else {
            queueLog = new ConcurrentLinkedQueue<>();
            queueLog.add(entry);
            weblogEntrys.set(queueLog);
        }
    }

    public static void printWebBoundry(String LogContent,  Throwable e) {
        TraceId trace = fetchTrace();
        LogEntry entry = new LogEntry();
        entry.setLogContent(LogContent);
        entry.setStacks(e);
        entry.setTimestamp(System.nanoTime() + "");
        entry.setTraceId(trace.getId());
        Queue<LogEntry> queueLog = weblogEntrys.get();
        if (queueLog != null) {
            queueLog.add(entry);
        } else {
            queueLog = new ConcurrentLinkedQueue<>();
            queueLog.add(entry);
            weblogEntrys.set(queueLog);
        }
    }

    private static LogEntry buildLogEntry(String LogContent, TraceId trace) {
        LogEntry entry = new LogEntry();
        entry.setLogContent(LogContent);
        entry.setTimestamp(System.nanoTime() + "");
        entry.setTraceId(trace.getId());
        return entry;
    }

    public static void printApiBoundry(String LogContent) {
        TraceId trace = fetchTrace();
        LogEntry entry = buildLogEntry(LogContent, trace);
        Queue<LogEntry> queueLog = apiEntrys.get();
        if (queueLog != null) {
            queueLog.add(entry);
        } else {
            queueLog = new ConcurrentLinkedQueue<>();
            queueLog.add(entry);
            apiEntrys.set(queueLog);
        }
    }

    public static void printSalBoundry(String LogContent) {
        TraceId trace = fetchTrace();
        LogEntry entry = buildLogEntry(LogContent, trace);
        Queue<LogEntry> queueLog = salEntrys.get();
        if (queueLog != null) {
            queueLog.add(entry);
        } else {
            queueLog = new ConcurrentLinkedQueue<>();
            queueLog.add(entry);
            salEntrys.set(queueLog);
        }
    }

    public static void printApiBoundry(String LogContent, Throwable e) {
        TraceId trace = fetchTrace();
        fetchTrace();
        LogEntry entry = new LogEntry();
        entry.setLogContent(LogContent);
        entry.setStacks(e);
        entry.setTimestamp(System.nanoTime() + "");
        entry.setTraceId(trace.getId());
        Queue<LogEntry> queueLog = apiEntrys.get();
        if (queueLog != null) {
            queueLog.add(entry);
        } else {
            queueLog = new ConcurrentLinkedQueue<>();
            queueLog.add(entry);
            apiEntrys.set(queueLog);
        }
    }
    
    public static void printBizError(String LogContent, Throwable e) {
        TraceId trace = fetchTrace();
        fetchTrace();
        LogEntry entry = new LogEntry();
        entry.setLogContent(LogContent);
        entry.setStacks(e);
        entry.setTimestamp(System.nanoTime() + "");
        entry.setTraceId(trace.getId());
        Queue<LogEntry> queueLog = bizErrorEntrys.get();
        if (queueLog != null) {
            queueLog.add(entry);
        } else {
            queueLog = new ConcurrentLinkedQueue<>();
            queueLog.add(entry);
            bizErrorEntrys.set(queueLog);
        }
    }

    public static void printSalBoundry(String LogContent, Throwable e) {
        TraceId trace = fetchTrace();
        fetchTrace();
        LogEntry entry = new LogEntry();
        entry.setLogContent(LogContent);
        entry.setStacks(e);
        entry.setTimestamp(System.nanoTime() + "");
        entry.setTraceId(trace.getId());
        Queue<LogEntry> queueLog = bizErrorEntrys.get();
        if (queueLog != null) {
            queueLog.add(entry);
        } else {
            queueLog = new ConcurrentLinkedQueue<>();
            queueLog.add(entry);
            bizErrorEntrys.set(queueLog);
        }
    }

    public static void flush() {
        flushTargetlog(LogTypeEnum.WEB_BOUNDRY, weblogEntrys);
        flushTargetlog(LogTypeEnum.SAL_BOUNDRY, salEntrys);
        flushTargetlog(LogTypeEnum.API_BOUNDRY, apiEntrys);
        flushTargetlog(LogTypeEnum.BIZERROR_BOUNDRY, bizErrorEntrys);
        flushTargetlog(LogTypeEnum.TRACE_BOUNDRY, traceEntrys);
    }
    
    public static void flushTarget(LogTypeEnum en) {
    	switch (en) {
    		case SAL_BOUNDRY:
    			flushTargetlog(LogTypeEnum.SAL_BOUNDRY, salEntrys);
    			flushTargetlog(LogTypeEnum.TRACE_BOUNDRY, traceEntrys);
    			break;
			case API_BOUNDRY: 
				flushTargetlog(LogTypeEnum.SAL_BOUNDRY, salEntrys);
		        flushTargetlog(LogTypeEnum.API_BOUNDRY, apiEntrys);
		        flushTargetlog(LogTypeEnum.BIZERROR_BOUNDRY, bizErrorEntrys);
		        flushTargetlog(LogTypeEnum.TRACE_BOUNDRY, traceEntrys);
		        break;
			case WEB_BOUNDRY:		        
			default:
				flushTargetlog(LogTypeEnum.WEB_BOUNDRY, weblogEntrys);
				flushTargetlog(LogTypeEnum.SAL_BOUNDRY, salEntrys);
				flushTargetlog(LogTypeEnum.API_BOUNDRY, apiEntrys);
				flushTargetlog(LogTypeEnum.BIZERROR_BOUNDRY, bizErrorEntrys);
				flushTargetlog(LogTypeEnum.TRACE_BOUNDRY, traceEntrys);
				break;
			}
    }

    private static void flushTargetlog(LogTypeEnum e, ThreadLocal<Queue<LogEntry>> threadLocal) {
        Queue<LogEntry> salQueueLog = threadLocal.get();
        if (salQueueLog != null) {
            String logContent = printLogDetail(salQueueLog);
            // fetch logger
            Logger logger = fetchLogger(e);
            logger.info(logContent);
            threadLocal.remove();
        }
    }

    private static String printLogDetail(Queue<LogEntry> queue) {
        StringBuilder builder = new StringBuilder("\n-----------------(" + "traceId start" + ")--------------------\n");
        // log format--（traceId start)(logContent)(stackTrace)(timestamp)(costime)(traceId end)--
        if (!queue.isEmpty()) {
            queue.iterator().forEachRemaining(item -> {
                builder.append(printDigest(item));
            });
        }
        builder.append("\n-----------------(" + "traceId end" + ")----------------------\n");
        return builder.toString();
    }

    private static String printDigest(LogEntry entry) {
        StringBuilder builder = new StringBuilder("(" + entry.getTimestamp() + "|" + entry.getTraceId() + ")").append(entry.getLogContent()).append("||").append(entry.getCostTime()).append("||")
                .append(entry.getStacks() != null ? ExceptionUtils.getStackTrace(entry.getStacks()) : null).append("\n");
        // build degist log
        return builder.toString();
    }

    public static TraceId fetchTrace() {
        if (traceId.get() != null) {
            return traceId.get();
        } else {
            TraceId trace = new TraceId(UUID.randomUUID().toString().replaceAll("-", ""), System.nanoTime(), System.nanoTime());
            traceId.set(trace);
            return trace;
        }
    }

    private static final Logger fetchLogger(LogTypeEnum logTypeEnum) {
        if (loggerCache != null) {
            return loggerCache.get(logTypeEnum);
        }
        synchronized (CommonLoggerInitializer.class) {
            if (loggerCache == null) {
                var commonLoggerInitializer = SpringUtil.getBean(CommonLoggerInitializer.class);
                loggerCache = commonLoggerInitializer.getLoggerCache();
            }
            return loggerCache.get(logTypeEnum);
        }
    }
}
