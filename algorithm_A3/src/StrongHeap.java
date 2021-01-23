import java.util.LinkedList;
import java.util.Queue;

public class StrongHeap {
    /**
     * Determines whether the binary tree with the given root node is
     * a "strong binary heap", as described in the assignment task sheet.
     *
     * A strong binary heap is a binary tree which is:
     *  - a complete binary tree, AND
     *  - its values satisfy the strong heap property.
     *
     * @param root root of a binary tree, cannot be null.
     * @return true if the tree is a strong heap, otherwise false.
     * @TimeComplexity O(n), n is the number of nodes.
     * @MemoryComplexity O(n), n is the number of nodes.
     */
    public static boolean isStrongHeap(BinaryTree<Integer> root) {
        // Check if it is complete tree
        if (!new StrongHeap().isComplete(root, 0, new StrongHeap().countNodes(root)))
            return false;

        return new StrongHeap().helper(root);
    }

    /**
     * Determines whether the binary tree with the given root node
     * its values satisfy the strong heap property.
     *
     * @param root root of a binary tree, cannot be null.
     * @return true if its values satisfy the strong heap property,
     * otherwise false.
     * @TimeComplexity O(n), n is the number of nodes.
     * @MemoryComplexity O(m), m is max(nodes of last level, nodes of second last level).
     */
    private boolean helper(BinaryTree<Integer> root) {
        int size;
        BinaryTree<Integer> node;
        Queue<BinaryTree<Integer>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            size = queue.size();

            for (int i = 0; i < size; i++) {
                node = queue.poll();

                if (!node.isLeaf()) {
                    if (node.getLeft() != null) {
                        queue.offer(node.getLeft());
                        if (node.getValue() <= node.getLeft().getValue())
                            return false;
                    }
                }

                if (!node.isLeaf()) {
                    if (node.getRight() != null) {
                        queue.offer(node.getRight());
                        if (node.getValue() <= node.getRight().getValue())
                            return false;
                    }
                }

                if (!new StrongHeap().isSum(node, 0, node.getValue(), 0)) {
                    return false;
                }
            }
        }

        return true;
    }


    /** Recursive method */
//    private boolean helper(BinaryTree<Integer> root) {
//        boolean left;
//        boolean right;
//
//
//        if (!root.isLeaf()) {
//            // check if root has left child
//            if (root.getLeft() != null) {
//                // check if x < parent(x)
//                if (root.getValue() <= root.getLeft().getValue())
//                    return false;
//                // check if left tree is qualified.
//                left = helper(root.getLeft());
//            } else {
//                left = true;
//            }
//
//            // if root has right child
//            if (root.getRight() != null) {
//                // check if x < parent(x)
//                if (root.getValue() <= root.getRight().getValue())
//                    return false;
//                // check if right tree is qualified.
//                right = helper(root.getRight());
//            } else {
//                right = true;
//            }
//
//            // Check if x + parent(x) < parent(parent(x))
//            if (!new StrongHeap().isSum(root, 0, root.getValue(), 0)) {
//                return false;
//            }
//        } else {
//            return true;
//        }
//
//        return left && right;
//    }

    /**
     * Determines whether the current root satisfies root > root.child + root.grandchild.
     *
     * @param root node of a binary tree.
     * @param cur current sum of its root.child + root.grandchild
     * @param value root value
     * @param level current level the root is in (0 from beginning)
     * @return true if root > root.child + root.grandchild.
     * @TimeComplexity O(1).
     * @MemoryComplexity O(1).
     */
    private boolean isSum(BinaryTree<Integer> root, int cur, int value, int level) {
        boolean left = false;
        boolean right = false;

        // return comparison when reaching grandchild
        if (level >= 2) return (cur < value);

        if (root.isLeaf()) {
            // return true if doesn't have grandchild
            if (level <= 1)
                return true;
        } else {
            // if root has left child
            if (root.getLeft() != null) {
                // check if left tree is qualified recursively
                left = isSum(root.getLeft(), cur + root.getLeft().getValue(), value, ++level);
            } else {
                left = true;
            }

            //if root has right child
            if (root.getRight() != null) {
                // check if right tree is qualified recursively
                right = isSum(root.getRight(), cur + root.getRight().getValue(), value, ++level);
            } else {
                right = true;
            }
        }

        // return true if left and right tree are qualified for x + parent(x) < parent(parent(x))
        return (left && right);
    }

    /**
     * Determines whether the current root satisfies root > root.child + root.grandchild.
     *
     * @param root node of a binary tree, can not be null.
     * @param index assigned index if the node is stored in array.
     * @param nodesNum total numbers of nodes in binary tree.
     * @return true if root > root.child + root.grandchild.
     * @TimeComplexity O(n), n is the number of nodes.
     * @MemoryComplexity O(n), n is the number of nodes
     */
    private boolean isComplete(BinaryTree<Integer> root, int index, int nodesNum) {
        if (root == null)
            return true;

        // If index is greater than number of nodes,
        // it indicates that it is not a complete tree.
        if (index >= nodesNum)
            return false;

        // Recursively check its left and right subtree.
        return (isComplete(root.getLeft(), 2 * index + 1, nodesNum)
                && isComplete(root.getRight(), 2 * index + 2, nodesNum));
    }

    /**
     * Calculate the total numbers of nodes in BST.
     *
     * @param root node of a binary tree, can not be null.
     * @return the total number of nodes in binary search tree.
     * @TimeComplexity O(n), n is the number of nodes.
     * @MemoryComplexity O(n), n is the number of nodes.
     */
    private int countNodes(BinaryTree<Integer> root) {
        if (root == null) return 0;
        return (1+ countNodes(root.getLeft()) + countNodes(root.getRight()));
    }
}
