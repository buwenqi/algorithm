package wenqi.rangeSearch;

import java.util.Arrays;

/**
 * Created by wenqi on 2020/7/6.
 *树状数组
 */
public class BitTree {

    public static void main(String[] args){
        int nums[]={1,2,3,4,5,6,7,8,9,10,11,12,13};
        //从下标1开始存放
        //bitTree[i]代表着从[i-lowbit[i]+1]的值
        int bitTree[]=new int[nums.length+1];
        BitTree bitTreeObject=new BitTree();
        bitTreeObject.buildBitTreeMethod1(nums,bitTree);
        System.out.println(Arrays.toString(bitTree));

        int bitTree2[]=new int[nums.length+1];
        bitTreeObject.buildBitTreeMethod2(nums,bitTree2);
        System.out.println(Arrays.toString(bitTree2));

        //计算2到7之间的和，应该是sum(7)-sum(1)
        int i=1;
        int j=7;
        System.out.println("i->j:"+bitTreeObject.getRangeSum(i,j,bitTree));
        bitTreeObject.update(6,1,bitTree,nums);
        System.out.println("i->j:"+bitTreeObject.getRangeSum(i,j,bitTree));

    }

    //下标从1开始
    public int getRangeSum(int i,int j,int[] bitTree){
        int endSum=getSum(j,bitTree);
        //函数计算的是下标1到i的和，范围的i-j应该包括i，所以应该算i-1的和
        int beginSum=0;
        if(i>1){
            beginSum=getSum(i-1,bitTree);
        }
        return endSum-beginSum;

    }


    /**
     * i从1开始计算
     */
    public int getSum(int i,int[] bitTree){
        int sum=0;
        while(i>0){
            //加上从(i-lowbit[i]+1，i)的值
            sum+=bitTree[i];
            //更新i到i-lowbit[i],下一轮是计算(xx,i-lowbit(i))范围的和
            i=i-lowBit(i);
        }
        return sum;
    }

    /**
     * 这里i也从1开始
     */
    public void update(int i,int value,int[] bitTree,int[] nums){
        int diff=value-nums[i-1];
        nums[i-1]=value;
        //更新(i-lowbit(i),i),即bitTree[i]的值，
        // 及其上层节点（i+lowbit(i)）的值
        while(i<bitTree.length){
            bitTree[i]+=diff;
            i=i+lowBit(i);
        }
    }

    /**
     * 更新的值假设初始均为0，需要更新为nums对应的值
     */
    public void updateForBuildTree(int i,int value,int[] bitTree,int[] nums){
        int diff=value-0;

        //更新(i-lowbit(i),i),即bitTree[i]的值，
        // 及其上层节点（i+lowbit(i)）的值
        while(i<bitTree.length){
            bitTree[i]+=diff;
            i=i+lowBit(i);
        }
    }

    /**
     * 建树时间复杂度为nlog(n)
     * @param nums
     * @param bitTree
     */
    public void buildBitTreeMethod1(int[] nums,int[] bitTree){
        for(int i=0;i<bitTree.length;i++){
            bitTree[i]=0;
        }

        for(int i=1;i<bitTree.length;i++){
            updateForBuildTree(i,nums[i-1],bitTree,nums);
        }
    }

    /**
     * 建树时间复杂度为O(n)
     * @param nums
     * @param bitTree
     */
    public void buildBitTreeMethod2(int[] nums,int[] bitTree){
        for(int i=1;i<bitTree.length;i++){
            bitTree[i]=nums[i-1];
        }

        for(int i=1;i<bitTree.length;i++){
            //更新其上级的值，将自己的影响提交给上级，上级的影响自己提交
            int j=i+lowBit(i);
            if(j<bitTree.length) bitTree[j]=bitTree[j]+bitTree[i];
        }
    }

    private int lowBit(int i){
        return i&(-i);
    }
}
