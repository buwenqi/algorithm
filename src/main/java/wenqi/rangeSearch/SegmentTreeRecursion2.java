package wenqi.rangeSearch;
import java.util.*;

/**
 * Created by wenqi on 2020/7/16.
 */
public class SegmentTreeRecursion2 {
    //使用线段树解答，涉及知识包括线段树的离散化+动态添加计数
    class SegmentTreeNode{
        SegmentTreeNode leftNode;
        SegmentTreeNode rightNode;
        int rangeStart;
        int rangeEnd;
        //用于记录当前范围的数量
        int count;

        public SegmentTreeNode(){
            count=0;
        }

        public SegmentTreeNode(int count,int rangeStart,int rangeEnd){
            this.count=count;
            this.rangeStart=rangeStart;
            this.rangeEnd=rangeEnd;
        }
    }
    //离散化存储，key是nums[i]的值，value是nums[i]排序后所在的位置，从0开始
    Map<Integer,Integer> hashMap;

    SegmentTreeNode buildInitTree(int[] nums){
        if(nums==null || nums.length==0){
            return null;
        }
        SortedSet<Integer> set=new TreeSet<>();
        for(int i=0;i<nums.length;i++){
            set.add(nums[i]);
        }
        int uniqueSize=set.size();
        hashMap=new HashMap(uniqueSize+1);
        int index=0;
        for(int setItem:set){
            //System.out.println(setItem);
            hashMap.put(setItem,index);
            index++;
        }
        return constructInitTree(0,uniqueSize-1);
    }

    SegmentTreeNode constructInitTree(int startIndex,int endIndex){
        SegmentTreeNode root=new SegmentTreeNode(0,startIndex,endIndex);
        if(startIndex==endIndex){
            return root;
        }
        int mid=startIndex+(endIndex-startIndex)/2;
        root.leftNode=constructInitTree(startIndex,mid);
        root.rightNode=constructInitTree(mid+1,endIndex);
        //所有的值初始化都为0，需要动态更新计数
        return root;
    }

    //计算index右侧的最小值，直接统计线段树0-index的数量就是,index为hash范围值
    int countRange(SegmentTreeNode root,int qstart,int qend){
        if(qstart>root.rangeEnd || qend<root.rangeStart){
            //完全没交集
            return 0;
        }
        if(qstart<=root.rangeStart && qend>=root.rangeEnd){
            //包含的状态
            return root.count;
        }

        //在左右子树查找
        int leftCount=countRange(root.leftNode,qstart,qend);
        int rightCount=countRange(root.rightNode,qstart,qend);
        System.out.println("leftCount:"+leftCount+",rightCount:"+rightCount);
        return leftCount+rightCount;
    }

    //对index指向的范围数量加1
    void insert(SegmentTreeNode root, int index){
        if(root==null || root.rangeStart>index || root.rangeEnd<index){
            //不包含
            return;
        }
        if(root.rangeStart<=index && root.rangeEnd>=index){
            root.count+=1;
        }

        insert(root.leftNode,index);
        insert(root.rightNode,index);
    }

    public List<Integer> countSmaller(int[] nums) {
        //典型的区间查询,维护区间内的
        SegmentTreeNode root=buildInitTree(nums);
        Integer[] resultArr=new Integer[nums.length];
        for(int i=nums.length-1;i>=0;i--){
            //倒序更新计数
            //查询小于等于的值，应该在0-i的hashindex-1范围内找
            System.out.println(hashMap.get(nums[i])-1);
            resultArr[i]=countRange(root,0,hashMap.get(nums[i])-1);
            System.out.println("--------------");
            insert(root,hashMap.get(nums[i]));
        }
        return Arrays.asList(resultArr);
    }

    public static void main(String[] args){
        int[] result={1,2,7,8,5};
        SegmentTreeRecursion2 sg=new SegmentTreeRecursion2();
        List<Integer> reslut=sg.countSmaller(result);
    }


}
