package wenqi.deep_search.dfs_maze;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by wenqi on 2018/5/22.
 */
public class Maze {
    //输入值定义为全局变量
    public static int maze[][];
    //行，列
    public static int xlen, ylen;
    public static Position start,end;

    //辅助全局常量
    //上下，左右
    public static final int directions[][]={{-1,0},{1,0},{0,-1},{0,1}};

    //结果容器
    //用于存储最优的路径
    public static Stack<Position> optimalStack=null;
    //设置为最大值
    public static int optimalStep=Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        xlen=scanner.nextInt();
        ylen=scanner.nextInt();
        scanner.nextLine();
        //读取地图信息
        //从1开始存放,0代表空，1代表墙
        maze=new int[xlen+1][ylen+1];
        for(int i=1;i<=xlen;i++){
            for(int j=1;j<=ylen;j++){
                maze[i][j]=scanner.nextInt();
            }
            scanner.nextLine();
        }
        //输入起始坐标和结束坐标
        start=new Position();
        end=new Position();
        start.x=scanner.nextInt();
        start.y=scanner.nextInt();
        end.x=scanner.nextInt();
        end.y=scanner.nextInt();
        scanner.nextLine();
        //开始dfs遍历，目标找出最短的路径值，及其路径坐标
        Stack<Position> stack=new Stack<>();
        int step=0;
        //将初始节点置为墙
        maze[start.x][start.y]=1;
        stack.push(start);
        dfs(start.x,start.y,stack,step);
        if(optimalStack!=null) {
            System.out.println("最少的步数是：" + optimalStep);
            System.out.println(optimalStack.toString());
        }else{
            System.out.println("没有通路");
        }
    }

    public static void dfs(int x,int y,Stack<Position> stack, int step){
        //从当前的节点网四个方向试探
        for(int i=0;i<4;i++){
            //获取下一个位置的坐标进行判断
            int nx=x+directions[i][0];
            int ny=y+directions[i][1];
            //超过迷宫范围
            if(nx<1||nx>xlen||ny<1||ny>ylen)
                continue;
            //新节点位置为墙
            if(maze[nx][ny]==1)
                continue;
            if(nx==end.x&&ny==end.y){
                //到达节点目标位置
                //如果当前步数小于最优步数，则更新最优步数为当前步数
                step++;
                stack.push(new Position(nx,ny));
                if(step<optimalStep){
                    optimalStack =(Stack<Position>)stack.clone();
                    optimalStep=step;
                }
                step--;
                stack.pop();
                return;
            }

            //如果不是最终节点的处理方式,可以跳到步子上，从新的节点上张望
            //站到下一个节点上（步数加一，压栈），并在新的节点上做深度遍历
            step++;
            stack.push(new Position(nx,ny));
            maze[nx][ny]=1;
            dfs(nx,ny,stack,step);
            step--;
            stack.pop();
            maze[nx][ny]=0;

        }
    }
}


class Position{
    public Position(){};
    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }
    public int x;
    public int y;

    @Override
    public String toString(){
        return "<"+x+","+y+">";
    }
}
