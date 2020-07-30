package com.test.spring.reource;

import com.google.common.base.Charsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * @author Carisy
 * @since 2020/7/29
 */
public class MetaInfoStreamHandlerFactory extends URLStreamHandler implements URLStreamHandlerFactory {

    private static final String PREFIX = "metainfo";

    private static final String BASE_PATH = "META-INF/";

    static {
        URL.setURLStreamHandlerFactory(new MetaInfoStreamHandlerFactory());
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol == null || !protocol.equals(PREFIX)) {
            return null;
        }
        return this;
    }

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        if (u == null || !PREFIX.equals(u.getProtocol())) {
            return null;
        }
        return new sun.net.www.URLConnection(u) {
            @Override
            public void connect() throws IOException {

            }

            @Override
            public InputStream getInputStream() throws IOException {
                final URL varUrl = this.url;
                String cleanedPath = StringUtils.cleanPath(varUrl.getPath());
                if(cleanedPath.startsWith("/")){
                    cleanedPath = cleanedPath.substring(1);
                }
                return new ClassPathResource(BASE_PATH + cleanedPath).getInputStream();
            }
        };
    }

    public static void main(String[] args) throws IOException {
        System.out.println(StreamUtils.copyToString(new URL("metainfo:/person.properties").openStream(), Charsets.UTF_8));
    }
}
