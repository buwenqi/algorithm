package wenqi.rangeSearch;
import java.util.*;
/**
 * Created by wenqi on 2020/7/18.
 * 求一个无序数组右边比它小的值
 */
public class BitTreeUseCase {
    int[] tr;
    int n;
    int lowbit(int x)
    {
        return x & -x;
    }
    void add(int x, int v)
    {
        for (int i = x; i <= n; i += lowbit(i))
            tr[i] += v;
    }
    int sum(int x)
    {
        int res = 0;
        for (int i = x; i != 0; i -= lowbit(i))
            res += tr[i];
        return res;
    }
    public List<Integer> countSmaller(int[] nums) {
        //HashMap + 排序 离散化
        n = nums.length;
        tr = new int[n + 1];
        int[] arr = nums.clone();
        Arrays.sort(arr);

        //Hash的过程
        Map<Integer, Integer> hash = new HashMap<>();
        int id = 0;
        for (int x : arr)
            if (!hash.containsKey(x))
                hash.put(x, ++ id);

        List<Integer> res = new LinkedList<>();
        for (int i = n - 1; i >= 0; i --)
        {
            int x = hash.get(nums[i]);
            add(x, 1);
            res.add(0, sum(x - 1));//<
        }
        return res;
    }
}
