package wenqi.graph;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by wenqi on 2018/10/1.
 */
class Edge implements Comparable<Edge>{
    public int start;
    public int end;
    public int value;

    public Edge(int start,int end,int value){
        this.start=start;
        this.end=end;
        this.value=value;
    }
    @Override
    public int compareTo(Edge edge) {
        if(this.value==edge.value){
            return 0;
        }else{
            return this.value>edge.value?1:-1;
        }
    }
}
public class MST {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        //node number
        int n=scanner.nextInt();
        scanner.nextLine();
        //input edge:start,end,value
        int edgeNum=n*(n-1)/2;
        //store from index 1
        Edge[] edges=new Edge[edgeNum+1];
        for(int i=1;i<=edgeNum;i++){
            edges[i]=new Edge(scanner.nextInt(),scanner.nextInt(),scanner.nextInt());
            scanner.nextLine();
        }

        //1. sort edge in ascending order
        Arrays.sort(edges,1,edgeNum);

        //2. Union-Find tree to mark node's parent, -1 represent root
        int[] tree=new int[n+1];
        //store from index 1
        for(int i=1;i<=n;i++){
            tree[i]=-1;
        }

        int ans=0;
        for(int i=1;i<=edgeNum;i++){
            int startRoot=findRoot(edges[i].start,tree);
            int endRoot=findRoot(edges[i].end,tree);
            if(startRoot!=endRoot){
                //start and end is not in the same union
                tree[startRoot]=endRoot;
                ans+=edges[i].value;
            }
        }
        System.out.println(ans);

    }

    public static int findRoot(int index,int[] tree){
        if(tree[index]==-1){
            return index;
        }else{
            int root=findRoot(tree[index],tree);
            //route compress
            tree[index]=root;
            return root;
        }
    }


}
