package com.renxh.mock

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

object IPConfig {
    fun getIpAddress(context: Context): String? {
        val info: NetworkInfo? = (context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).getActiveNetworkInfo()
        if (info != null && info.isConnected()) {
            // 3/4g网络
            if (info.getType() === ConnectivityManager.TYPE_MOBILE) {
                try {
                    val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val intf: NetworkInterface = en.nextElement()
                        val enumIpAddr: Enumeration<InetAddress> = intf.getInetAddresses()
                        while (enumIpAddr.hasMoreElements()) {
                            val inetAddress: InetAddress = enumIpAddr.nextElement()
                            if (!inetAddress.isLoopbackAddress() && inetAddress is Inet4Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                } catch (e: SocketException) {
                    e.printStackTrace()
                }
            } else if (info.getType() === ConnectivityManager.TYPE_WIFI) {
                //  wifi网络
                val wifiManager: WifiManager =
                    context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo: WifiInfo = wifiManager.getConnectionInfo()
                return intIP2StringIP(wifiInfo.getIpAddress())
            } else if (info.getType() === ConnectivityManager.TYPE_ETHERNET) {
                // 有限网络
                return getLocalIp()
            }
        }
        return null
    }

    private fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }


    // 获取有限网IP
    private fun getLocalIp(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface
                .getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = intf
                    .getInetAddresses()
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress()
                        && inetAddress is Inet4Address
                    ) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
        }
        return "0.0.0.0"
    }

}