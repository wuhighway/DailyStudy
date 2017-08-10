package com.highway.study.anim;


import android.animation.TypeEvaluator;

/**
 * @author JH
 * @date 2017/8/9
 */


public class CharEvaluator implements TypeEvaluator<Character> {
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int start = (int)startValue;
        int end = (int)endValue;
        int current = (int) (start + fraction * (end - start));
        char result = (char)current;
        return result;
    }
}
