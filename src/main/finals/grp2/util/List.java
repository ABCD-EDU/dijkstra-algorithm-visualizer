package main.finals.grp2.util;

import java.util.NoSuchElementException;

public interface List<E> {
    int getSize();

    void insert(E data) throws ListOverflowException;

    E getElement(E data) throws NoSuchElementException;

    E getElement(int pos) throws NoSuchElementException;

    boolean delete(E data);

    boolean search(E data);
}

