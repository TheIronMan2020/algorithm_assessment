import java.util.Comparator;

/**
 * A comparator for Binary Trees.
 */
public class BinaryTreeComparator<E extends Comparable<E>> implements Comparator<BinaryTree<E>> {

    /**
     * Compares two binary trees with the given root nodes.
     *
     * Two nodes are compared by their left childs, their values, then their right childs,
     * in that order. A null is less than a non-null, and equal to another null.
     *
     * @param tree1 root of the first binary tree, may be null.
     * @param tree2 root of the second binary tree, may be null.
     * @return -1, 0, +1 if tree1 is less than, equal to, or greater than tree2, respectively.
     * @TimeComplexity O(n) where n is the tree contains the least nodes n.
     * @MemoryComplexity O(logn)
     */
    @Override
    public int compare(BinaryTree<E> tree1, BinaryTree<E> tree2) {
        int left, right;

        if (tree1 == null || tree2 == null) {
            if (tree1 == null && tree2 != null) {
                return -1;
            } else if (tree2 == null && tree1 != null) {
                return 1;
            } else if (tree1 == null && tree2 == null) {
                return 0;
            }
        }

        // first compare its left subtree (inorder)
        left = compare(tree1.getLeft(), tree2.getLeft());
        // if left subtree are identical
        if (left == 0) {
            // if two root value are equal, compare its right subtree
            if (tree1.getValue().compareTo(tree2.getValue()) == 0) {
                // finally compare its right subtree
                right = compare(tree1.getRight(), tree2.getRight());
                if (right != 0) {
                    return right;
                }
            } else if (tree1.getValue().compareTo(tree2.getValue()) > 0) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return left;
        }
        // two trees are identical
        return 0;
    }

}
