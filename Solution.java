import java.util.*;

class Solution {
    List<Integer>[] children;
    List<Integer>[] newTree;
    int[] last;
    int[] newParent;
    int[] size;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        children = new ArrayList[n];
        newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
            newTree[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        last = new int[26];
        Arrays.fill(last, -1);

        newParent = parent.clone();

        // Find new parents using original tree traversal
        dfsChange(0, s);

        // Build modified tree
        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        size = new int[n];

        // Calculate subtree sizes in modified tree
        dfsSize(0);

        return size;
    }

    private void dfsChange(int node, String s) {
        int c = s.charAt(node) - 'a';

        // Save previous ancestor with the same character
        int previous = last[c];

        // If matching ancestor exists, change parent
        if (previous != -1) {
            newParent[node] = previous;
        }

        // Mark current node as the closest ancestor for this character
        last[c] = node;

        // Visit original children
        for (int child : children[node]) {
            dfsChange(child, s);
        }

        // Restore previous ancestor for other branches
        last[c] = previous;
    }

    private int dfsSize(int node) {
        size[node] = 1;

        for (int child : newTree[node]) {
            size[node] += dfsSize(child);
        }

        return size[node];
    }
}
