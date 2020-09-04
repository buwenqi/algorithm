package wenqi.rangeSearch;
import java.util.*;

/**
 * Created by wenqi on 2020/7/20.
 */
class SegmentTreeUseCase {

    class SegmentNode{
        int count;
        //rangeStart是用来记录下标的
        int rangeStart;
        int rangeEnd;
        SegmentNode leftNode;
        SegmentNode rightNode;

        public SegmentNode(int rangeStart,int rangeEnd,int count){
            this.rangeStart=rangeStart;
            this.rangeEnd=rangeEnd;
            this.count=count;
        }
    }

    public static void main(String[] args){
        SegmentTreeUseCase segmentTreeUseCase=new SegmentTreeUseCase();
        int[] nums=new int[]{0};
        System.out.println(segmentTreeUseCase.countRangeSum(nums,0,0));
        int val=2147483647;
        long result=(long)val+1;
        System.out.println(result);
    }

    //直接用线段树查询
    int result=0;
    public int countRangeSum(int[] nums, int lower, int upper) {
        if(nums==null || nums.length==0 || lower>upper){
            return 0;
        }

        int[] preSum=new int[nums.length+1];
        int pre=0;
        //统计前缀和数组，并且排序
        for(int i=1;i<preSum.length;i++){
            preSum[i]=pre+nums[i-1];
            pre=preSum[i];
        }

        int[] preSumCopy=new int[preSum.length+1];
        System.arraycopy(preSum,0,preSumCopy,0,preSum.length);
        Arrays.sort(preSumCopy);

        Map<Integer,Integer> map=new HashMap<>();
        //从0开始放
        int index=0;
        for(int i=0;i<preSumCopy.length;i++){
            if(!map.containsKey(preSumCopy[i])){
                map.put(preSumCopy[i],index++);
            }
        }

        SegmentNode root=initTree(0,map.size()-1);
        int result=0;
        System.out.println("hello");
        for(int i=0;i<preSum.length;i++){
            int leftRange=preSum[i]-upper;
            int rightRange=preSum[i]-lower;
            System.out.println("leftRange:"+leftRange+",rightRange:"+rightRange);
            //寻找leftRange的上界，第一个大于等于leftRange的值
            int leftIndex=findMethod1(preSumCopy,leftRange);
            //寻找rightRange的下届，小于等于rightRange的
            int rightIndex=findMethod2(preSumCopy,rightRange);
            System.out.println("leftIndex:"+leftIndex+",rightIndex:"+rightIndex);
            result+=query(root,leftIndex,rightIndex);
            System.out.println(result);
            update(root,map.get(preSum[i]));
        }

        return result;

    }

    SegmentNode initTree(int rangeStart,int rangeEnd){
        if(rangeStart==rangeEnd){
            return new SegmentNode(rangeStart,rangeEnd,0);
        }
        SegmentNode root=new SegmentNode(rangeStart,rangeEnd,0);
        int mid=rangeStart+(rangeEnd-rangeStart)/2;
        root.leftNode=initTree(rangeStart,mid);
        root.rightNode=initTree(mid+1,rangeEnd);
        return root;
    }

    int findMethod1(int[] preSum,int val){
        int low=0;
        int high=preSum.length-1;
        while(low<=high){
            int mid=low+(high-low)/2;
            if(preSum[mid]==val){
                high=mid-1;
            }else if(preSum[mid]>val){
                high=mid-1;
            }else{
                low=mid+1;
            }
        }

        return low;
    }

    int findMethod2(int[] preSum,int val){
        int low=0;
        int high=preSum.length-1;
        while(low<=high){
            int mid=low+(high-low)/2;
            if(preSum[mid]==val){
                low=mid+1;
            }else if(preSum[mid]>val){
                high=mid-1;
            }else{
                low=mid+1;
            }
        }

        return high;
    }

    void update(SegmentNode root,int index){

        if(root==null || root.rangeStart>index || root.rangeEnd<index){
            return;
        }else{
            root.count++;
        }

        int mid=root.rangeStart+(root.rangeEnd-root.rangeStart)/2;
        if(index<=mid){
            //包含在左边
            update(root.leftNode,index);
        }else{
            //往右子树上插入
            update(root.rightNode,index);
        }
    }

    int query(SegmentNode root,int qstart,int qend){

        if(root==null || root.rangeStart>qend || root.rangeEnd<qstart){
            return 0;
        }

        //root的范围包含在qstart和qend中
        if(qstart<=root.rangeStart && qend>=root.rangeEnd){
            return root.count;
        }

        int leftResult=query(root.leftNode,qstart,qend);
        int rightResult=query(root.rightNode,qstart,qend);
        return leftResult+rightResult;
    }


}