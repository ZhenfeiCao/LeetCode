import java.util.*;

class RandomizedCollection {

    private ArrayList<Integer> nums;
    private HashMap<Integer, Set<Integer>> indexs;

    /** Initialize your data structure here. */
    public RandomizedCollection() {
        nums = new ArrayList<>();
        indexs = new HashMap<>();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        if(indexs.containsKey(val)){
            nums.add(nums.size(), val);
            Set<Integer> pos = indexs.get(val);
            pos.add(nums.size()-1);
            indexs.put(val, pos);
            return false;
        }else{
            nums.add(nums.size(), val);
            Set<Integer> pos = new HashSet<>();
            pos.add(nums.size()-1);
            indexs.put(val, pos);
            return true;
        }
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if(indexs.containsKey(val)){
            int lastElement = nums.get(nums.size()-1);
            Iterator<Integer> it = indexs.get(val).iterator();
            int firstIndex = it.next();
            nums.set(firstIndex, lastElement);

            indexs.get(lastElement).remove(nums.size()-1);
            indexs.get(val).remove(firstIndex);
            if(firstIndex<nums.size()-1)
                indexs.get(lastElement).add(firstIndex);
            if(indexs.get(val).size() == 0){
                indexs.remove(val);
            }
            nums.remove(nums.size()-1);
            return true;
        }else{
            return false;
        }
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        return nums.get((int) (Math.random() * nums.size()));
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */