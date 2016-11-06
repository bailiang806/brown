package com.happydriving.rockets.server.component.tools;

import com.happydriving.rockets.server.common.BusinessException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP相关组件服务提供者
 *
 * @author mazhiqiang
 */
@Component
public class HttpRelatedComponent {

    public static final int BUFFER_SIZE = 4096;

    /**
     * 用HTTP GET的方式，下载httpUrl中的文件，并保存至savedPath本地目录中
     *
     * @param httpUrl
     * @param savedPath
     * @throws BusinessException
     */
    public void getMethodDownloadFile(String httpUrl, String savedPath) throws BusinessException {
        HttpURLConnection con = null;
//        String url =
//                "http://qr.liantu.com/api.php?bg=ffffff&fg=3d516b&el=h&w=500&m=10&text=http://www.ejiapei.com/index_campaign.html&logo=http://www.ejiapei.com/images/ejiapei_300_300_LOGO.jpg";
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            con = (HttpURLConnection) new URL(httpUrl).openConnection();
            con.setRequestMethod("GET");
            con.connect();
            byte[] buffer = new byte[BUFFER_SIZE];
            bis = new BufferedInputStream(con.getInputStream());
//            fos = new FileOutputStream(new File("/Users/mazhiqiang/Downloads/2.jpg"));
            fos = new FileOutputStream(new File(savedPath));
            int size;
            while ((size = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, size);
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(fos);
        }
    }

}
