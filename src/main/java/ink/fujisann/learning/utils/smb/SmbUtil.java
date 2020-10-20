package ink.fujisann.learning.utils.smb;

import java.net.MalformedURLException;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmbUtil {

    private static String uri;
    private static String ip;
    private static String name;
    private static String password;

    public static SmbFile getSmbInstance() {
        SmbFile smbFile = null;
        // NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ip, name, password);
        String url = uri;
        // SmbFile file = new SmbFile(url);
        try {
            // smbFile = new SmbFile(uri, auth);
            smbFile = new SmbFile(url);
        } catch (MalformedURLException e) {
            log.error("getSmbInstance error: ", e);
        }
        return smbFile;
    }

    @Value ("${smb.uri}")
    public void setUri(String uri) {
        SmbUtil.uri = uri;
    }

    @Value ("${smb.ip}")
    public void setIp(String ip) {
        SmbUtil.ip = ip;
    }

    @Value ("${smb.name}")
    public void setName(String name) {
        SmbUtil.name = name;
    }

    @Value ("${smb.password}")
    public void setPassword(String password) {
        SmbUtil.password = password;
    }
}
