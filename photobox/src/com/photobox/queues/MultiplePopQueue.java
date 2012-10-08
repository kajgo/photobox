package com.photobox.queues;

import java.util.ArrayList;
import java.util.List;

public class MultiplePopQueue<T> {

    private List<T> items;

    public MultiplePopQueue(List<T> items) {
        this.items = new ArrayList(items);
    }

    public List<T> popN(int n) {
        List<T> subList = new ArrayList<T>();
        while(items.size() > 0 && subList.size() < n) {
            subList.add(items.remove(items.size()-1));
        }
        return subList;
    }

}
