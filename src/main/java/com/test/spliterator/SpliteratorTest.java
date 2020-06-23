package com.test.spliterator;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author lixiaoyu
 * @since 2020/6/19
 */
public class SpliteratorTest {

    public static void main(String[] args) {
        String str = "asdfs adsf we fwef wr csadf aefae aefawe faesf afeaf asefawef asfeafa afeafsdfas";

        Stream<Character> stream = StreamSupport.stream(new CharacterStreamSpliterator(str, 0, 10), true);
        CharacterCounter reduce = stream.reduce(new CharacterCounter(0, true), CharacterCounter::accumulate, CharacterCounter::combine);
        System.out.println(reduce);
    }
}
