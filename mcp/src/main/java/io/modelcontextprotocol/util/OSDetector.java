package io.modelcontextprotocol.util;

/**
 * @ClassName OSDetector
 * @Author menglingle
 * @Date 2025/8/14 14:12
 * @Version 1.0
 * @Description
 **/
public class OSDetector {
    // 定义 OS 类型枚举
    public enum OSType {
        WINDOWS, LINUX, MACOS, UNIX, ANDROID, UNKNOWN
    }

    /**
     * 获取OS类型
     * @return
     */
    public static OSType getOSType() {
        String osName = System.getProperty("os.name").toLowerCase();
        // 判断 Windows
        if (osName.contains("win")) {
            return OSType.WINDOWS;
        }
        // 判断 macOS（兼容旧版本 JVM 返回的 "mac os x"）
        if (osName.contains("mac") || osName.contains("darwin")) {
            return OSType.MACOS;
        }
        // 判断 Linux（注意：Android 基于 Linux，需额外判断）
        if (osName.contains("linux")) {
            // 可选：进一步判断是否为 Android（需依赖系统属性或文件）
            // （见下文扩展）
            return OSType.LINUX;
        }
        // 判断 Unix 系（如 FreeBSD、OpenBSD）
        if (osName.contains("freebsd") || osName.contains("openbsd") || osName.contains("unix")) {
            return OSType.UNIX;
        }
        // 未知类型
        return OSType.UNKNOWN;
    }
}
