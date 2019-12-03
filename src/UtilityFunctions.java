import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class UtilityFunctions {

    public static ArrayList<String> convertInputToArrayListString(String path) throws Exception{

        File file = new File(path);

        BufferedReader br = new BufferedReader(new FileReader(file));

        ArrayList<String> lines = new ArrayList<>();
        String st;

        while ((st = br.readLine()) != null) {
            lines.add(st);
        }

        return lines;
    }

    public static String convertInputToString(String path) throws Exception {

        File file = new File(path);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String full = "";
        String st;

        while ((st = br.readLine()) != null) {
            full += st;
        }

        return full;
    }

    public static int countChar(String str, char c) {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }

        return count;
    }

    public static int findDiffernceBetweenStrings(String str1, String str2){

        int countDifference = 0;

        for (int i  = 0; i < str1.length(); i++){
            if (str1.charAt(i) != str2.charAt(i)){
                countDifference++;
            }
        }

        return countDifference;
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

}
