package com.test.spring.reource;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author lixiaoyu
 * @since 2020/7/29
 */
public class ResourceLoaderTest {

    @Test
    public void testPathMather() throws IOException {
        //base path: class file path
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        System.out.println(resourceLoader.getResource("/").getURI());

        //base path: project path
        FileSystemResourceLoader resourceLoader1 = new FileSystemResourceLoader();
        System.out.println(resourceLoader1.getResource("/").getURI());
        System.out.println(resourceLoader1.getResource("classpath:").getURI());

        //base path: class file path
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Arrays.stream(patternResolver.getResources("*")).forEach(System.out::println);
    }
}
