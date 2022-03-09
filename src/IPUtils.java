import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPUtils {
    public static void printIpv4Interfaces() throws SocketException {
        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = e.nextElement();
            Enumeration<InetAddress> ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = ee.nextElement();
                if (i.getClass() == Inet4Address.class){ //filter for ipv4
                    System.out.printf("%-40s\t",n.getDisplayName());
                    System.out.println(i.getHostAddress());
                }
            }
        }
    }
}
