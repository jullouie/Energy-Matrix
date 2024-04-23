import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author 
 */
public class matrix {

    // instance variables
    static String[] attributes = {"Abundance", "Difficulty", "Intermittency", "Demonstrated", "Electricity", "Heat", "Transport", "Acceptance", "Backyard?", "Efficiency", "Environmental Impact", "Cost"};
    static String[] energySource = {"Natural Gas", "Petroleum", "Coal", "Solar PV", "Hydropower", "Wind", "Uranium"};
    static double[][] points = {{0, 1, 1, 1, 1, 1, 1/2, 1, 1, 1/2, -1, 1}, //natural gas
                                {0, 1, 1, 1, 0, 1, 1, 1, 1, 1/2, -1, 1}, // petroleum
                                {0, 1, 1, 1, 1, 1, 0, 1, 1, 1/2, -1, 1}, // coal
                                {1, 1, -1, 1, 1, 0, 0, 1, 1, 0, 1, 1/2},  // solar
                                {-1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1/2}, // hydro
                                {0, 1, -1, 1, 1, 0, 0, 0, 1, 0, 1/2, 1/2}, // wind
                                {1, 0, 1, 0, 1, 1, 0, -1, -1, 0, 1, 0}}; // uranium/nuclear
    static HashMap<String, Integer> dict = new HashMap<String, Integer>();  
    static double[] weighting = new double[12];               

    /**
     * 
     */
    @SuppressWarnings("rawtypes")
    public static void userInteraction(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\nIn Murphy’s Energy and Human Ambitions on a Finite Planet, he devises a simple scoring system to rank energy sources.");
        System.out.println("Once you have entered all 12 weightings, I will rank your energy sources based off the attributes most important to you.");
        System.out.println("And then, you will receive some feedback!\n");
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
        
        // PRINT FEEDBACK 
        String key = "";
        for (Object e : a){
            key = ((HashMap.Entry<String, Integer>) e).getKey();
            break;
        }
        if (key.equals("Natural Gas")){
            System.out.println("Great answers! Your energy ranking reflects today’s energy sources very closely.\n" + 
                "Your top choice is natural gas, which is currently an abundant, cheap, and versatile energy source\n" + 
                "Most likely your home is heated with this! Unfortunately, we will run out of natural gas within your lifetime: 60 years or less.\n" +
                "Maybe try weighting Environmental Impact higher and Abundance lower and see if you can get a renewable energy source as your highest ranked energy source!");
        } else if (key.equals("Petroleum")){
            System.out.println("Great answers! Your energy ranking reflects today’s energy sources very closely.\n" +
                "Your top choice is petroleum, which is currently an abundant, cheap, and versatile energy source.\n" +
                "Our transportation industry completely runs on this and is about 31% of our total energy consumption! Unfortunately, we will run out of petroleum within your lifetime: 60 years or less.\n" +
                "Maybe try weighting Environmental Impact higher and Abundance lower and see if you can get a renewable energy source as your highest ranked energy source!");
        } else if (key.equals("Coal")){
            System.out.println("Great answers! Your energy ranking reflects today’s energy sources very closely.\n" +
                "Your top choice is coal, which is currently an abundant, cheap, and versatile energy source. Coal accounts for 25% of primary energy usage and is most likely what helps power your home!\n" +
                "Unfortunately, this energy source is the WORST OFFENDER in terms of CO2 emissions and we will run out of coal within 110 years or sooner.\n" +
                "Maybe try weighting Environmental Impact higher and Abundance lower and see if you can get a renewable energy source as your highest ranked energy source!");
        } else if (key.equals("Solar PV")){
            System.out.println("Great answers! Your energy ranking reflects what we hope our energy source distribution will look like in the future.\n" +
                "Your top choice is solar PVs, which is a fantastic renewable energy source that is easy to put on your rooftop!\n" +
                "By covering 0.4% of Earth’s land with PV panels, we can meet our current energy demand. While Solar Power is more expensive than fossil fuels, it is much better for the environment.\n" + 
                "Its downfall is that it needs the sun to be out. What happens to our energy if there’s a natural disaster? Maybe try weighting Intermittency lower and see what happens to your ranking!");
        } else if (key.equals("Hydropower")){
            System.out.println("Great answers! Your energy ranking reflects what we hope our energy source distribution will look like in the future.\n" + 
                "Your top choice is hydropower, which is a fantastic renewable energy source that is 90% efficient! This source is less intermittent than solar and wind power, its 2 renewable counterparts.\n" + 
                "Its downfall is that there are very few locations left where we can build hydroelectric installations, so it will be difficult to incorporate more hydropower into our future energy consumption.\n" +
                "Maybe try weighting Efficiency lower and see what happens to your ranking!");
        } else if (key.equals("Wind")){
            System.out.println("Great answers! Your energy ranking reflects what we hope our energy source distribution will look like in the future.\n" + 
                "Your top choice is wind, which is a fantastic renewable energy source that it has 2% of the CO2 emissions that fossil fuels have!\n" + 
                "While wind is more expensive than fossil fuels, it is much better for the environment. Its downfall is its low land efficiency and that it needs wind to produce energy.\n" + 
                "Maybe try weighting Intermittency lower and see what happens to your ranking!");
        } else {
            System.out.println("Great answers! Your energy ranking reflects what our future energy source distribution will look like if we make developments in nuclear power.\n" + 
                "Your top choice is uranium, which is a non-renewable energy source that is not a fossil fuel! Many people are optimistic about uranium’s success despite it not having far-reaching demonstrated success.\n" + 
                "However, currently, it is far too expensive and radioactive. Maybe try weighting Demonstrated higher and see what happens to your ranking!");
        }

    }
    public static void main(String[] args) {

        matrix.userInteraction();
    }

}