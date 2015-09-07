import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class getipaddress {
	static String localIpAddr = null;
	public static String lookupLocalAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            netInterFace : while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                // Check for mac address
                byte[] hardwareAddress = networkInterface.getHardwareAddress();
                if ((hardwareAddress == null) || (hardwareAddress.length == 0)) {
                    continue;
                }
 
                Set<InetAddress> subAddresses = new HashSet<InetAddress>();
                Enumeration<NetworkInterface> subInterfaces = networkInterface.getSubInterfaces();
                while (subInterfaces.hasMoreElements()) {
                    NetworkInterface subInterface = subInterfaces.nextElement();
                    for (InterfaceAddress interfaceAddress : subInterface.getInterfaceAddresses()) {
                        subAddresses.add(interfaceAddress.getAddress());
                    }
                }
 
                List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                    InetAddress address = interfaceAddress.getAddress();
                    if (address.isLoopbackAddress()) {
                        continue;
                    }
                    if (subAddresses.contains(address)) {
                        continue;
                    }
                    if (address instanceof Inet4Address) {
                        localIpAddr = address.getHostAddress();
                        //System.out.println(localIpAddr);
                        break netInterFace;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        if (localIpAddr == null) {
            try {
                localIpAddr = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                localIpAddr = "127.0.0.1";
            }
        }
		return localIpAddr;
	}
}
