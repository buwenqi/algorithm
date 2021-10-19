package wenqi.graph.tsp;

import java.util.Scanner;

/**
 * Created by wenqi on 2018/8/12.
 * tsp旅行商问题，可以使用经典的深度优先搜索进行求解，这里使用动态规划自底向上求解
 *
 * 存储：
 * 1. 图使用邻接矩阵存储，因为要获取任意两点之间的距离
 * 2. 动态规划矩阵dp[][]:
 *      表示：
 *          dp[i][j]代表从i经过j集合回到原点的最小值
 *          i代表第i个节点
 *          j代表第集合，二进制表示，例如3（011）代表集合{1,2},0不在j的考虑范围内
 *      更新：
 *          动态规划更新应该按列更新，初始集合{},空集dp[i][0]=graph[0][i]，即原点0和i的直达节点
 *          dp[i][j]=min(dp[k][集合j去掉k后的集合值]),
 *          其中k是j集合中的任一个值，可以通过遍历获取，集合j去掉k后的集合值可以通过j^(i<<(k-1))获取
 * 3.算法过程：
 *   1.获取图graph[][],没有联通的话用-1表示
 *   2. 通过节点数计算n,计算dp j的数量colNum=1<<(n-1),dp[n][colNum],dp初始化均为-1
 *   3. 初始化dp[i][0] 为graph[i][0]
 *   4. 列j从1到colNum-1更新：
 *      遍历所有的点i,查询不在j列表中的点i:
 *          取出集合j中的一个点k:（从所有的可能中求解min的最小值）
 *              如果 [i][k]的距离 加上 k经过(j除去k)之后的集合所得的路径长度现在的dp[i][j]:
 *                  则更新
 *
 * 参考：
 * https://blog.csdn.net/derek_tian/article/details/45057443
 */
public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        //从0存放，没有边用-1表示,自己到自己也应该定为-1
        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
        }

        /**
         * 动态规划计算
         * dist表示，从i开始，经过j定义的集合中的点回到初始点的最短距离
         * 假设初始点为0
         * j定义的集合的意思是以二进制表示哪个点在集合内，比如5（101）代表1号和3号店在集合内,0号是初始节点
         * 可以用j>>(k-1) &1 == 1来判断k号节点是否在集合中
         * 用j^(1<<(k-1))从集合中去掉k
         */

        //列数集合,以4个节点0,1,2,3算，起点是0的话，其排列应该是：
        //{},{1},{2},{3},{1,2},{1,3},{2,3},{1,2,3}
        int colSize = 1 << (n - 1);
        int[][] dist = new int[n][colSize];

        //将第0列(也就是不经过任何集合回到原点)置为graph[i][0]
        for (int i = 0; i < n; i++) {
            dist[i][0] = graph[i][0];
        }
        //开始按列更新dist数组
        for (int j = 1; j < colSize; j++) {
            for (int i = 0; i < n; i++) {
                //初始化
                dist[i][j]=-1;
                //如果i已经在j集合中，则不符合情况，直接跳过
                //i只会尝试从不包含i的节点返回原点
                if (isInSet(i,j))
                    continue;

                //尝试从集合中取出一个k，如果i到k，加上i经过j集合去掉k(已计算)回到i的值小于现在的值，则更新
                for(int k=1;k<n;k++){
                    //尝试将k从集合j中提出来，并且设置i->k，k->{set-k}
                    if(!isInSet(k,j)){
                        //如果不在集合中则不用处理
                        continue;
                    }

                    //没有边也不用更新,dist为-1也说明无路径
                    if(graph[i][k]==-1 || dist[k][j^(1<<(k-1))]==-1)
                        continue;

                    if(dist[i][j]==-1 || dist[i][j]>graph[i][k]+dist[k][j^(1<<(k-1))]){
                        dist[i][j]=graph[i][k]+dist[k][j^(1<<(k-1))];
                    }
                }
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<colSize;j++){
                System.out.print(dist[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static boolean isInSet(int i, int set) {
        //i是0的时候会返回false,代表0不在集合中
        //set>>-1会取-1的后5位
        return (set >> (i - 1) & 1) == 1;
    }
}
