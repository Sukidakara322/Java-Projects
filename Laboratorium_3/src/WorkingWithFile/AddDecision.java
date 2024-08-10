package WorkingWithFile;
import WorkingWithoutFile.Decision;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class AddDecision {
    public static Decision addDecision (String filePath) {
        List<Decision> decisionList = ReadDecisions_fromFile.ReadDecisions(filePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Scanner scanner = new Scanner(System.in);
        while(true){
            try {
                System.out.print("Date (yyyy-MM-dd): ");
                String Date = scanner.nextLine();
                LocalDate date = LocalDate.parse(Date, formatter);
                System.out.print("Component: ");
                String Component = scanner.nextLine();
                System.out.print("Person: ");
                String Person = scanner.nextLine();
                System.out.print("Description: ");
                String Description = scanner.nextLine();
                Integer Importance;
                while(true){
                    try{
                        System.out.print("Importance(1 - 3): ");
                        Importance = scanner.nextInt();
                        if(Importance < 1 || Importance > 3){
                            System.out.println("Not valid option for importance.");
                            System.out.println("Please enter an option from (1 - 3) range.");
                        }else{
                            break;
                        }
                    }catch (InputMismatchException e){
                        System.out.println("Incorrect format dor importance, please enter a numeric option.");
                        scanner.nextLine();
                    }
                }
                Decision decision = new Decision(date, Component, Person, Importance, Description);
                decisionList.add(decision);
                WriteDecisions_toFile.writeDecisionsToFile(filePath, decisionList);
                return decision;
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Incorrect format of parsed date.");
                System.out.println("Please enter date at yyyy-MM-dd format.");
            }
        }
    }
}

