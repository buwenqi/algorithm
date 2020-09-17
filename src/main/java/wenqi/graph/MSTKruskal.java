package wenqi.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Kruskal Algorithm: Edge expanding
 * Kruskal算法，使用边扩展
 * 存储：
 *  1.边集合，2. 节点数量nodeSize，3并查集数组union,4.结果集TreeResult
 * 算法过程：
 *  1.将所有的边edges按从小到大排列，并初始化并查集union
 *  2.遍历edges至 edges末尾 或者 TreeResult.size=node的数量
 *      2.1 当前edge(u,v,weight),
 *      2.2 如果 (union,u,v)是联通的，则跳过
 *      2.3 如果不是联通的，将edge加入TreeResult,并且将edge的所有节点放入union
 *  3. 如果TreeResult.size==node的数量，则返回
 *      否则为连通，应该返回空集合
 *
 * @author buwenqi
 * @date 2020/9/17
 */
public class MSTKruskal {

    class Edge{
        public int u;
        public int v;
        public int weight;
    }


    public List<Edge> getMinCostTree(List<Edge> graph,int nodeSize){
        //假设node从0开始计算，Edge中的u，v即记录此信息
        List<Edge> treeResult=new ArrayList<>();
        int[] unionFind=new int[nodeSize];
        initUnionFind(unionFind);
        //边集合从小到大排序
        Collections.sort(graph,(edge1,edge2)->edge1.weight-edge2.weight);
        for(Edge edge: graph){
            if(treeResult.size()==nodeSize-1) break;
            int root1=findRoot(unionFind,edge.u);
            int root2=findRoot(unionFind,edge.v);
            if(root1==root2) {
                //处于同一集合，继续
                continue;
            }else{
                treeResult.add(edge);
                //合并两个集合
                unionFind[root1]=root2;
            }
        }
        if(treeResult.size()==nodeSize-1) return treeResult;
        else return new ArrayList<Edge>();
    }

    public void initUnionFind(int[] unionFind){
        for(int i=0;i<unionFind.length;i++){
            unionFind[i]=-1;
        }
    }

    public int findRoot(int[] unionFind,int index){
        if(unionFind[index]==-1){
            return index;
        }else{
            int root=findRoot(unionFind,unionFind[index]);
            unionFind[index]=root;
            return root;
        }
    }

    public void union(int[] unionFind,int index1,int index2){
        int root1=findRoot(unionFind,index1);
        unionFind[root1]=index2;
    }

    //https://blog.csdn.net/fuxuemingzhu/article/details/101214765
    //https://leetcode-cn.com/problems/connecting-cities-with-minimum-cost/
    //leetcode连接城市以最小代价Kruskal解法
    //Input: N = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
    //Output: 6, Explanation: Choosing any 2 edges will connect all cities so we choose the minimum 2.
}
