package com.test;

import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author lixiaoyu
 * @since 2020-08-13 17:29
 */
public class TestMain {

    public static void main(String[] args) throws IOException {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        List<String> list = Files.readLines(new File("D:\\GoogleDownload\\index.m3u8"), Charset.defaultCharset());
        for (int i = 0; i <list.size(); i++) {
            final int position = i;
            if(list.get(i).startsWith("#")){
                continue;
            }
            new Thread(()->{
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    String url = list.get(position);
                    byte[] forObject = new byte[0];
                    Exception ex = null;
                    do{
                        try {
                            forObject = restTemplate.getForObject(list.get(position), byte[].class);
                            FileOutputStream fileOutputStream = new FileOutputStream("D:\\GoogleDownload\\movie\\" + StringUtils.substringAfterLast(url, "/"), true);
                            fileOutputStream.write(forObject);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (RestClientException e) {
                            e.printStackTrace();
                            ex = e;
                        }
                    } while(ex != null);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }).start();
        }
    }

    @Test
    public void test() throws Exception{
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);
        SSLSocketFactory socketFactory = context.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket();
        for (String enabledProtocol : socket.getEnabledProtocols()) {
            System.out.println(enabledProtocol);
        }
    }
}
