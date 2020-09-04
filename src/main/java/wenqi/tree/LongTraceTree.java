package wenqi.tree;

import java.util.Stack;

/**
 * Created by wenqi on 2018/5/27.
 */
public class LongTraceTree {
    //输出信息
    public static Stack<TreeNode> longestTrace;
    public static long longestLength=0;

    public static void main(String[] args){
        TreeNode root=new TreeNode(1);
        TreeNode left1=new TreeNode(2);
        TreeNode right1=new TreeNode(3);
        root.left=left1;
        root.right=right1;
        left1.right=new TreeNode(4);
        right1.right=new TreeNode(6);
        TreeNode left2=new TreeNode(5);
        right1.left=left2;
        left2.right=new TreeNode(7);

        findLongestTrace(root,0,new Stack<TreeNode>());
        System.out.println(longestLength);
        System.out.println(longestTrace.toString());
    }

    public static void findLongestTrace(TreeNode root,long length,Stack<TreeNode> trace){
        if(root==null)
            return;
        if(isLeaf(root)){
            length++;
            trace.push(root);
            if(length>longestLength){
                longestTrace=(Stack<TreeNode>) trace.clone();
                longestLength=length;
            }
            length--;
            trace.pop();
        }
        //非叶子节点
        if(root.left!=null){
            length++;
            trace.push(root);
            findLongestTrace(root.left,length,trace);
            length--;
            trace.pop();
        }

        if(root.right!=null){
            length++;
            trace.push(root);
            findLongestTrace(root.right,length,trace);
            length--;
            trace.pop();
        }

    }

    public static boolean isLeaf(TreeNode root){
        if(root.left==null&&root.right==null){
            return true;
        }else{
            return false;
        }
    }
}
class TreeNode{
    public int data;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int data){
        this.data=data;
    }

    @Override
    public String toString() {
        return "data"+data;
    }
}
