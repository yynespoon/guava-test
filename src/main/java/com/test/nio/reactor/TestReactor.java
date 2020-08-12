package com.test.nio.reactor;

import java.io.IOException;

/**
 * @author lixiaoyu
 * @since 2020-08-12 00:00
 */
public class TestReactor {

    public static void main(String[] args) throws IOException {
        Acceptor.start();
    }
}
