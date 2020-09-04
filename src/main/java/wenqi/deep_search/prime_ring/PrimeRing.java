package wenqi.deep_search.prime_ring;

import java.util.*;

/**
 * Created by wenqi on 2018/5/23.
 */
public class PrimeRing {
    //输入变量
    private static int n;

    //辅助变量
    //标记变量是否被访问到过
    private static boolean[] flag;
    /**
     * 素数和表，如果最大是17，则两个数之间的和最大为37
     */
    private static final List<Integer> prime= Arrays.asList(2,3,5,7,11,13,17,19,23,29,31,37);

    //结果容器
    private static int caseNum;
    //代替栈
    private static int[] ans;

    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        n=scanner.nextInt();
        scanner.nextLine();
        //初始化工作,从1开始存放
        flag=new boolean[n+1];
        ans=new int[n+1];
        caseNum=0;
        for(int i=1;i<=n;i++){
            flag[i]=false;
        }

        //开始深度遍历放入
        ans[1]=1;
        flag[1]=true;
        dfs(1);
    }

    /**
     * 放入的index位置后的操作
     * @param index
     */
    public static void dfs(int index){
        if(index>1){
            //开始检查后两个和是否为质数,不是的话直接返回
            if(!prime.contains(ans[index]+ans[index-1]))
                return;
        }
        if(index==n){
            //已经放入最后一个数，检查第一个数和最后一个数的和是否仍为质数
            if(prime.contains(ans[index]+ans[1])){
                caseNum++;
                System.out.println("Case "+caseNum);
                for(int i=1;i<=n;i++){
                    System.out.print(ans[i]+" ");
                }
                System.out.println();
            }
            return;
        }

        //非出口节点
        for(int i=2;i<=n;i++) {
           if(flag[i]==false){
               //i可以尝试放入结果中
               ans[index+1]=i;
               //将i位置的值设置为已用
               flag[i]=true;
               dfs(index+1);
               //递归回来设置i为可以用的状态
               flag[i]=false;
           }
        }
    }


}
