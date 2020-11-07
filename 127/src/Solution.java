import java.util.*;

class Solution {

    List<List<Integer>> edge = new ArrayList<List<Integer>>();
    Map<String,Integer> wordID = new HashMap<>();
    int nodeNum = 0;

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        for(String word:wordList){
            addEdge(word);
        }
        addEdge(beginWord);
        if (!wordID.containsKey(endWord)) {
            return 0;
        }
        int beginID = wordID.get(beginWord);
        int endID = wordID.get(endWord);

        int[] top2BotDis = new int[nodeNum];
        Arrays.fill(top2BotDis,Integer.MAX_VALUE);
        Queue<Integer> top2BotQueue = new LinkedList<>();
        top2BotDis[beginID] = 0;
        top2BotQueue.offer(beginID);

        int[] bot2TopDis = new int[nodeNum];
        Arrays.fill(bot2TopDis,Integer.MAX_VALUE);
        Queue<Integer> bot2TopQueue = new LinkedList<>();
        bot2TopDis[endID] = 0;
        bot2TopQueue.offer(endID);

        while (!top2BotQueue.isEmpty()&&!bot2TopQueue.isEmpty()){
            int top2BotSize = top2BotQueue.size();
            for(int i=0;i<top2BotSize;i++){
                int x = top2BotQueue.poll();
                if(bot2TopDis[x]!=Integer.MAX_VALUE){
                    return (top2BotDis[x] + bot2TopDis[x]) / 2 + 1;
                }
                for(int a:edge.get(x)){
                    if(top2BotDis[a]==Integer.MAX_VALUE){
                        top2BotDis[a] = top2BotDis[x] + 1;
                        top2BotQueue.offer(a);
                    }
                }
            }
            int bot2TopSize = bot2TopQueue.size();
            for(int i=0;i<bot2TopSize;i++){
                int x = bot2TopQueue.poll();
                if(top2BotDis[x]!=Integer.MAX_VALUE){
                    return (top2BotDis[x] + bot2TopDis[x]) / 2 + 1;
                }
                for(int a:edge.get(x)){
                    if(bot2TopDis[a]==Integer.MAX_VALUE){
                        bot2TopDis[a] = bot2TopDis[x] + 1;
                        bot2TopQueue.offer(a);
                    }
                }
            }
        }
        return 0;
    }

    public void addEdge(String word){
        addWord(word);
        int id = wordID.get(word);
        char[] wordCharArray = word.toCharArray();
        int len = wordCharArray.length;
        for(int i=0;i<len;i++){
            wordCharArray[i] = '*';
            String tmpWord = new String(wordCharArray);
            addWord(tmpWord);
            int tmpWordId = wordID.get(tmpWord);
            edge.get(id).add(tmpWordId);
            edge.get(tmpWordId).add(id);
            wordCharArray[i] = word.charAt(i);
        }
    }

    public void addWord(String word){
        if(!wordID.containsKey(word)){
            wordID.put(word, nodeNum++);
            edge.add(new ArrayList<Integer>());
        }
    }

}
