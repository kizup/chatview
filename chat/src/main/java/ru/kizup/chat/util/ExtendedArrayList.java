package ru.kizup.chat.util;

import java.util.ArrayList;

/**
 * Created by kizup on 23.09.17.
 */

public class ExtendedArrayList<E> extends ArrayList<E> {

    public int lastPosition() {
        return size() - 1;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return get(0);
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return get(lastPosition());
    }

}
