package com.webservicepizzariazeff.www.util;

import java.util.function.BiPredicate;

public class Validator {

    private Validator() {
    }

    public static <E> boolean checkThatStringIsNotAEnum(String valueToBeValidated, E[] elements, BiPredicate<E, String> behavior) {

        for (E element : elements) {

            if (behavior.test(element, valueToBeValidated)) {
                return false;
            }
        }

        return true;
    }
}
