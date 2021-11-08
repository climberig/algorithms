package leetcode;

public class a {
    public boolean isSubtree(TreeNode root, TreeNode sub) {
        if (root == null)
            return false;
        return sub(root, sub) || isSubtree(root.left, sub) || isSubtree(root.right, sub);
    }

    boolean sub(TreeNode root, TreeNode sub) {
        if (root == null && sub == null)
            return true;
        if (root == null || sub == null)
            return false;
        return root.val == sub.val && sub(root.left, sub.left) && sub(root.right, sub.right);
    }
}

