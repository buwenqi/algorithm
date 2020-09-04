package wenqi.rangeSearch;

import java.util.Arrays;

/**
 * Created by wenqi on 2020/7/6.
 * 线段树
 */
public class SegmentTree {
    public static void main(String[] args){
        int nums[]={1,2,3,4,5,6,7,8,9,10,11,12,13};
        SegmentTree segmentTree=new SegmentTree();
        int[] st=segmentTree.buildSTree(nums);
        System.out.println(Arrays.toString(st));
        System.out.println(segmentTree.rangeSum(0,6,st,nums.length));
        segmentTree.update(5,1,nums,st);
        System.out.println(Arrays.toString(st));
        System.out.println(segmentTree.rangeSum(0,6,st,nums.length));
    }

    public int[] buildSTree(int[] nums){
        int st[]=new int[2*nums.length];
        for(int i=0;i<nums.length;i++){
            //为n->2n-1的叶子节点赋值
            st[i+nums.length]=nums[i];
        }
        for(int i=nums.length-1;i>0;i--){
            //为1-> n-1 赋值
            //左右子孩子的和
            st[i]=st[i<<1]+st[(i<<1)^1];
        }
        return st;
    }

    public void update(int i,int val,int[] nums,int[] st){
        //i是指的nums的下标
        int diff=val-nums[i];
        nums[i]=val;
        i+=nums.length;
        while (i>0){
            //更新每一级的父节点
            st[i]+=diff;
            i=i>>1;
        }
    }

    public int rangeSum(int i,int j,int[] st,int length){
        i+=length;
        j+=length;
        int result=0;
        while (i<=j){
            //终点i==j的时候，i肯定是奇数或者偶数，肯定被算一次
            if((i&1)==1){
                //右节点，需要单独计算，
                // 不能算其父节点（左右子节点的和，父节点包含了左节点），缩减范围置[i+1,j]
                result+=st[i];
                i+=1;
            }
            if((j&1)==0){
                //左节点，此时需要单独计算j,（不能计算其父节点），范围缩减至[i,j-1]
                result+=st[j];
                j-=1;
            }
            //可以用父节点的和代替当前的和，范围缩减至[i父节点，父节点]
            i>>=1;
            j>>=1;
        }
        return result;
    }
}
