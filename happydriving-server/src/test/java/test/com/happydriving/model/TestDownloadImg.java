package test.com.happydriving.model;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * @author mazhiqiang
 */
public class TestDownloadImg {

    public static void main(String[] args) {
        HttpURLConnection con = null;
        String url =
                "http://qr.liantu.com/api.php?bg=ffffff&fg=3d516b&el=h&w=500&m=10&text=http://www.ejiapei.com/index_campaign.html&logo=http://www.ejiapei.com/images/ejiapei_300_300_LOGO.jpg";

        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            bis = new BufferedInputStream(con.getInputStream());
            fos = new FileOutputStream(new File("/Users/mazhiqiang/Downloads/2.jpg"));

            int size;
            while ((size = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, size);
            }

            System.out.println(new File("/Users/mazhiqiang/Downloads/2.jpg").length());
//            IOUtils.write
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(fos);
        }

    }
}
