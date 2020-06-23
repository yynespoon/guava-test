package com.test.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author lixiaoyu
 * @since 2020/6/19
 */
public class CharacterStreamSpliterator implements Spliterator<Character> {

    private int threshold;

    private String originString;

    private int currentPosition;

    public CharacterStreamSpliterator(String originString, int currentPosition, Integer threshold) {
        this.originString = originString;
        this.currentPosition = currentPosition;
        this.threshold = threshold;
    }

    /**
     * 怎样去分割流中的每个元素 返回 false 时停止解析
     *
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(this.originString.charAt(this.currentPosition ++));
        return this.currentPosition < this.originString.length();
    }

    /**
     * 怎样去分割
     * @return 返回 null 代表不再进行分割, 否则继续分割
     */
    @Override
    public Spliterator<Character> trySplit() {
        if (this.currentPosition <= threshold){
            return null;
        }
        int position = this.currentPosition;
        int len = this.originString.length();
        for (int i = position + threshold; i < len; i++) {
            if(Character.isWhitespace(this.originString.charAt(i))){
                return new CharacterStreamSpliterator(this.originString.substring(i, len), i, threshold);
            }
        }
        return null;
    }

    /**
     * 预估长度
     * @return
     */
    @Override
    public long estimateSize() {
        return this.originString.length();
    }

    /**
     * 分割优化
     * @return
     * ORDERED 并行流中元素需要有序
     * SIZED estimateSize 返回的长度是准确的
     * NONNULL 流中每个元素都不为空
     * IMMUTABLE 流中元素都不可更改
     * SUBSIZED 从spliterator 拆分出来的长度都是准确的
     */
    @Override
    public int characteristics() {
        return ORDERED & SIZED & NONNULL & IMMUTABLE & SUBSIZED;
    }
}
