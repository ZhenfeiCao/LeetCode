import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class RandomizedSet {

    private ArrayList<Integer> nums;
    private HashMap<Integer,Integer> indexs;
    Random rand = new Random();

    /** Initialize your data structure here. */
    public RandomizedSet() {
        nums = new ArrayList<>();
        indexs = new HashMap<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(indexs.containsKey(val))
            return false;
        else{
            indexs.put(val,nums.size());
            nums.add(nums.size(),val);
            return true;
        }
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!indexs.containsKey(val)){
            return false;
        }else{
            int index = indexs.get(val);
            int copyElement = nums.get(nums.size()-1);
            nums.set(index, copyElement);
            indexs.put(copyElement,index);
            nums.remove(nums.size()-1);
            indexs.remove(val);
            return true;
        }
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return nums.get(rand.nextInt(nums.size()));
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */