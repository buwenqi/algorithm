package wenqi.dp.str_instance;

import java.util.Scanner;

/**
 * Created by wenqi on 2018/8/13.
 * 求两个字符串A->B的最短编辑路径，动态规划
 */
public class Solution {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        String A=scanner.nextLine();
        String B=scanner.nextLine();

        int dist[][]=new int[A.length()+1][B.length()+1];

        //边界值初始化
        for(int i=0;i<=A.length();i++){
            dist[i][0]=i;
        }
        for(int j=0;j<=B.length();j++){
            dist[0][j]=j;
        }

        //开始填表，下标i,j是按从1开始的，但是从A,B中取的时候是i-1,j-1
        for(int i=1;i<=A.length();i++){
            for(int j=1;j<=B.length();j++){
                if(A.charAt(i-1)==B.charAt(j-1)){
                    dist[i][j]=dist[i-1][j-1];
                }else{
                    int deleteA=dist[i-1][j]+1;
                    int insertA=dist[i][j-1]+1;
                    int modifyA=dist[i-1][j-1]+1;
                    dist[i][j]=Math.min(deleteA,Math.min(insertA,modifyA));
                }
                System.out.print(dist[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println(dist[A.length()][B.length()]);
    }
}
