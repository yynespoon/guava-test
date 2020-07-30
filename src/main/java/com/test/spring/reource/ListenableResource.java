package com.test.spring.reource;

import com.sun.nio.file.SensitivityWatchEventModifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lixiaoyu
 * @since 2020/7/30
 */
public class ListenableResource {

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Properties propertiesResource;

    private Resource resource;

    public ListenableResource() throws IOException, InterruptedException {
        this.propertiesResource = initProperties();
        initWatchDog();
    }

    private void initWatchDog() throws IOException, InterruptedException {
        FileSystem defaultFileSystem = FileSystems.getDefault();
        WatchService watchService = defaultFileSystem.newWatchService();
        Path listenedFilePath = resource.getFile().getParentFile().toPath();
        listenedFilePath.register(watchService, new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_MODIFY}, SensitivityWatchEventModifier.HIGH);
        while (true) {
            WatchKey take = watchService.take();
            try {
                List<WatchEvent<?>> watchEvents = take.pollEvents();
                for (WatchEvent<?> watchEvent : watchEvents) {
                    if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        Path context = (Path) watchEvent.context();
                        System.out.println(context.getFileName());
                    }
                }
            } finally {
                take.reset();
            }

        }


    }

    private Properties initProperties() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/META-INF/person.properties");
        this.resource = resource;
        EncodedResource encodedResource = new EncodedResource(resource, "GBK");
        Properties properties = new Properties();
        properties.load(encodedResource.getReader());
        System.out.println(properties.getProperty("person.username"));
        return properties;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new ListenableResource();
    }
}
