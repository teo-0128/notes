package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private final Comparator<T> defaultComparator;

    /**
     * 创建一个具有给定比较器的 MaxArrayDeque61B
     *
     * @param c 用于确定最大元素的比较器
     */
    public MaxArrayDeque61B(Comparator<T> c) {
        super(); // 调用父类构造函数
        defaultComparator = c;
    }

    /**
     * 使用默认比较器返回双端队列中的最大元素
     *
     * @return 双端队列中的最大元素，如果为空则返回 null
     */
    public T max() {
        return max(defaultComparator);
    }

    /**
     * 使用指定的比较器返回双端队列中的最大元素
     *
     * @param c 用于确定最大元素的比较器
     * @return 双端队列中的最大元素，如果为空则返回 null
     */
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }

        Iterator<T> iterator = iterator();
        T maxElement = iterator.next();

        while (iterator.hasNext()) {
            T current = iterator.next();
            // 使用提供的比较器比较元素
            if (c.compare(current, maxElement) > 0) {
                maxElement = current;
            }
        }

        return maxElement;
    }
}