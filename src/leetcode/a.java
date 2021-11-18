package leetcode;

public class a{
    public String tree2str(TreeNode node){
        if(node == null)
            return "";
        String left = tree2str(node.left), right = tree2str(node.right), r = node.val + "";
        if(node.right != null)
            r += "(" + left + ")(" + right + ")";
        else if(node.left != null)
            r += "(" + left + ")";
        return r;
    }
}
