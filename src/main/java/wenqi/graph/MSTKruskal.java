package wenqi.graph;

/**
 * Kruskal Algorithm: Edge expanding
 * Kruskal算法，使用边扩展
 * 存储：
 *  1.边集合，2并查集数组union,3.结果集TreeResult
 * 算法过程：
 *  1.将所有的边edges按从小到大排列，并初始化并查集union
 *  2.遍历edges至 edges末尾 或者 TreeResult.size=node的数量
 *      2.1 当前edge(u,v,weight),
 *      2.2 如果 (union,u,v)是联通的，则跳过
 *      2.3 如果不是联通的，将edge加入TreeResult
 *  3. 如果TreeResult.size==node的数量，则返回
 *      否则为连通，应该返回空集合
 *
 * @author buwenqi
 * @date 2020/9/17
 */
public class MSTKruskal {

}
