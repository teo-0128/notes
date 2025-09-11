import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

public class UnionFindTest {

    /**
     * Checks that the initial state of the disjoint sets are correct (this will pass with the skeleton
     * code, but ensure it still passes after all parts are implemented).
     */
    @Test
    public void initialStateTest() {
        UnionFind uf = new UnionFind(4);
        assertThat(uf.connected(0, 1)).isFalse();
        assertThat(uf.connected(0, 2)).isFalse();
        assertThat(uf.connected(0, 3)).isFalse();
        assertThat(uf.connected(1, 2)).isFalse();
        assertThat(uf.connected(1, 3)).isFalse();
        assertThat(uf.connected(2, 3)).isFalse();
    }

    /**
     * Checks that invalid inputs are handled correctly.
     */
    @Test
    public void illegalFindTest() {
        UnionFind uf = new UnionFind(4);
        try {
            uf.find(10);
            fail("Cannot find an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
        try {
            uf.union(1, 10);
            fail("Cannot union with an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Checks that union is done correctly (including the tie-breaking scheme).
     */
    @Test
    public void basicUnionTest() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        assertThat(uf.find(0)).isEqualTo(1);
        uf.union(2, 3);
        assertThat(uf.find(2)).isEqualTo(3);
        uf.union(0, 2);
        assertThat(uf.find(1)).isEqualTo(3);

        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(8, 9);
        uf.union(4, 8);
        uf.union(4, 6);

        assertThat(uf.find(5)).isEqualTo(9);
        assertThat(uf.find(7)).isEqualTo(9);
        assertThat(uf.find(8)).isEqualTo(9);

        uf.union(9, 2);
        assertThat(uf.find(3)).isEqualTo(9);
    }

    /**
     * Unions the same item with itself. Calls on find and checks that the outputs are correct.
     */
    @Test
    public void sameUnionTest() {
        UnionFind uf = new UnionFind(4);
        uf.union(1, 1);
        for (int i = 0; i < 4; i += 1) {
            assertThat(uf.find(i)).isEqualTo(i);
        }
    }

    /**
     * Write your own tests below here to verify for correctness. The given tests are not comprehensive.
     * Specifically, you may want to write a test for path compression and to check for the correctness
     * of all methods in your implementation.
     */


    @Test
    public void pathCompressionTest() {
        UnionFind uf = new UnionFind(6);
        // Build a chain: 0 -> 1 -> 2 -> 3
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(1, 2); // Now 0,1,2,3 are connected with 3 as root
        // Before path compression, the parent of 0 might be 1, and 1's parent is 2, etc.
        int root = uf.find(0); // This should trigger path compression
        // After find, all nodes along the path should point directly to the root
        assertThat(uf.parent(0)).isEqualTo(root);
        // Verify that the path was compressed by checking the parent of 0 is now the root
        // Also check that the root has a negative value (size information)
        assertThat(uf.parent(root)).isLessThan(0);
    }

    @Test
    public void sizeTest() {
        UnionFind uf = new UnionFind(5);
        // Initial sizes should all be 1
        for (int i = 0; i < 5; i++) {
            assertThat(uf.sizeOf(i)).isEqualTo(1);
        }
        uf.union(0, 1);
        assertThat(uf.sizeOf(0)).isEqualTo(2);
        assertThat(uf.sizeOf(1)).isEqualTo(2);
        uf.union(2, 3);
        assertThat(uf.sizeOf(2)).isEqualTo(2);
        uf.union(0, 2);
        assertThat(uf.sizeOf(0)).isEqualTo(4);
        // Check that all elements in the set have the same size
        assertThat(uf.sizeOf(1)).isEqualTo(4);
        assertThat(uf.sizeOf(3)).isEqualTo(4);
        // The remaining element should still have size 1
        assertThat(uf.sizeOf(4)).isEqualTo(1);
    }

    @Test
    public void multipleUnionsAndFind() {
        UnionFind uf = new UnionFind(10);
        // Perform a series of unions
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(0, 2);
        uf.union(4, 6);
        uf.union(0, 4);

        // Now all elements 0-7 should be connected
        int root = uf.find(0);
        for (int i = 0; i < 8; i++) {
            assertThat(uf.find(i)).isEqualTo(root);
        }
        // Elements 8 and 9 should be separate
        assertThat(uf.find(8)).isEqualTo(8);
        assertThat(uf.find(9)).isEqualTo(9);
        // Verify sizes
        assertThat(uf.sizeOf(0)).isEqualTo(8);
        assertThat(uf.sizeOf(8)).isEqualTo(1);
        assertThat(uf.sizeOf(9)).isEqualTo(1);

        // Connect the last two
        uf.union(8, 9);
        assertThat(uf.sizeOf(8)).isEqualTo(2);
        uf.union(0, 8);
        assertThat(uf.sizeOf(0)).isEqualTo(10);
        for (int i = 0; i < 10; i++) {
            assertThat(uf.find(i)).isEqualTo(root);
        }
    }

}


