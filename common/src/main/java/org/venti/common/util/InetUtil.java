package org.venti.common.util;

import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * @author Xieningjun
 * @date 2025/2/20 10:51
 * @description 提供网络相关的工具方法
 */
public class InetUtil {

    /**
     * 获取本地机器的IPv4地址
     *
     * @return 本地IPv4地址
     * @throws SocketException 如果无法获取IPv4地址，抛出SocketException异常
     */
    public static String getLocalIPv4Address() throws SocketException {
        // 获取所有网络接口
        var networkInterfaces = NetworkInterface.getNetworkInterfaces();

        // 遍历所有网络接口
        while (networkInterfaces.hasMoreElements()) {
            var networkInterface = networkInterfaces.nextElement();

            // 获取网络接口的所有IP地址
            var inetAddresses = networkInterface.getInetAddresses();

            // 遍历所有IP地址
            while (inetAddresses.hasMoreElements()) {
                var inetAddress = inetAddresses.nextElement();

                // 如果是IPv4地址并且不是回环地址，返回该地址
                if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                    return inetAddress.getHostAddress();
                }
            }
        }

        // 如果没有找到有效的IPv4地址，抛出异常
        throw new SocketException("没有找到有效的IPv4地址");
    }
}
