import java.util.List;
import java.util.ArrayList;
public class LinkedListDeque61B<T> implements Deque61B<T> {
    public class Node {
        public T item;
        public Node next;
        public Node prev;
        public Node(T i, Node nn, Node pn) {
            item = i;
            next = nn;
            prev = pn;
        }
    }

    public Node sentinel;
    public int size;
    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node first = new Node(x, sentinel.next, sentinel);
        sentinel.next.prev = first;
        sentinel.next = first;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node last = new Node(x, sentinel, sentinel.prev);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node node = sentinel.next;
        while (node != sentinel) {
            returnList.add(node.item);
            node = node.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        first.prev = null;
        first.next = null;
        size--;
        return sentinel.next.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        last.prev = null;
        last.next = null;
        size--;
        return sentinel.prev.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node node = sentinel.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return recursion(index, sentinel.next);
    }

    private T recursion(int index, Node node) {
        if(index == 0) {
            return node.item;
        } else {
            return recursion(index - 1, node.next);
        }
    }
}

