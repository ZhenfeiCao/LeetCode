import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] res = new double[queries.size()];
        HashMap<String, HashMap<String, Double>> edges = new HashMap<>();
        for(int i=0;i<equations.size();i++){
            String s1 = equations.get(i).get(0);
            String s2 = equations.get(i).get(1);
            if(edges.containsKey(s1)){
                edges.get(s1).put(s2, values[i]);
            }else{
                HashMap<String, Double> s1To2 = new HashMap<>();
                s1To2.put(s2, values[i]);
                edges.put(s1, s1To2);
            }
            if(edges.containsKey(s2)){
                edges.get(s2).put(s1, 1/values[i]);
            }else{
                HashMap<String, Double> s2To1 = new HashMap<>();
                s2To1.put(s1, 1/values[i]);
                edges.put(s2, s2To1);
            }
        }
        for(int i=0;i<queries.size();i++){
            String s1 = queries.get(i).get(0);
            String s2 = queries.get(i).get(1);
            double tmpRes = 0.0;
            if(!edges.containsKey(s1)||!edges.containsKey(s2)){
                tmpRes = -1.0;
            } else if(s1.equals(s2)){
                tmpRes = 1.0;
            }else{
                List<String> visited = new ArrayList<>();
                visited.add(s1);
                tmpRes = dfs(edges, s1, s2, visited, 1.0);
            }
            res[i] = tmpRes;
        }
        return res;
    }

    public double dfs(HashMap<String, HashMap<String, Double>> edges, String s1, String s2, List<String> visited, double total){
        HashMap<String, Double> tmp = edges.get(s1);
        if(tmp.containsKey(s2)){
            return total * tmp.get(s2);
        }
        for(String s : tmp.keySet()){
            if(!visited.contains(s)){
                List<String> newVisited = new ArrayList<>(visited);
                newVisited.add(s);
                double cur = total * tmp.get(s);
                double res = dfs(edges, s, s2, newVisited, cur);
                if(res > 0)
                    return res;
            }
        }
        return -1.0;
    }

}