package wenqi.sort;

import java.util.Arrays;

/**
 * Created by wenqi on 2018/9/12.
 */
public class QuickSort {

    public static void main(String[] args){
        int[] arr={5,5,6,7,2,5};
        QuickSort quickSort=new QuickSort();
        quickSort.quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public void quickSort(int[] arr,int start,int end){
        //当start>=end的时候返回
        if(start<end){
            int low=start;
            int high=end;
            //轴值取第一个
            int qivot=arr[start];
            while(low<high){
                //探索右边比qivot大的值,加等号的情况适用于有重复值的情况
                while(low<high && arr[high]>=qivot){
                    high--;
                }
                arr[low]=arr[high];
                while (low<high && arr[low]<=qivot){
                    low++;
                }
                arr[high]=arr[low];
            }
            arr[low]=qivot;
            quickSort(arr,start,low-1);
            quickSort(arr,low+1,end);
        }
    }
}
