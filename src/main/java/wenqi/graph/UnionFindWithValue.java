package wenqi.graph;

/**
 * Created by wenqi on 2020/9/19.
 *
 * 带权并查集，用于求解同一连通集中任意两点之间的权重
 * 存储：
 *  1. 图中节点数nodeSize
 *  2. 根节点集合：root[]
 *  3. 当前节点的到根节点的权重：values[], values[i]代表i到i root的权重
 *
 *  参考：https://www.cnblogs.com/zhxmdefj/p/11117791.html
 *      https://www.zybuluo.com/moyujiang/note/1531702
 */
public class UnionFindWithValue {

    int nodeSize;
    int[] root;
    int[] values;


    public int findPathWeight(int x,int y){
        //找任意两点之间的权重
        int xroot=findRoot(x);
        int yroot=findRoot(y);
        if(xroot==yroot){
            //x->y的权重可以表示为x到root，然后root退回到y(-values[y]),即values[x]-values[y]
            return values[x]-values[y];
        }else{
            return -1;
        }
    }

    public void init(int nodeSize){
        this.nodeSize=nodeSize;
        root=new int[nodeSize];
        values=new int[nodeSize];
        for(int i=0;i<nodeSize;i++){
            root[i]=-1;
            //求和的话应该初值为0,如果是其他定义的话应该变动
            values[i]=0;
        }
    }
    public void merge(int x,int y,int xyweight){
        int xroot=findRoot(x);
        int yroot=findRoot(y);
        if(xroot!=yroot){
            root[xroot]=yroot;
            //xroot到yroot的路径变为xroot退回到x(-value[x]),然后x->y->yroot
            //此处xroot的子节点并没有更新权值，需要在findRoot的时候延迟更新
            values[xroot]=xyweight+values[y]-values[x];
        }
    }

    public int findRoot(int x){
        if(root[x]==-1) return x;
        //带权需要获取原来x到root的权值
        int old_root_index=root[x];
        int xroot=findRoot(x);
        //如果root的权重被更新的话会递归更新对应的子节点权值
        values[x]+=values[old_root_index];
        //路径压缩相关
        root[x]=xroot;
        return xroot;
    }


}
