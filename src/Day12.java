public class Day12{
}

/*
import static java.util.stream.Collectors.toList;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Day12 {

    public static void main(String[] args) {
        List<Coordinate> xAxis = createAxis(13, 16, 7, -3);
        List<Coordinate> yAxis = createAxis(-13, 2, -18, -8);
        List<Coordinate> zAxis = createAxis(-2,-15,-12,-8);

        calculateEnergyOfSystem(xAxis, yAxis, zAxis);
        calculateStepsForCycle(xAxis, yAxis, zAxis);
    }

    private static List<Coordinate> createAxis(int... axis) {
        return Arrays.stream(axis).mapToObj(Coordinate::new).collect(toList());
    }

    private static void calculateEnergyOfSystem(List<Coordinate> xAxis, List<Coordinate> yAxis, List<Coordinate> zAxis) {
        for (int i = 1; i <= 1000; i++) {
            moveOneStep(xAxis);
            moveOneStep(yAxis);
            moveOneStep(zAxis);
        }
        System.out.println("The total energy of the system after 1000 cycles: " + calculateEnergy(xAxis, yAxis, zAxis));
    }

    private static void moveOneStep(List<Coordinate> axis) {
        axis.forEach(anAxis -> axis.stream().filter(otherAxis -> !otherAxis.equals(anAxis)).forEach(anAxis::recalculateVelocity));
        axis.forEach(Coordinate::applyVelocity);
    }

    private static int calculateEnergy(List<Coordinate> xAxis, List<Coordinate> yAxis, List<Coordinate> zAxis) {
        int energy = 0;
        for(int i = 0; i < xAxis.size(); i++) {
            energy += calculateEnergy(xAxis.get(i), yAxis.get(i), zAxis.get(i));
        }
        return energy;
    }

    private static int calculateEnergy(Coordinate x, Coordinate y, Coordinate z) {
        return (Math.abs(x.getPosition()) + Math.abs(y.getPosition()) + Math.abs(z.getPosition())) * (Math.abs(x.getVelocity()) + Math.abs(y.getVelocity()) + Math.abs(z.getVelocity()));
    }

    private static void calculateStepsForCycle(List<Coordinate> xAxis, List<Coordinate> yAxis, List<Coordinate> zAxis) {
        BigInteger stepsForCycle = lcd(lcd(calculateStepsForCycle(xAxis), calculateStepsForCycle(yAxis)), calculateStepsForCycle(zAxis));
        System.out.println("it took " + stepsForCycle + " to complete a full cycle");
    }

    private static BigInteger calculateStepsForCycle(List<Coordinate> axis) {
        List<Coordinate> origin = clone(axis);
        int cycleNumber = 0;
        while(true) {
            moveOneStep(axis);
            cycleNumber++;
            if(isSame(axis, origin)) {
                return new BigInteger(String.valueOf(cycleNumber));
            }
        }
    }

    private static List<Coordinate> clone(List<Coordinate> axis) {
        return axis.stream().map(Coordinate::new).collect(toList());
    }

    private static boolean isSame(List<Coordinate> axis, List<Coordinate> otherAxis) {
        for(int i = 0; i < axis.size(); i++) {
            if(!axis.get(i).isSame(otherAxis.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static BigInteger lcd(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger abs = number1.multiply(number2).abs();
        return abs.divide(gcd);
    }

}

class Coordinate {
    private int position;
    private int velocity = 0;

    Coordinate(int position) {
        this.position = position;
    }

    Coordinate(Coordinate coordinate) {
        this.position = coordinate.position;
        this.velocity = coordinate.velocity;
    }

    int getPosition() {
        return position;
    }

    int getVelocity() {
        return velocity;
    }

    void applyVelocity() {
        this.position += velocity;
    }

    void recalculateVelocity(Coordinate other) {
        if (other.position > this.getPosition()) {
            velocity++;
        } else if (other.position < this.getPosition()) {
            velocity--;
        }
    }

    boolean isSame(Coordinate coordinate) {
        return position == coordinate.position && velocity == coordinate.velocity;
    }

}
*/
