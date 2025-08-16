import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /* In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /* In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /* This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.

    @Test
    @DisplayName("is it empty?")
    public void isemptyTest() {
        LinkedListDeque61B<String> list = new LinkedListDeque61B<>();
        assertThat(list.isEmpty()).isTrue();
        LinkedListDeque61B<String> list2 = new LinkedListDeque61B<>();
        list2.addLast("Man!What can I say!");
        assertThat(list2.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("size?")
    public void sizeTest() {
         LinkedListDeque61B<String> list1 = new LinkedListDeque61B<>();
         list1.addFirst("3");
         list1.addFirst("2");
         list1.addFirst("1");
         assertThat(list1.size()).isEqualTo(3);
         LinkedListDeque61B<String> list2 = new LinkedListDeque61B<>();
         assertThat(list2.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("pokemon,get daze!")
    public void getTest() {
        LinkedListDeque61B<String> list1 = new LinkedListDeque61B<>();
        assertThat(list1.get(0)).isNull();
        LinkedListDeque61B<String> list2 = new LinkedListDeque61B<>();
        list2.addLast("a");
        list2.addLast("b");
        list2.addLast("c");
        assertThat(list2.get(0)).isEqualTo("a");
        assertThat(list2.get(1)).isEqualTo("b");
        assertThat(list2.get(2)).isEqualTo("c");
        assertThat(list2.get(-1)).isNull();
        assertThat(list2.get(3)).isNull();
        assertThat(list2.get(999)).isNull();
    }

    @Test
    @DisplayName("pokemon,getRecursive daze!!")
    public void getRecursiveTest() {
        LinkedListDeque61B<String> list1 = new LinkedListDeque61B<>();
        assertThat(list1.getRecursive(0)).isNull();
        LinkedListDeque61B<String> list2 = new LinkedListDeque61B<>();
        list2.addLast("a");
        list2.addLast("b");
        list2.addLast("c");
        assertThat(list2.getRecursive(0)).isEqualTo("a");
        assertThat(list2.getRecursive(1)).isEqualTo("b");
        assertThat(list2.getRecursive(2)).isEqualTo("c");
        assertThat(list2.getRecursive(-1)).isNull();
        assertThat(list2.getRecursive(3)).isNull();
        assertThat(list2.getRecursive(999)).isNull();
    }

    @Test
    @DisplayName("remove first and last")
    public void removeFirstLastTest() {
         LinkedListDeque61B<String> list = new LinkedListDeque61B<>();
         assertThat(list.removeFirst()).isNull();
         assertThat(list.removeLast()).isNull();
         list.addLast("x");
         list.addLast("y");
         list.addLast("z");
         assertThat(list.removeLast()).isEqualTo("y");
         assertThat(list.removeFirst()).isEqualTo("y");
         assertThat(list.get(0)).isEqualTo("y");
     }
}
