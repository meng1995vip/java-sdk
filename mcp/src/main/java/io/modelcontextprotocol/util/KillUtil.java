package io.modelcontextprotocol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName KillUtil
 * @Author menglingle
 * @Date 2025/8/14 14:13
 * @Version 1.0
 * @Description
 **/
public class KillUtil {
    private static final Logger logger = LoggerFactory.getLogger(KillUtil.class);
    private static String WIN_KILL_COMMAND = "taskkill %s /F /T";
    private static String KILL_COMMAND = "kill -9 %s && pkill -P %s -9";
    /**
     * 结束进程
     * @param pids
     */
    public static void killProcess(List<Long> pids){
        if (null == pids || 0 == pids.size()) {
            return;
        }
        String command;
        if(OSDetector.OSType.WINDOWS.equals(OSDetector.getOSType())){
            // windows
            String pidStr = pids.stream().map((i) -> "/PID " + i).collect(Collectors.joining(" "));
            command = String.format(WIN_KILL_COMMAND, pidStr);
        }else {
            // other
            String pidStr = pids.stream().map(String::valueOf).collect(Collectors.joining(" "));
            command = String.format(KILL_COMMAND, pidStr, pidStr);
        }
        Process killProcess = null;
        try {
            killProcess = Runtime.getRuntime().exec(command);
            boolean flag = killProcess.waitFor(3000, TimeUnit.MILLISECONDS);
            if(!flag){
                logger.error("Failed to execute command {} within {} millis, force to destroy process", command, command);
            }
        }catch (Exception e) {
            logger.error("Failed to execute command {} ", command);
        }finally {
            if(null != killProcess){
                killProcess.destroyForcibly();
            }
        }
    }
}
