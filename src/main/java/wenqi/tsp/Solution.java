package wenqi.tsp;

import java.util.Scanner;

/**
 * Created by wenqi on 2018/8/12.
 * tsp旅行商问题
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
         * j定义的集合的意思是以二进制表示哪个点在集合内，比如5（101）代表0号和2号店在集合内
         * 可以用j>>(k-1) &1 == 1来判断k号节点是否在集合中
         * 用j^(1<<(k-1))从集合中去掉k
         */
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
                if (isInSet(i,j))
                    continue;

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
