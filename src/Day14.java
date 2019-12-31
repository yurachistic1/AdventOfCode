import java.util.*;

public class Day14 {
    public static void main(String[] args) throws Exception {
        HashMap<String, Chemical> list = createListOfChemicals();

        System.out.println(list.get("FUEL").requirements);
        System.out.println(list.size());
        HashMap<String, Double> reqs = new HashMap<>();
        HashMap<String, Integer> excess = new HashMap<>();

		depthFirst(list.get("FUEL"), list, 1, reqs);
        System.out.println(reqs);
        calcTotal(list, reqs);

    }

    public static void calcTotal(HashMap<String, Chemical> list, HashMap<String, Double> reqs){
        int total = reqs
                .keySet()
                .stream()
                .mapToInt(chemName -> {
                    Chemical current = list.get(chemName);
                    double amount = Math.ceil(reqs.get(chemName));
                    int oreReq = (int)Math.ceil(amount/(double)current.amount) * current.requirements.get("ORE");
                    return oreReq;
                })
                .reduce(Integer::sum)
                .getAsInt();
        System.out.println(total);
    }

    public static void depthFirst(Chemical chemical, HashMap<String, Chemical> list, double amount, HashMap<String, Double> reqs) {
    	if (chemical.requirements.containsKey("ORE")){
    	    reqs.put(chemical.name, reqs.getOrDefault(chemical.name, 0.0) + amount);
    	    return;
        } else {
    	    for(String chem : chemical.requirements.keySet()){
                double req = (double)amount/(double)chemical.amount * chemical.requirements.get(chem);//(int)Math.ceil((double)amount/(double)chemical.amount)
    	        depthFirst(list.get(chem), list, req, reqs);
            }
        }
    }

    public static void depthFirstAlt(Chemical chemical, HashMap<String, Chemical> list, int amount, HashMap<String, Double> reqs, HashMap<String, Integer> excess) {
        if (chemical.requirements.containsKey("ORE")){
            reqs.put(chemical.name, reqs.getOrDefault(chemical.name, 0.0) + amount);
            return;
        } else {
            for(String chem : chemical.requirements.keySet()){
                int req = (int)Math.ceil((double)amount/(double)chemical.amount) * chemical.requirements.get(chem);
                depthFirst(list.get(chem), list, req, reqs);
            }
        }
    }

//    public static void breadthFirst(HashMap<String, Chemical> list, HashMap<String, Integer> reqs){
//        ArrayDeque<String> nextlvl = new ArrayDeque<>();
//        HashMap<String, Integer> current = list.get("FUEL").requirements;
//        HashMap<String, Integer> next = new HashMap<>();
//
//        while(!nextlvl.isEmpty()){
//            for (String name : current.keySet()){
//                int req = (int)Math.ceil((double)amount/(double)chemical.amount) * chemical.requirements.get(chem);
//                next.put(name, next.getOrDefault(name, 0) + current.get(name));
//            }
//        }
//    }

    public static HashMap<String, Chemical> createListOfChemicals() throws Exception {
        HashMap<String, Chemical> list = new HashMap<>();
        ArrayList<String> input = UtilityFunctions.convertInputToArrayListString("./inputs/Day14.txt");
        String pattern = "(.*)( => )(.*)";

        input.forEach(i -> {
            HashMap<String, Integer> reqs = new HashMap<>();
            String name = i.replaceAll(pattern, "$3").split(" ")[1];
            int amount = Integer.parseInt(i.replaceAll(pattern, "$3").split(" ")[0]);
            String[] comps = i.replaceAll(pattern, "$1").split(", ");
            Arrays.stream(comps)
                    .forEach(j -> {
                        String[] indComp = j.split(" ");
                        reqs.put(indComp[1], Integer.parseInt(indComp[0]));
                    });
            list.put(name, new Chemical(amount, name, reqs));
        });
        return list;
    }
}

class Chemical {
    int amount;
    String name;
    HashMap<String, Integer> requirements;

    public Chemical(int amount, String name, HashMap<String, Integer> requirements) {
        this.amount = amount;
        this.name = name;
        this.requirements = requirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chemical chemical = (Chemical) o;
        return this.name.equals(chemical.name);
    }
}
