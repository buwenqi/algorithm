package wenqi.graph;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by wenqi on 2018/10/1.
 * Dijkstra单源点最短路径
 * 存储：
 *  1. 使用邻接矩阵存储图（因为需要快速获取图中任意两点的距离，不相连用无穷大-1表示）
 *  2. 使用dist[i]记录从源点到i点的最短距离
 *  3. mark[i]标志i点是否已经处于最短路径的集合中
 *  4. 当前选中的最小起始点current
 * 算法过程：
 *  初始化所有dist[i]为-1，mark[i]为false,读入graph[][]
 *  将0加入节点最短路径集合中mark[0]=true，令current=0,dist[0]=0
 *  进行n-1次筛选：
 *      1. 更新dist数组，
 *          遍历n个节点（0到n-1）:j
 *              如果mark[j]已经未true或者[current][j]没有边（即-1），则应跳过
 *              如果dist[j]==-1(源点到j的距离无穷大)或者dist[j]>dist[current]+graph[current][j]:
 *                  应该更新dist[j]=dist[current]+graph[current][j]
 *      2. 筛选下一次的current:
 *          遍历所有dist(0-n-1):j
 *              如果已经在最短路径中或者dist[j]无穷大，则应该跳过
 *              选取最小的dist[j]的j为current，mark[j]=true
 *  直接返回dist结果即为所有最短路径
 *
 */
public class ShortestPathDijkstra {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        //edge number
        int m=scanner.nextInt();
        scanner.nextLine();
        int[][] graph=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                graph[i][j]=-1;
            }
        }
        //input edges
        //使用邻接矩阵存储
        for(int i=0;i<m;i++){
            int from=scanner.nextInt();
            int to=scanner.nextInt();
            int value=scanner.nextInt();
            graph[from][to]=value;
            scanner.nextLine();
        }

        //find shortest path
        //初始化，
        int[] dist=new int[n];
        boolean[] flag=new boolean[n];
        int[] path=new int[n];
        for(int i=0;i<n;i++){
            dist[i]=-1;
            flag[i]=false;
            path[i]=-1;
        }

        dist[0]=0;
        flag[0]=true;
        //重建路径的根
        path[0]=-1;
        int current=0;
        for(int i=1;i<n;i++){
            //loop for n-1 times
            //update dist
            for(int j=0;j<n;j++){
                if(flag[j]==true || graph[current][j]==-1){
                    continue;
                }
                if(dist[j]==-1||dist[j]>dist[current]+graph[current][j]){
                    dist[j]=dist[current]+graph[current][j];
                    //记录父节点，用于重建路径
                    path[j]=current;
                }
            }

            //find min dist which flag is false
            int minDist=0x7fffffff;
            for(int j=0;j<n;j++){
                if(flag[j]==true || dist[j]==-1){
                    continue;
                }
                if(dist[j]<minDist){
                    minDist=dist[j];
                    current=j;
                }
            }
            //mark flag[current] is true
            flag[current]=true;
        }

        System.out.println(Arrays.toString(dist));
        System.out.println(Arrays.toString(path));

    }
}
