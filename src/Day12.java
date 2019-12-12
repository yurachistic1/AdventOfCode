import java.util.ArrayList;

public class Day12 {

    public static void day12a() {
        int limit = 1000;

        ArrayList<PVector> positions = new ArrayList<>();
        positions.add(new PVector(13, -13, -2));
        positions.add(new PVector(16, 2, -15));
        positions.add(new PVector(7, -18, -12));
        positions.add(new PVector(-3, -8, -8));

        ArrayList<PVector> velocities = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) velocities.add(new PVector(0, 0, 0));

        for(int timestep = 1; timestep <= limit; timestep++) {
            for (int i = 0; i < positions.size(); i++) {
                for (int j = 0; j < positions.size(); j++) {
                    if (j == i) continue;
                    int velChangeX = 0;
                    int velChangeY = 0;
                    int velChangeZ = 0;
                    velChangeX = positions.get(i).getX() < positions.get(j).getX() ? 1 : -1;
                    if (positions.get(i).getX() == positions.get(j).getX()) velChangeX = 0;
                    velChangeY = positions.get(i).getY() < positions.get(j).getY() ? 1 : -1;
                    if(positions.get(i).getY() == positions.get(j).getY()) velChangeY = 0;
                    velChangeZ = positions.get(i).getZ() < positions.get(j).getZ() ? 1 : -1;
                    if (positions.get(i).getZ() == positions.get(j).getZ()) velChangeZ = 0;
                    velocities.get(i).add(new PVector(velChangeX, velChangeY, velChangeZ));
                }
            }
            //System.out.printf("After step %d:\n", timestep);
            for (int i = 0; i < positions.size(); i++) {
                positions.get(i).add(velocities.get(i));
//                System.out.print(positions.get(i) + " " + velocities.get(i));
//                System.out.println();
            }
        }

        int totalEnergy = 0;
        for (int i = 0; i < positions.size(); i++){
            int a = Math.abs(positions.get(i).getX()) +
                    Math.abs(positions.get(i).getY()) +
                    Math.abs(positions.get(i).getZ());

            int b = Math.abs(velocities.get(i).getX()) +
                    Math.abs(velocities.get(i).getY()) +
                    Math.abs(velocities.get(i).getZ());

            totalEnergy += a * b;
        }
        System.out.printf("Energy after step %d: %d", limit, totalEnergy);
    }
}
