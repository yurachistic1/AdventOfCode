import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Day6 {

    public static void day6a() throws Exception{

        HashMap<String, SpaceObject> orbits = generateMap();

        int sum = 0;

        for (String orbit : orbits.keySet()){
            sum += orbits.get(orbit).getAmountOfOrbits();
        }

        System.out.println(sum);
    }

    public static void day6b() throws Exception{

        HashMap<String, SpaceObject> orbits = generateMap();

        ArrayList<String> pathFromMeToCom = new ArrayList<>();
        pathFromMeToCom.add("YOU");
        SpaceObject spaceObject = orbits.get("YOU");
        while (spaceObject.getParent() != null){
            pathFromMeToCom.add(spaceObject.getParent().getName());
            spaceObject = spaceObject.getParent();
        }

        ArrayList<String> pathFromSanToCom = new ArrayList<>();
        pathFromSanToCom.add("SAN");
        SpaceObject spaceObject1 = orbits.get("SAN");
        while (spaceObject1.getParent() != null){
            pathFromSanToCom.add(spaceObject1.getParent().getName());
            spaceObject1 = spaceObject1.getParent();
        }

        int count = 0;
        int i = pathFromMeToCom.size() - 1;
        int j = pathFromSanToCom.size() - 1;

        for(;;){
            if (pathFromMeToCom.get(i) != pathFromSanToCom.get(j)){
                break;
            }
            count++;
            i--;
            j--;
        }

        System.out.println(pathFromMeToCom.size() - 1 + pathFromSanToCom.size() - 1 - 2 * count);
    }

    public static HashMap<String, SpaceObject> generateMap () throws Exception{
        ArrayList<String> planets = UtilityFunctions.convertInputToArrayListString("./inputs/Day6.txt");

        HashMap<String, SpaceObject> orbits = new HashMap<>();

        for(String pair : planets){
            String[] arr = pair.split("\\)");
            if (orbits.containsKey(arr[0])){
                if (!orbits.containsKey(arr[1])) {
                    orbits.put(arr[1], new SpaceObject(arr[1]));
                }
                orbits.get(arr[0]).addChild(orbits.get(arr[1]));
            } else {
                if (orbits.containsKey(arr[1])){
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
