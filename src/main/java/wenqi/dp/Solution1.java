package wenqi.dp;

/**
 * Created by wenqi on 2018/8/21.
 */
public class Solution1 {

    public static void main(String[] args){
        System.out.println(findLongest2(new int[]{1,2,2,3},4));
    }

  /*  public static int findLongest(int[] A,int n){
        //用于存储当前的最小尾序列，从1开始存放
        int ans[]=new int[n+1];
        //len用于存储当前最小尾的长度，也是最后一个的下标
        int
    }*/

    public static int findLongest2(int[] A, int n) {
        //dist代表以i结尾的最长子串的长度
        int dist[]=new int[n];
        for(int i=0;i<n;i++){
            dist[i]=1;
        }

        //i从1开始更新dist
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(A[i]>=A[j]) {
                    if (dist[i] < dist[j] + 1) {
                        dist[i] = dist[j] + 1;
                    }
                }
            }
        }
        int result=-1;
        for(int i=0;i<n;i++){
            if(dist[i]>result){
                result=dist[i];
            }
        }
        return result;
    }
}
