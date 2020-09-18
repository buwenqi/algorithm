package wenqi.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Prime Algorithm, Node splaying method
 * Prime算法，使用节点进行扩展
 * 存储：
 *  1.需要邻接链表存储以及节点数量nodeSize，node->相关的Edge（u,v,weight）,
 *      如果node用0-n代替的话，可以用Edge[]数组存储，需要在获取到一个节点时获取所有相关的边
 *  2.是否已经在最小生成树中的标记mark[]
 *  3.待选的边的优先队列-小根堆PriorityQueue heap
 *  4.输出：结果Edge集TreeResult
 *
 * 算法过程（核心以点进行扩展）：
 *  1. 初始化将0放入最小生成树，即mark[0]=true,并将与0相关的边放入待选边集合中
 *  2. 当TreeResult的数量<nodeSize-1 并且heap不为空时，执行如下操作：
 *      2.1 从heap中取出当前edge, 取出对应的节点u,v
 *      2.2 如果mark[u] 和mark[v] 都为true，说明都在最小生成树中，则直接跳过
 *      2.3, 否则的话，说明edge有效且为目标边，将edge放入TreeResult集合中
 *      2.4 以节点u和v进行扩展，如果u或者v不mark不为false，则将u或者v相关的边放入待选集合heap中
 *  3. 如果TreeResult的size==nodeSize-1返回TreeResult,否则直接返回空
 *
 * @author buwenqi
 * @date 2020/9/17
 */
public class MSTPrime {

    class Edge{
        public int u;
        public int v;
        public int weight;
        public Edge(){};
        public Edge(int u,int v,int weight){
            this.u=u;
            this.v=v;
            this.weight=weight;
        }
    }

    /**
     *
     * @param edges
     * @return
     */
    public List<Edge> getMinCostTree(int[][] edges, int nodeSize){
        //edges存储是以[[u,v,weight],[u,v,weight]]的方式，需要将其转换成邻接表的方式
        //使用List数组存储
        List[] graph=new List[nodeSize];
        for(int i=0;i<graph.length;i++){
            graph[i]=new ArrayList<Edge>();
        }
        for(int i=0;i<edges.length;i++){
            int u=edges[i][0];
            int v=edges[i][1];
            int weight=edges[i][2];
            List<Edge> uList=(List<Edge>) graph[u];
            List<Edge> vList=(List<Edge>) graph[v];
            //无向图
            uList.add(new Edge(u,v,weight));
            vList.add(new Edge(u,v,weight));
        }

        boolean[] marked=new boolean[nodeSize];
        PriorityQueue<Edge> candidateHeap=new PriorityQueue<Edge>((edge1,edge2)->edge1.weight-edge2.weight);
        List<Edge> result=new ArrayList<>();
        //已完成图的存储和初始化
        marked[0]=true;
        List<Edge> firstList=(List<Edge>)graph[0];
        for(Edge item:firstList){
            candidateHeap.offer(item);
        }
        while(!candidateHeap.isEmpty()){
            if(result.size()==nodeSize-1) break;
            Edge curEdge=candidateHeap.poll();
            if(marked[curEdge.u] && marked[curEdge.v]) continue;
            result.add(curEdge);
            if(!marked[curEdge.u]) candidateHeap.addAll(graph[curEdge.u]);
            if(!marked[curEdge.v]) candidateHeap.addAll(graph[curEdge.v]);
        }

        if(result.size()==nodeSize-1) return result;
        else return new ArrayList<Edge>();
    }
}
