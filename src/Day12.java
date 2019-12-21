import javax.swing.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12{

    public static void main(String[] args) {

        Axis xAxis = new Axis(new ArrayList<>(Arrays.asList(13, 16, 7, -3)));
        Axis yAxis = new Axis(new ArrayList<>(Arrays.asList(-13, 2, -18, -8)));
        Axis zAxis = new Axis(new ArrayList<>(Arrays.asList(-2, -15, -12, -8)));

        System.out.printf("Part one: %d\n", calculateEnergy(new Axis(xAxis), new Axis(yAxis), new Axis(zAxis)));
        System.out.printf("Part two: %d\n", lcm(calculateCycle(xAxis), lcm(calculateCycle(yAxis), calculateCycle(zAxis))));


    }

    public static void moveAxisOneStep(Axis axis){
        axis.recalculateVelocity();
        axis.applyVelocity();
    }

    private static BigInteger lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger abs = number1.multiply(number2).abs();
        return abs.divide(gcd);
    }

    public static BigInteger calculateCycle(Axis axis){
        Axis copy = new Axis(axis);
        int cycle = 1;

        while (true) {
            moveAxisOneStep(axis);
            if (axis.isSame(copy)) {
                return new BigInteger(String.valueOf(cycle));
            }
            cycle++;
        }
    }

    public static int calculateEnergy(Axis xAxis, Axis yAxis, Axis zAxis){
        IntStream
                .range(0, 1000)
                .boxed()
                .forEach(integer -> {
                    moveAxisOneStep(xAxis);
                    moveAxisOneStep(yAxis);
                    moveAxisOneStep(zAxis);
                });

        int total = IntStream.range(0, xAxis.coords.size())
                .boxed()
                .mapToInt(i -> {
                    return (Math.abs(xAxis.coords.get(i)) + Math.abs(yAxis.coords.get(i)) + Math.abs(zAxis.coords.get(i)))
                            *(Math.abs(xAxis.velocity.get(i)) + Math.abs(yAxis.velocity.get(i)) + Math.abs(zAxis.velocity.get(i)));
                })
                .reduce(Integer::sum)
                .getAsInt();

        return total;
    }
}

class Axis{
    ArrayList<Integer> coords;
    ArrayList<Integer> velocity;

    public Axis(ArrayList<Integer> coords){
        this.coords = coords;
        velocity = new ArrayList<>();
        coords.forEach(integer -> velocity.add(0));
    }

    public Axis(Axis axis){
        coords = new ArrayList<>(axis.coords);
        velocity = new ArrayList<>(axis.velocity);

    }

    public void applyVelocity(){
        IntStream
                .range(0, coords.size())
                .boxed()
                .forEach(i -> coords.set(i, coords.get(i) + velocity.get(i)));
    }

    public void recalculateVelocity(){
        ArrayList<Integer> change = (ArrayList<Integer>) coords
                .stream()
                .mapToInt(coord -> {
                    return coords
                            .stream()
                            .filter(otherCoord -> !otherCoord.equals(coord))
                            .mapToInt(otherCoord -> (int) Math.signum(otherCoord - coord))
                            .reduce(Integer::sum)
                            .getAsInt();
                })
                .boxed()
                .collect(Collectors.toList());

        IntStream
                .range(0, velocity.size())
                .boxed()
                .forEach(i -> velocity.set(i, velocity.get(i) + change.get(i)));

    }

    public boolean isSame(Axis axis){
        return coords.equals(axis.coords) && velocity.equals(axis.velocity);
    }

    @Override
    public String toString() {
        return new String(coords.toString() + " " + velocity.toString());
    }
}