package wenqi.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenqi on 2020/9/18.
 * 多源点Floyd最短路径算法（插点法）
 * 存储：
 *  1. 使用邻接矩阵存储graph[][]（目标是快速的获取两点之间的距离）
 *  2. 使用dist[i][j]存储从i到j最短路径长度
 *  3.（可选）使用path[i][j]存储i到j最短路径最后经过的点，可用于重建路径
 *
 * 算法过程：
 *  1. 获取graph[][],没有边的话用Integer.MAX_VALUE存储
 *      初始化dist[i][j]为graph[i][j]
 *      初始化path[i][j]全部为-1，代表i,j之前没有通过任何点，直连
 *   2. 选取k：0到n-1为待插入点
 *          遍历i，j对（两层循环）：
 *              如果graph[i][k]或者graph[k][j]==Integer.MAX_VALUE，表示k和i或者j不连通，则跳过更新
 *              如果graph[i][k]+graph[k][j]<dist[i][j],说明经过k之后i到j路径更小，更新：
 *                  dist[i][j]=graph[i][k]+graph[k][j]
 *                  记录path[i][j]=k
 *   3. 返回dist结果
 *   4. (可选) 恢复i->j的最短路径算法（递归算法，类似于中序遍历）：
 *      去经过点 mid=path[i][j]
 *      如果mid==-1,则说明直连，直接返回空
 *      求解（i，mid）的路径
 *      将mid加入结果集
 *      求解（mid,j）的路径
 *  最终方法获取的是中间的结果集，需要在两边加上i，j
 *
 * 参考：
 * https://blog.csdn.net/qq_34842671/article/details/90637502
 */
public class ShortestPathFloyd {

    public int[][] shortestPathFlod(int[][] graph){
        int[][] dist=new int[graph.length][graph[0].length];
        int[][] path=new int[graph.length][graph[0].length];
        for(int i=0;i<graph.length;i++){
            for(int j=0;j<graph[0].length;j++){
                dist[i][j]=graph[i][j];
                path[i][j]=-1;
            }
        }
        //graph是一个方阵，graph.length即为节点的个数
        for(int k=0;k<graph.length;k++){
            //k是插点
            for(int i=0;i<graph.length;i++){
                for(int j=0;j<graph.length;j++){
                    if(graph[i][k]==Integer.MAX_VALUE || graph[k][j]==Integer.MAX_VALUE) continue;
                    if(graph[i][k]+graph[k][j]<dist[i][j]) {
                        dist[i][j]=graph[i][k]+graph[k][j];
                        path[i][j]=k;
                    }
                }

            }
        }
        return dist;
    }

    public List<Integer> constructPath(int[][] path,int i,int j){
        List<Integer> result=new ArrayList<>();
        constructPathRecursive(path,i,j,result);
        result.add(0,i);
        result.add(j);
        return result;
    }

    public void constructPathRecursive(int path[][],int i,int j,List<Integer> result){
        int mid=path[i][j];
        if(mid==-1){ return;}
        constructPathRecursive(path,i,mid,result);
        result.add(mid);
        constructPathRecursive(path,mid,j,result);
    }

}
