package wenqi.graph;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by wenqi on 2018/10/1.
 */
public class ShortestPath {
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
        for(int i=0;i<m;i++){
            int from=scanner.nextInt();
            int to=scanner.nextInt();
            int value=scanner.nextInt();
            graph[from][to]=value;
            scanner.nextLine();
        }

        //find shortest path
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
