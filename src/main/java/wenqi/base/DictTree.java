package wenqi.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenqi on 2021/1/6.
 * 字典树的增加，删除，查询单词，查询前缀功能
 * 参考：https://www.cnblogs.com/gaopeng527/p/4887765.html
 * https://blog.csdn.net/zuochao_2013/article/details/79134714
 */
public class DictTree {
    class TrieNode{
        public char val;
        public Map<Character,TrieNode> children;
        public boolean isEnd;
        //用于删除时候计数
        public int count;
        public TrieNode(char val){
            this.val=val;
            this.children=new HashMap<>();
            isEnd=false;
            count=0;
        }
    }

    public static void main(String[] args){
        DictTree dicTree=new DictTree();
        String str1="abc";
        String str2="abcd";
        String str3="abd";
        String str4="b";
        String str5="bcd";
        String str6="efg";
        String str7="hik";

        dicTree.insert(str1);
        dicTree.insert(str2);
        dicTree.insert(str3);
        dicTree.insert(str4);
        dicTree.insert(str5);
        dicTree.insert(str6);
        dicTree.insert(str7);

        System.out.println(dicTree.search(str3));
        dicTree.delete(str3);
        System.out.println(dicTree.search(str3));

        System.out.println(dicTree.searchPrefix(str3));
        System.out.println(dicTree.searchPrefix(str1));

    }

    public TrieNode root;
    public DictTree(){
        root=new TrieNode('a');
    }

    public void insert(String curStr){
        TrieNode preNode=root;
        for(int i=0;i<curStr.length();i++){
            char curChar=curStr.charAt(i);
            Map<Character,TrieNode> curChildrenMap=preNode.children;
            TrieNode curNode=curChildrenMap.getOrDefault(curChar,null);
            if(curNode==null) {
                TrieNode newNode=new TrieNode(curChar);
                curChildrenMap.put(curChar,newNode);
                curNode=newNode;
            }
            curNode.count++;
            preNode=curNode;
        }
        //最后一个节点置为isEnd=true
        preNode.isEnd=true;
    }

    public void delete(String deleteStr){
        TrieNode preNode=root;
        for(int i=0;i<deleteStr.length();i++){
            //依次删除
            char curChar=deleteStr.charAt(i);
            Map<Character,TrieNode> curChildrenMap=preNode.children;

            TrieNode curNode=curChildrenMap.getOrDefault(curChar,null);
            if(curNode==null) return;
            if(curNode.count==1){
                //只有一个了，直接删除
                curChildrenMap.remove(curChar);
                return;
            }else{
                //计数器减一即可
                curNode.count--;
            }
            preNode=curNode;
        }
    }

    /**
     * 查找字典树中是否存在某个字符串，
     * 算法：直接遍历所有的curStr的所有字符，碰到为空的中间节点则返回false
     * @param curStr
     * @return
     */
    public boolean search(String curStr){
        TrieNode preNode=root;
        for(int i=0;i<curStr.length();i++){
            char curChar=curStr.charAt(i);
            TrieNode curNode=preNode.children.getOrDefault(curChar,null);
            if(curNode==null) return false;
            preNode=curNode;
        }
        return preNode.isEnd;
    }

    /**
     * 查询前缀是否存在
     * @param prefix
     * @return
     */
    public boolean searchPrefix(String prefix){
        TrieNode preNode=root;
        for(int i=0;i<prefix.length();i++){
            char curChar=prefix.charAt(i);
            TrieNode curNode=preNode.children.getOrDefault(curChar,null);
            if(curNode==null) return false;
            preNode=curNode;
        }
        return true;
    }
}
