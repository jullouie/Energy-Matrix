import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class matrix {

    // instance variables
    static String[] attributes = {"Abundance", "Difficulty", "Intermittency", "Demonstrated", "Electricity", "Heat", "Transport", "Acceptance", "Backyard?", "Efficiency", "Environmental Impact", "Cost"};
    static String[] energySource = {"Natural Gas", "Petroleum", "Coal", "Solar PV", "Hydroelectric", "Wind", "Uranium"};
    static double[][] points = {{0, 1, 1, 1, 1, 1, 1/2, 1, 1, 1/2, 0, 1}, //natural gas
                                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1/2, 0, 1}, // petroleum
                                {0, 1, 1, 1, 1, 1, 0, 1, 1, 1/2, 0, 1}, // coal
                                {1, 1, -1, 1, 1, 0, 0, 1, 1, 0, 1, 1/2},  // solar
                                {-1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1/2}, // hydro
                                {0, 1, -1, 1, 1, 0, 0, 0, 1, 0, 1/2, 1/2}, // wind
                                {1, 0, 1, 0, 1, 1, 0, -1, -1, 0, 1, 0}}; // uranium/nuclear
    static HashMap<String, Integer> dict = new HashMap<String, Integer>();  
    static double[] weighting = new double[12];               

    @SuppressWarnings("rawtypes")
    public static void userInteraction(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ADD INSTRUCTIONS");
        double total_weight = 0;
        int index = 0;

        // get user weightings
        for (int i=0; i < attributes.length; i++){
            System.out.println("What is your weighting for " + attributes[i] + "?");
            weighting[i] = scanner.nextDouble();
            total_weight += weighting[i];
        }

        // ensure it adds to 12
        while(total_weight != 12){
            double distanceToTwelve = total_weight - 12;
            System.out.println("\nYour total weight does not add to 12.");
            if (distanceToTwelve < 0){
                System.out.println("You need to add " + Math.abs(distanceToTwelve) + " more to reach 12.");
            } else {
                System.out.println("You need to subtract by " + distanceToTwelve + " to reach 12.");
            }
            System.out.println();

            // print out the current weightings
            int count = 0;
            for (int i = 0; i < attributes.length; i++){
                count = i + 1;
                System.out.println(count + ". " + attributes[i] + ": " + weighting[i]);
            }
            System.out.println();
            System.out.println("Please type the attribute number you would like to edit");
            index = scanner.nextInt() - 1;
            System.out.println("You chose " + weighting[index] + " prior.");
            System.out.println("What weighting would you like to change " + attributes[index] + " to?");
            total_weight -= weighting[index];
            weighting[index] = scanner.nextDouble();
            total_weight += weighting[index];
 
        }
        scanner.close();

        // compute scores for each energy source
        int score;

        // for each energy source
        for (int i=0; i < points.length; i++){
            // represents the score for each energy source
            score = 0;

            // multiply every attribute weight with its points
            for (int j=0; j<attributes.length; j++){
                score += weighting[j] * points[i][j];
            }
            dict.put(energySource[i], score);
        }

        // run comparator to find ranking in dict
        Object[] a = dict.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((HashMap.Entry<String, Integer>) o2).getValue().compareTo(((HashMap.Entry<String, Integer>) o1).getValue());
            }
        });

        System.out.println("\nYour Energy Source Ranking (highest to lowest) will be printed as 'Energy Source': Score");
        for (Object e : a) {
            System.out.println(((HashMap.Entry<String, Integer>) e).getKey() + ": "
                    + ((HashMap.Entry<String, Integer>) e).getValue());
        }
        
        // TODO: PRINT FEEDBACK
    }
    public static void main(String[] args) {
        matrix.userInteraction();
    }

}