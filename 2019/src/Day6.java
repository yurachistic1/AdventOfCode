import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Day6 {


    public static void main(String[] args) throws Exception{

        HashMap<String, SpaceObject> orbits = generateMap();

        int checkSum = orbits
                .keySet()
                .stream()
                .mapToInt(i -> orbits.get(i).getAmountOfOrbits())
                .reduce(0, Integer::sum);


        int pathLen = pathFromMeToSan(orbits);

        System.out.printf("Part one: %d\n", checkSum);
        System.out.printf("Part two: %d\n", pathLen);
    }

    public static int pathFromMeToSan(HashMap<String, SpaceObject> orbits){

        ArrayList<String> pathFromMeToCom = generatePathToCentre(orbits, "YOU");
        ArrayList<String> pathFromSanToCom = generatePathToCentre(orbits, "SAN");

        int count = 1;
        int i = pathFromMeToCom.size() - 1;
        int j = pathFromSanToCom.size() - 1;

        while (pathFromMeToCom.get(i) == pathFromSanToCom.get(j)){
            count++;
            i--;
            j--;
        }

        return pathFromMeToCom.size() + pathFromSanToCom.size() - 2 * count;
    }

    public static ArrayList<String> generatePathToCentre(HashMap<String, SpaceObject> orbits, String start){
        ArrayList<String> pathToCom = new ArrayList<>();
        pathToCom.add(start);
        SpaceObject spaceObject = orbits.get(start);
        while (spaceObject.getParent() != null) {
            pathToCom.add(spaceObject.getParent().getName());
            spaceObject = spaceObject.getParent();
        }
        return pathToCom;
    }

    public static HashMap<String, SpaceObject> generateMap() throws Exception {
        ArrayList<String> planets = UtilityFunctions.convertInputToArrayListString("./inputs/Day6.txt");

        HashMap<String, SpaceObject> orbits = new HashMap<>();

        for (String pair : planets) {
            String[] arr = pair.split("\\)");
            if (orbits.containsKey(arr[0])) {
                if (!orbits.containsKey(arr[1])) {
                    orbits.put(arr[1], new SpaceObject(arr[1]));
                }
                orbits.get(arr[0]).addChild(orbits.get(arr[1]));
            } else {
                if (orbits.containsKey(arr[1])) {
                    orbits.put(arr[0], new SpaceObject(arr[0]));
                    orbits.get(arr[0]).addChild(orbits.get(arr[1]));
                } else {
                    orbits.put(arr[0], new SpaceObject(arr[0]));
                    orbits.put(arr[1], new SpaceObject(arr[1]));
                    orbits.get(arr[0]).addChild(orbits.get(arr[1]));
                }
            }
        }

        return orbits;
    }
}

class SpaceObject {

    String name;
    int amountOfOrbits = 0;
    SpaceObject parent;
    ArrayList<SpaceObject> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public int getAmountOfOrbits() {
        return amountOfOrbits;
    }

    public SpaceObject getParent() {
        return parent;
    }

    public void setParent(SpaceObject parent) {
        this.parent = parent;
    }

    public void increaseOrbits(int base) {
        amountOfOrbits = base + amountOfOrbits + 1;
        for (SpaceObject child : children) {
            child.increaseOrbits(base);
        }
    }

    public void addChild(SpaceObject spaceObject) {
        children.add(spaceObject);
        spaceObject.setParent(this);
        spaceObject.increaseOrbits(amountOfOrbits);
    }

    public SpaceObject(String name) {
        this.name = name;
    }
}


