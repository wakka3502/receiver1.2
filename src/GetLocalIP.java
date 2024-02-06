//构造函数获取本地所有的IP地址，再排除明显不是正常网卡IP的部分，以得到本地所有网卡的IP，结果存储在 HashSet<String> lan内

//方法getIP 返回HashSet<String> lan对象
//方法isValidIPAddress判断IP是否是正常网卡IP


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


import java.util.HashSet;

public class GetLocalIP {

    //初始化全局变量
    public  HashSet<String> lan = new HashSet<>();

    //构造函数
    public GetLocalIP() throws SocketException {

    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()){
        NetworkInterface ni = interfaces.nextElement();
        Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (isValidIPAddress(address.getHostAddress())) {
                    System.out.println("本地IP：" + address.getHostAddress());
                    lan.add(address.getHostAddress());//将本机的所有IP存在lan中
                }
            }
        }
    }
    public static boolean isValidIPAddress(String ip) {

        String ipRegex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        return ip.matches(ipRegex);
    }

    public  HashSet getIP(){
        return this.lan;

    }


}

