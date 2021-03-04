package com.test.arithmetic;

import java.util.Random;

/**
 * @author lixiaoyu
 * @since 2021/3/3
 */
public class TestSkipList2 {

     int currentLevel = 0;

    final  int MAX_LEVEL = 16;

    final  Random random = new Random();

    final  Node header = new Node(MAX_LEVEL);

    public static void main(String[] args) {
        TestSkipList2 testSkipList = new TestSkipList2();
        testSkipList.insert(1);
        testSkipList.insert(3);
        testSkipList.insert(3);
        testSkipList.insert(5);
        testSkipList.insert(4);
        testSkipList.insert(2);
        System.out.println(testSkipList.find(15));
        testSkipList.delete(1);
    }

     class Node {
        Node[] forward;
        int value;

        Node(int level){
            this.forward = new Node[level];
        }
    }

     int randomLevel(){
        if(currentLevel == 0) {
            return 1;
        }
        int level = 1;
        while(random.nextInt() % 2 == 0 && level < MAX_LEVEL) {
            level ++;
        }
        return level;
    }

    int find(int value) {
        int result = -1;
        Node tmp = header;
        for (int i = currentLevel - 1; i >= 0 ; i--) {
            while(tmp.forward[i] != null && tmp.forward[i].value <= value) {
                tmp = tmp.forward[i];
            }
        }
        return tmp.value == value ? tmp.value : result;
    }

    void insert(int value) {
        int level = randomLevel() > currentLevel ? ++ currentLevel : currentLevel;
        Node[] update = new Node[level];
        Node node = new Node(level);
        node.value = value;
        for (int i = 0; i < level; i++) {
            update[i] = header;
        }
        for (int i = currentLevel - 1; i >= 0; i--) {
            Node tmp = header;
            while(tmp.forward[i] != null && tmp.forward[i].value < value) {
                tmp = tmp.forward[i];
            }
            if(i < level) {
                update[i] = tmp;
            }
        }
        for (int i = 0; i < update.length; i++) {
            node.forward[i] = update[i].forward[i];
            update[i].forward[i] = node;
        }
        if(level > currentLevel) {
            currentLevel = level;
        }
    }

    void delete(int value) {
        Node tmp = header;
        for (int i = currentLevel - 1; i >=0 ; i--) {
            while(tmp.forward[i] != null && tmp.forward[i].value < value){
                tmp = tmp.forward[i];
            }
            if(tmp.forward[i] != null && tmp.forward[i].value == value) {
                tmp.forward[i] = tmp.forward[i].forward[i];
            }
        }
        int level = currentLevel - 1;
        while(header.forward[level] == null) {
            level --;
        }
        currentLevel = level + 1;
    }
}
