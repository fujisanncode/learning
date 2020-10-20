package ink.fujisann.learning.utils.ftp;

import java.io.InputStream;

public interface FtpClientI {

    public Boolean uploadFile(InputStream inputStream, String remoteFileName, String remoteDir);

    public void downloadFile(String remoteFileName, String localFileName, String remoteDir);

    public String readFileToBase64(String remoteFileName, String remoteDir);

    public InputStream readFileToIn(String remoteFileName, String remoteDir);
}
