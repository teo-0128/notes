public class UnionFind {
    private int[] parent;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int root = find(v);
        return -parent[root];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Invalid vertex: " + v);
        }
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Invalid vertex: " + v);
        }
        int root = v;
        // Find the root
        while (parent[root] >= 0) {
            root = parent[root];
        }
        // Path compression: set the parent of all nodes along the path to the root
        int current = v;
        while (current != root) {
            int next = parent[current];
            parent[current] = root;
            current = next;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return;
        }
        int size1 = -parent[root1];
        int size2 = -parent[root2];
        if (size1 <= size2) {
            // Merge root1 into root2
            parent[root2] -= size1;
            parent[root1] = root2;
        } else {
            // Merge root2 into root1
            parent[root1] -= size2;
            parent[root2] = root1;
        }
    }
}