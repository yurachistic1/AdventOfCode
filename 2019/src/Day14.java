import java.util.*;

public class Day14 {
    public static void main(String[] args) throws Exception {
        System.out.printf("Part one: %d\n", calcOre(1));
        System.out.printf("Part two: %d", binarySearch(0, 4000000, (long)Math.pow(10, 12)));

    }

    public static int binarySearch(int l, int r, long x) throws Exception
    {
        if (r >= l) {
            int mid = l + (r - l) / 2;

            if (calcOre(mid) > x)
                return binarySearch(l, mid - 1, x);

            return binarySearch(mid + 1, r, x);
        }
        return r;
    }

    public static long calcOre(long amountOfFuel) throws Exception{
        HashMap<String, Chemical> list = createListOfChemicals();
        HashMap<String, Long> total = new HashMap<>();
        total.put("FUEL", amountOfFuel);

        list.keySet()
                .forEach(str -> list.get(str).requirements.keySet().forEach(chemName -> list.get(chemName).reqBy++));


        while(!(total.containsKey("ORE") && total.size() == 1)){
            list
                    .keySet()
                    .stream()
                    .filter(name -> list.get(name).reqBy == 0)
                    .forEach(name -> {
                        long amountNeeded = total.get(name);
                        list.get(name).requirements.keySet().forEach(child -> {
                            list.get(child).reqBy--;
                            long amount = (long)list.get(name).requirements.get(child) * (long)Math.ceil((double)amountNeeded/(double)list.get(name).amount);
                            total.put(child, total.getOrDefault(child, (long) 0) + amount);
                        });
                        if (name != "ORE") {
                            total.remove(name);
                        }
                        list.get(name).reqBy--;
                    });
        }

        return total.get("ORE");
    }

    public static HashMap<String, Chemical> createListOfChemicals() throws Exception {
        HashMap<String, Chemical> list = new HashMap<>();
        ArrayList<String> input = UtilityFunctions.convertInputToArrayListString("./inputs/Day14.txt");
        String pattern = "(.*)( => )(.*)";

        input.forEach(i -> {
            HashMap<String, Long> reqs = new HashMap<>();
            String name = i.replaceAll(pattern, "$3").split(" ")[1];
            int amount = Integer.parseInt(i.replaceAll(pattern, "$3").split(" ")[0]);
            String[] comps = i.replaceAll(pattern, "$1").split(", ");
            Arrays.stream(comps)
                    .forEach(j -> {
                        String[] indComp = j.split(" ");
                        reqs.put(indComp[1], Long.parseLong(indComp[0]));
                    });
            list.put(name, new Chemical(amount, name, reqs));
        });

        list.put("ORE", new Chemical(1, "ORE", new  HashMap<String, Long>()));
        return list;
    }
}

class Chemical {

    int reqBy = 0;
    long amount;
    String name;
    HashMap<String, Long> requirements;

    public Chemical(long amount, String name, HashMap<String, Long> requirements) {
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
