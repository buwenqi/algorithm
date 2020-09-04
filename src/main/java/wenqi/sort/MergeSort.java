package wenqi.sort;

import java.util.Arrays;

/**
 * Created by wenqi on 2018/9/12.
 */
public class MergeSort {
    public static void main(String[] args){
        int[] arr={5,3,6,7,2,8};
        int[] tempArr=new int[arr.length];
        MergeSort mergeSort=new MergeSort();
        mergeSort.mergeSort(arr,tempArr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    //将arr中index从start到end排序
    public void mergeSort(int[] arr, int[] tmpArr,int start,int end){
        if(start>=end)
            return;
        int mid=(start+end)/2;
        mergeSort(arr,tmpArr,start,mid);
        mergeSort(arr,tmpArr,mid+1,end);
        //将两边的合并
        merge(arr,tmpArr,start,mid,end);

    }

    public void merge(int[] arr,int[] tempArr,int start,int mid,int end){
        //将从start-mid和mid+1到end的合并到同一个数组
        int leftIdx=start;
        int rightIdx=mid+1;
        int tempIdx=start;
        while (leftIdx<=mid&&rightIdx<=end){
            if(arr[leftIdx]<arr[rightIdx]){
                tempArr[tempIdx++]=arr[leftIdx++];
            }else{
                tempArr[tempIdx++]=arr[rightIdx++];
            }
        }
        //将剩余的加入到tempArr中
        while(leftIdx<=mid){
            tempArr[tempIdx++]=arr[leftIdx++];
        }

        while (rightIdx<=end){
            tempArr[tempIdx++]=arr[rightIdx++];
        }
        //将排好序的tempArr拷贝到arr中
        for(int i=start;i<=end;i++){
            arr[i]=tempArr[i];
        }
    }
}
