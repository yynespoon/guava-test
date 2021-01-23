package com.test;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiaoyu
 * @since 2020/11/21
 */
public class TestMain {

    static int i = 0;

    private static ConcurrentHashMap<String, Object> beanMap;

    static {
        beanMap = new ConcurrentHashMap<>();
    }

    public static  <T> T get(Class<T> clazz) {
        try {
            String className = clazz.getName();
            synchronized (className) {
                Object obj = beanMap.get(className);
                if (obj == null) {
                    Class<?> dao = Class.forName(className);
                    obj = dao.newInstance();
                    beanMap.put(className, obj);
                }
                return (T) obj;
            }

        } catch (Exception e) {
            //
        }
        return null;
    }

    static class A{

        static {
            try {
                try {
                    Object o = Class.forName(B.class.getName()).newInstance();
                    System.out.println(o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    static class B {
        A a = get(A.class);

        public B(){
            i++;
        }
    }

    public static void main(String[] args) {
        get(A.class);
    }
}
