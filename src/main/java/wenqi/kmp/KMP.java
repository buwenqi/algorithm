package wenqi.kmp;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by wenqi on 2018/9/3.
 */
public class KMP {
    public static void main(String[] args){
        int[] next;
        Scanner scanner=new Scanner(System.in);
        String mainStr=scanner.nextLine();
        String pattern=scanner.nextLine();
        next=KMP.getNext(pattern);

        //母串
        int i=0;
        //子串
        int j=0;
        while(i<mainStr.length()&& j<pattern.length()){
            if(next[j]==-1||mainStr.charAt(i)==pattern.charAt(j)){
                //如果第一个字符都不匹配或者相等，则应该同时往前移动
                i++;
                j++;
            }else{
                //否则，只要移动j就可以了，next[j]代表j不匹配的时候应该滑动的位置
                j=next[j];
            }
        }
        System.out.println(Arrays.toString(next));
        if(j==pattern.length()){
            System.out.println(i-j);
        }else{
            System.out.println(-1);
        }
    }

    /**
     * next[j]代表，j之前字符串的最长前缀后缀，
     * 同时也代表着j匹配失败后j应该跳到的位置
     * @param ps
     * @return
     */
    public static int[] getNext(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        //k代表前一个的最长前后缀长度
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                //这种情况下，p[0]~p[k-1]和p[j-1]为结尾的后缀已验证，
                //若p[j]==p[k],代表，以k结尾的前缀和以j为结尾的后缀相等
                next[++j] = ++k;
            } else {
                //递归找后缀的最长前缀，相当于将前缀作为匹配串，后缀作为模式进行匹配，是个递归过程
                k = next[k];
            }
        }
        return next;
    }
}
