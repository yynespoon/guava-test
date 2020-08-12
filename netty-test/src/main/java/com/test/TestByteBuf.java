package com.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * @author lixiaoyu
 * @since 2020-08-12 23:58
 */
public class TestByteBuf {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        ByteBuf byteBuf = buffer.writeBytes(new byte[]{1, 2, 3, 4, 5, 6});
        // (ridx: 0, widx: 6, cap: 10)
        System.out.println(byteBuf);

        // 1 2 3 4 5 6 0 0 0 0
        byte[] array = byteBuf.array();
        for (byte b : array) {
            System.out.printf(b + " ");
        }
        System.out.println();

        // 6
        System.out.println(byteBuf.readableBytes());

        // (ridx: 1, widx: 6, cap: 10)
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf);

        // 移除读过的数据并且将 readIndex = 0 , writeIndex = oldWriteIndex - oldReadIndex
        // (ridx: 0, widx: 5, cap: 10)
        byteBuf.discardReadBytes();
        System.out.println(byteBuf);

        // copyOnWrite
        // buf.copy(buf.readerIndex(), buf.readableBytes())
        ByteBuf copy = byteBuf.copy();
        System.out.println(copy);

        // 数据共享 指针不共享
        ByteBuf duplicate = byteBuf.duplicate();
        duplicate.writeBytes(new byte[]{1,2,3,4,5,6,7});
        System.out.println(duplicate);
        System.out.println(byteBuf);
    }
}
