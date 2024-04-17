import java.util.Scanner;

public class matrix {

    // TODO: add all the attributes
    static String[] attributes = {"Abundance", "Difficulty", "Intermittency"};

    public static void userInteraction(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ADD INSTRUCTIONS");
        double[] weighting = new double[12];
        double total_weight = 0;
        int index = 0;
        for (int i=0; i < attributes.length; i++){
            System.out.println("What is your weighting for " + attributes[i] + "?");
            weighting[i] = scanner.nextDouble();
            total_weight += weighting[i];
        }
        while(total_weight != 12){
            double distanceToTwelve = total_weight - 12;
            System.out.println("\nYour total weight does not add to 12.");
            if (distanceToTwelve < 0){
                System.out.println("You need to add a total of " + Math.abs(distanceToTwelve) + " more to reach 12.");
            } else {
                System.out.println("You need to add a total of " + distanceToTwelve + " more to reach 12.");
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
            // for (int i=0; i < attributes.length; i++){
            //     System.out.println("What is your weighting for " + attributes[i]);
            //     weighting[i] = scanner.nextInt();
            //     total_weight += weighting[i];
            // }
        }
        scanner.close();
    }
    public static void main(String[] args) {
        matrix.userInteraction();
    }

}