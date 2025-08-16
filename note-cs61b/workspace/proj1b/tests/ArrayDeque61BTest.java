import jh61b.utils.Reflection;
import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.util.List;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("Constructor initializes empty deque")
    public void testConstructor() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Add and remove operations")
    public void testAddRemove() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();

        deque.addFirst("front");
        deque.addLast("end");

        assertThat(deque.removeFirst()).isEqualTo("front");
        assertThat(deque.removeLast()).isEqualTo("end");
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Basic addFirst and get")
    public void testAddFirstAndGet() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(10);
        deque.addFirst(20);
        deque.addFirst(30);

        assertThat(deque.get(0)).isEqualTo(30);
        assertThat(deque.get(1)).isEqualTo(20);
        assertThat(deque.get(2)).isEqualTo(10);
        assertThat(deque.get(3)).isNull(); // Out of bounds
    }

    @Test
    @DisplayName("Basic addLast and get")
    public void testAddLastAndGet() {
        ArrayDeque61B<Character> deque = new ArrayDeque61B<>();
        deque.addLast('a');
        deque.addLast('b');
        deque.addLast('c');

        assertThat(deque.get(0)).isEqualTo('a');
        assertThat(deque.get(1)).isEqualTo('b');
        assertThat(deque.get(2)).isEqualTo('c');
        assertThat(deque.get(-1)).isNull(); // Invalid index
    }

    @Test
    @DisplayName("Mixed addFirst and addLast")
    public void testMixedAdd() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // Front: 30, 20, 10
        deque.addFirst(10);
        deque.addFirst(20);
        deque.addFirst(30);
        // Back: 40, 50
        deque.addLast(40);
        deque.addLast(50);

        assertThat(deque.toList()).containsExactly(30, 20, 10, 40, 50).inOrder();
    }

    @Test
    @DisplayName("ToList maintains order")
    public void testToList() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("A");
        deque.addFirst("B");
        deque.addLast("C");
        deque.addFirst("D");

        assertThat(deque.toList()).containsExactly("D", "B", "A", "C").inOrder();
    }

    @Test
    @DisplayName("Resize on add")
    public void testResizeOnAdd() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // Add 9 elements to trigger resize (initial capacity=8)
        for (int i = 0; i < 9; i++) {
            deque.addLast(i);
        }

        assertThat(deque.size()).isEqualTo(9);
        assertThat(deque.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8).inOrder();
    }

    @Test
    @DisplayName("Remove and resize down")
    public void testRemoveAndResizeDown() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // Fill to initial capacity
        for (int i = 0; i < 8; i++) {
            deque.addLast(i);
        }

        // Remove until below 25% (6 items removed, 2 remain)
        for (int i = 0; i < 6; i++) {
            deque.removeFirst();
        }

        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.toList()).containsExactly(6, 7).inOrder();
    }

    @Test
    @DisplayName("Circular behavior test")
    public void testCircularBehavior() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        // Fill and empty partially to wrap around
        deque.addLast(1);
        deque.addFirst(2);
        deque.removeFirst();
        deque.addLast(3);
        deque.addFirst(4);
        deque.removeLast();

        assertThat(deque.toList()).containsExactly(4, 1).inOrder();
    }

    @Test
    @DisplayName("Remove from empty deque")
    public void testRemoveFromEmpty() {
        ArrayDeque61B<Object> deque = new ArrayDeque61B<>();
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.removeLast()).isNull();
    }

    @Test
    @DisplayName("Complex sequence")
    public void testComplexSequence() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(10);
        deque.addLast(20);
        deque.addFirst(5);
        deque.addLast(25);

        assertThat(deque.removeFirst()).isEqualTo(5);
        assertThat(deque.removeLast()).isEqualTo(25);

        deque.addFirst(0);
        deque.addLast(30);

        assertThat(deque.size()).isEqualTo(4);
        assertThat(deque.toList()).containsExactly(0, 10, 20, 30).inOrder();
    }

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() ||
                        f.getType().equals(Object[].class) ||
                        f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }
}