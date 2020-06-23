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

    public CharacterCounter accumulate(Character c){
        if(Character.isWhitespace(c)){
            return this.isWhiteSpace ?
                    this:
                    new CharacterCounter(count, true);
        } else {
            return this.isWhiteSpace ?
                    new CharacterCounter(count + 1, false) :
                    new CharacterCounter(count, false);
        }
    }

    public CharacterCounter combine(CharacterCounter before){
        return new CharacterCounter(this.count + before.count, this.isWhiteSpace);
    }

    @Override
    public String toString() {
        return "CharacterCounter{" +
                "count=" + count +
                ", isWhiteSpace=" + isWhiteSpace +
                '}';
    }
}
