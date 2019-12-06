import java.util.ArrayList;

public class SpaceObject {

    String name;
    int amountOfOrbits = 0;
    SpaceObject parent;
    ArrayList<SpaceObject> children = new ArrayList<>();

    public String getName(){
        return name;
    }

    public int getAmountOfOrbits() {
        return amountOfOrbits;
    }

    public void setAmountOfOrbits(int amountOfOrbits) {
        this.amountOfOrbits = amountOfOrbits;
    }

    public ArrayList<SpaceObject> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<SpaceObject> children) {
        this.children = children;
    }

    public SpaceObject getParent() {
        return parent;
    }

    public void setParent(SpaceObject parent) {
        this.parent = parent;
    }

    public void increaseOrbits(int base){
        amountOfOrbits = base + amountOfOrbits + 1;
        for (SpaceObject child : children) {
            child.increaseOrbits(base);
        }
    }

    public void addChild(SpaceObject spaceObject){
        children.add(spaceObject);
        spaceObject.setParent(this);
        spaceObject.increaseOrbits(amountOfOrbits);
    }

    public SpaceObject(String name) {
        this.name = name;
    }
}
