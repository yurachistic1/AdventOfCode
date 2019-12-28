import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day14 {
    public static void main(String[] args) throws Exception {
        HashMap<String, Chemical> list = createListOfChemicals();

        System.out.println(list.get("FUEL").requirements);
        System.out.println(list.size());

		System.out.println(depthFirst(list.get("FUEL"), list, 1, 0));
    }

    public static int depthFirst(Chemical chemical, HashMap<String, Chemical> list, int amount, int total) {
    	if (chemical.requirements.size() == 1 && chemical.requirements.containsKey("ORE")){
			return total + (amount / chemical.amount) * chemical.requirements.get("ORE");
		} else {
			int finalTotal = total;
			chemical.requirements.keySet().forEach(i -> {
				Chemical nextChem = list.get(chemical.requirements.get(i));
				finalTotal += depthFirst(nextChem, list, amount / chemical.amount, finalTotal);
			});
		}

    	return total;
    }

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
