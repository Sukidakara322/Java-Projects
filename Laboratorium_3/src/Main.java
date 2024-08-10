import java.util.InputMismatchException;
import java.util.Scanner;

import WorkingWithFile.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "Decisions.ser";
        Scanner scanner = new Scanner(System.in);
        Boolean numberB = true;
        try{
            while(numberB){
                System.out.println("--------------------------------------");
                System.out.println("|Select one option from this list:   |");
                System.out.println("--------------------------------------");
                System.out.println("|1 - add a decision                  |");
                System.out.println("|2 - review all decisions            |");
                System.out.println("|3 - search a decision by person     |");
                System.out.println("|4 - search a decision by date       |");
                System.out.println("|5 - search a decision by component  |");
                System.out.println("|6 - search a decision by importance |");
                System.out.println("--------------------------------------");
                Integer numberA = scanner.nextInt();
                switch (numberA){
                    case 1:
                        AddDecision.addDecision(filePath);
                        break;
                    case 2:
                        Review.review(filePath);
                        break;
                    case 3:
                        SearchingPerson.searchingPerson(filePath);
                        break;
                    case 4:
                        SearchingDate.searchingDate(filePath);
                        break;
                    case 5:
                        SearchingComponent.searchingComponent(filePath);
                        break;
                    case 6:
                        SearchingImportance.searchingImportance(filePath);
                }
                System.out.println("------------------------------------");
                System.out.println("|     Do you want to continue?     |");
                System.out.println("------------------------------------");
                System.out.println("|     1 - Yes     |     0 - No     |");
                System.out.println("------------------------------------");
                Integer option = scanner.nextInt();
                if(option == 0){
                    numberB = false;
                }
                else if(option == 1){
                    numberB = true;
                }
                else{
                    System.out.println("Not correct option");
                    break;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Not correct input, please enter a numeric option");
            scanner.nextLine();
        }
    }
}