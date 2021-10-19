package wenqi.base;

/**
 * Created by wenqi on 2021/1/6.
 * 实现队列，使用循环数组
 */
public class MyQueue {
    public static void main(String[] args){
        MyQueue queue=new MyQueue(3);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        while (!queue.isEmpty()){
            queue.poll();
        }

        queue.offer(5);
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);

    }
    int[] arr;
    int front;
    int rear;
    int MAX_SIZE;
    public MyQueue(int max_size){
        this.MAX_SIZE=max_size+1;
        front=0;
        rear=0;
        //队列为空时，front=end,队列满时仅相差1个
        arr=new int[this.MAX_SIZE];
    }

    public boolean isEmpty(){
        return this.front==this.rear;
    }

    public boolean isFull(){
        return (this.rear+1)%this.MAX_SIZE==this.front;
    }

    public boolean offer(int val){
        if(isFull()){
            System.out.println("full queue");
            return false;
        }else{
            //rear处于当前已经排好的队列的最后一个位置，新放入值需要前进一步
            this.rear=(this.rear+1)%this.MAX_SIZE;
            arr[rear]=val;
            System.out.println(String.format("index:%s,offer:%s", rear, val));
            return true;
        }
    }

    public int poll(){
        if(isEmpty()){
            System.out.println("empty queue");
            return -1;
        }else{
            //front执行头部的前一个位置，front位置为空，所以需要前进一格取值，并置空
            front=(front+1)%MAX_SIZE;
            System.out.println(String.format("index:%s,poll:%s", front, arr[front]));
            //不需要置空，直接覆盖即可
            return arr[front];
        }
    }
}
