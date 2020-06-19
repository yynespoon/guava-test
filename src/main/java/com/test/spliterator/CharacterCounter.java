package com.test.spliterator;

/**
 * @author lixiaoyu
 * @since 2020/6/19
 */
public class CharacterCounter {

    private int count;

    private boolean isWhiteSpace;

    public CharacterCounter(int count, boolean isWhiteSpace) {
        this.count = count;
        this.isWhiteSpace = isWhiteSpace;
    }

    public CharacterCounter accumulate(char c){
        if(Character.isWhitespace(c)){
            return this.isWhiteSpace ?
                    this:
                    new CharacterCounter(count, true);
        } else {
            return this.isWhiteSpace ?
                    new CharacterCounter(count, true) :
                    new CharacterCounter(count + 1, false);
        }
    }

    public int combine(CharacterCounter before, CharacterCounter after){
        return before.count + after.count;
    }
}
