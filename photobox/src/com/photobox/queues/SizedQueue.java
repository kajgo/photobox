package com.photobox.queues;

import java.util.ArrayList;
import java.util.List;

public class SizedQueue<T> {

    private int maxSize;
    private List<T> items;

    public SizedQueue(int maxSize) {
        this.maxSize = maxSize;
        this.items = new ArrayList<T>();
    }

    public T enqueue(T item) {
        T lastItem = null;
        if (items.size() == maxSize) {
            lastItem = items.remove(items.size() - 1);
        }
        items.add(0, item);
        return lastItem;
    }

    public int numFreeSlots() {
        return maxSize - items.size();
    }

    public void remove(T item) {
        items.remove(item);
    }

    public boolean isFull() {
        return numFreeSlots() == 0;
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

}
