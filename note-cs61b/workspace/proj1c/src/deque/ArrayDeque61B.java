package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    static final int INIT = 8;
    private int size;
    private T[] items;
    private int p1;
    private int p2;

    public ArrayDeque61B() {
        items = (T[]) new Object[INIT];
        size = 0;
        p1 = 0;
        p2 = 1;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int current = Math.floorMod(p1 + 1, items.length);
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(current + i) % items.length];
        }
        items = newItems;
        p1 = capacity - 1;
        p2 = size;
    }

    @Override
    public void addFirst(T x) {
        if (items.length == size) resize(size * 2);
        size++;
        items[p1] = x;
        p1 = Math.floorMod(p1 - 1, items.length);
    }

    @Override
    public void addLast(T x) {
        if (items.length == size) resize(size * 2);
        size++;
        items[p2] = x;
        p2 = Math.floorMod(p2 + 1, items.length);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        return items[Math.floorMod(index + p1 + 1, items.length)];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index0 = Math.floorMod(p1 + 1, items.length);
        for (int i = 0; i < size; i++) {
            returnList.add(items[(index0 + i) % items.length]);
        }
        return returnList;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) return null;
        int index = Math.floorMod(p1 + 1, items.length);
        T removed = items[index];
        items[index] = null;
        p1 = Math.floorMod(p1 + 1, items.length);
        size--;
        if (size > 0 && size < items.length / 4) {
            resize(items.length / 2);
        }
        return removed;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int index = Math.floorMod(p2 - 1, items.length);
        T removed = items[index];
        items[index] = null;
        p2 = Math.floorMod(p2 - 1, items.length);
        size--;
        if (size > 0 && size < items.length / 4) {
            resize(items.length / 2);
        }
        return removed;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T> {
        private int currentIndex;
        public ArrayDeque61BIterator() {
            currentIndex = 0;
        }
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }
        @Override
        public T next() {
            if (hasNext()) {
                T returnItem = get(currentIndex);
                currentIndex++;
                return returnItem;
            } else {
                return null;
            }
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if  (!(obj instanceof ArrayDeque61B other)) {
            return false;
        }
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            T thisItem = this.get(i);
            Object otherItem = other.get(i);
            if (thisItem == null) {
                if (otherItem != null) return false;
            } else {
                if (!thisItem.equals(otherItem)) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String x = "[";
        for (int i = 0; i < this.size; i++) {
            if (i == this.size - 1) {
                x += this.get(i);
                break;
            }
            x += this.get(i) + ", ";
        }
        x += "]";
        return x;
    }
}