package wenqi.rangeSearch;

/**
 * Created by wenqi on 2020/7/15.
 * 递归方式建立线段树
 * 以求区间内的最小值为例
 * (包含延迟更新)
 */
public class SegmentTreeRecursion {
    class SegmentTreeNode{
        //当前节点的最小值
        int minValue;
        //当前节点的范围
        int rangeStart;
        int rangeEnd;
        //左右子范围
        SegmentTreeNode leftNode;
        SegmentTreeNode rightNode;

        //区间延迟添加标记
        int addMark=0;

        public SegmentTreeNode() {
            addMark = 0;
            leftNode = null;
            rightNode = null;
        }

        public SegmentTreeNode(int minValue,int rangeStart,int rangeEnd){
            this.minValue=minValue;
            this.rangeStart=rangeStart;
            this.rangeEnd=rangeEnd;
        }
    }

    public SegmentTreeNode buildSegmentTree(int[] nums,int startIndex,int endIndex){
        if(startIndex==endIndex){
            return new SegmentTreeNode(nums[startIndex],startIndex,endIndex);
        }
        SegmentTreeNode rootNode=new SegmentTreeNode();
        int mid=startIndex+(endIndex-startIndex)/2;
        rootNode.leftNode=buildSegmentTree(nums,startIndex,mid);
        rootNode.rightNode=buildSegmentTree(nums,mid+1,endIndex);
        rootNode.minValue=Math.min(rootNode.leftNode.minValue,rootNode.rightNode.minValue);
        rootNode.rangeStart=startIndex;
        rootNode.rangeEnd=endIndex;
        return  rootNode;
    }

    /**
     *查询[qstart,qend]中的最小值
     */
    public int query(SegmentTreeNode root,int qstart,int qend){
        if(root==null || qstart>root.rangeEnd || qend<root.rangeStart){
            //完全没交集,由于求的是最小值，所以一定不能选这个。
            return Integer.MAX_VALUE;
        }
        if(qstart<=root.rangeStart && qend>=root.rangeEnd){
            //查询的区间如果包含当前的root区间的话，就将其最小值返回
            return root.minValue;
        }
        //查询子节点之前将之前的addMark生效
        pushDown(root);
        //包括在其中的时候
        //往左右子树分别查询
        //System.out.println("left:"+root.leftNode.rangeStart+":"+root.rightNode.rangeStart);
        //System.out.println("right:"+root.rightNode.rangeStart+":"+root.rightNode.rangeStart);
        return Math.min(query(root.leftNode,qstart,qend),query(root.rightNode,qstart,qend));
    }

    /**
     *更新某个值nums中index为value值
     */
    public void updateOne(SegmentTreeNode root,int index,int value){
        //System.out.println(root.rangeStart+":"+root.rangeEnd);
        if(root.rangeStart==root.rangeEnd && index==root.rangeStart){
            //是叶子节点，并且是目标节点，更新
            root.minValue=value;
            return;
        }
        //查询子节点之前将之前的addMark生效
        pushDown(root);
        int mid=root.rangeStart+(root.rangeEnd-root.rangeStart)/2;
        if(mid>=index){
            //说明在左子树上
            updateOne(root.leftNode,index,value);
        }else{
            //说明在右子树上
            updateOne(root.rightNode,index,value);
        }
        //最后更新根节点的值
        root.minValue=Math.min(root.leftNode.minValue,root.rightNode.minValue);
    }

    /**
     * 批量将一范围的值加上一个数,
     */
    public void rangeUpdate(SegmentTreeNode root,int start,int end,int addValue){
        if(root==null || end<root.rangeStart || start>root.rangeEnd){
            //不在执行范围内
            return;
        }
        if(start<=root.rangeStart && end>=root.rangeEnd){
            //root区间包括在查询区间内
            //懒更新，直接设置addMark返回
            root.addMark+=addValue;
            root.minValue+=addValue;
            return;
        }
        //将之前有addMark的下行执行
        pushDown(root);
        //对左右子树同时更新
        rangeUpdate(root.leftNode,start,end,addValue);
        rangeUpdate(root.rightNode,start,end,addValue);
    }

    /**
     * 将root的延迟标记同步到其下一层子节点,这个操作延迟到查询执行
     */
    public void pushDown(SegmentTreeNode root){
        if(root!=null && root.addMark!=0){
            if(root.leftNode!=null){
                root.leftNode.addMark+=root.addMark;
                root.leftNode.minValue+=root.addMark;
            }
            if(root.rightNode!=null){
                root.rightNode.addMark+=root.addMark;
                root.rightNode.minValue+=root.addMark;
            }
            //当前的查询标记以修复
            root.addMark=0;
        }
    }

    public static void main(String[] args){
        int[] nums={2,5,1,4,9,3};
        SegmentTreeRecursion segmentTree=new SegmentTreeRecursion();
        SegmentTreeNode root=segmentTree.buildSegmentTree(nums,0,nums.length-1);
        segmentTree.rangeUpdate(root,0,5,2);

        int query1=segmentTree.query(root,1,3);
        int query2=segmentTree.query(root,1,5);
        System.out.println(String.format("query1:%s,query2:%s",query1,query2));
        segmentTree.updateOne(root,2,6);
        int query3=segmentTree.query(root,1,3);
        int query4=segmentTree.query(root,1,5);
        System.out.println(String.format("query3:%s,query4:%s",query3,query4));


    }
}
