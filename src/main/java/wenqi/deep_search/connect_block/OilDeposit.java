package wenqi.deep_search.connect_block;

import java.util.Scanner;

/**
 * Created by wenqi on 2018/5/24.
 */
public class OilDeposit {
    //输入变量
    private static char maze[][];
    private static int row,col;

    //辅助变量
    //标志是否已经归入某个区域
    private static boolean mark[][];
    //分别是左上，上，右上，左，右，左下，下，右下
    private static final int directions[][]={{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

    //结果变量
    private static int ans;
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        while(true){
            row=scanner.nextInt();
            col=scanner.nextInt();
            scanner.nextLine();
            if(row==0&&col==0)
                break;
            //初始化
            maze=new char[row+1][col+1];
            mark=new boolean[row+1][col+1];
            String mazeLine;
            for(int i=1;i<=row;i++){
                mazeLine=scanner.nextLine();
                for(int j=1;j<=col;j++){
                    maze[i][j]=mazeLine.charAt(j-1);
                    mark[i][j]=false;
                }
            }
            ans=0;
            //遍历所有位置，进行扩展
            for(int i=1;i<=row;i++){
                for(int j=1;j<=col;j++){
                    if(mark[i][j]==true|| maze[i][j]=='*'){
                        //如果已经归入一个区域或者不是油田块（*表示没有，@表示有）
                        continue;
                    }else{
                        dfs(i,j);
                        ans++;
                    }
                }
            }
            System.out.println(ans);
        }
    }

    /**
     * 从x,y位置深度搜索8个方位相邻的油田，归为一块
     * @param x
     * @param y
     */
    public static void dfs(int x,int y){
        for(int i=0;i<8;i++){
            int nx=x+directions[i][0];
            int ny=y+directions[i][1];
            if(nx<1||ny<1||nx>row||ny>col)
                continue;
            if(maze[nx][ny]=='*')
                continue;
            if(mark[nx][ny]==true)
                continue;
            //设置相邻位置为已连通状态
            mark[nx][ny]=true;
            dfs(nx,ny);
        }
    }
}
