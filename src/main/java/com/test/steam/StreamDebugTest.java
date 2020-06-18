package com.test.steam;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author lixiaoyu
 * @since 2020/6/13
 */
public class StreamDebugTest {

    public static void main(String[] args) {
        Stream.iterate(new int[]{0, 1}, element -> new int[]{element[1], element[0] + element[1]}).limit(10)
                .forEach(element -> System.out.println("(" + element[0] + ", " + element[1] + ")"));

        Stream.generate(System::currentTimeMillis).limit(10).forEach(System.out::println);

        try (Stream<String> lines = Files.lines(Paths.get("D:\\code\\guava-test\\src\\main\\java\\com\\test\\encrypt\\AESGCMArithmeticTest.java"))){
            System.out.println(lines.filter(line -> line.contains("public")).findFirst().orElse("aaa"));
        } catch (IOException e){

        }


        IntSummaryStatistics collect = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7).stream().collect(Collectors.summarizingInt(a -> a));
        System.out.println("--" + collect.getAverage() + "--" + collect.getCount() + "--" + collect.getMax() + "--" + collect.getMin() + "--" + collect.getSum());

        IntStream.rangeClosed(0, 100000).boxed().parallel().collect(Collectors.reducing(0, a -> a, Integer::sum));

        Integer collect1 = IntStream.rangeClosed(0, 10000).boxed().collect(Collectors.collectingAndThen(Collectors.maxBy(Integer::compareTo), Optional::get));
        System.out.println(collect1);
        
    }
}
