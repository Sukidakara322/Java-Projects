package WorkingWithFile;
import WorkingWithoutFile.Decision;
import WorkingWithoutFile.SearchByImportance;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SearchingImportance {
    public static List<Decision> searchingImportance(String filePath){
        Scanner scanner = new Scanner(System.in);
        List<Decision> decisionList = ReadDecisions_fromFile.ReadDecisions(filePath);
        while(true){
            try{
                System.out.println("Give a level of importance to search by: ");
                Integer importance = scanner.nextInt();
                if(importance < 1 || importance > 3){
                    System.out.println("Not a valid option for importance. Please enter a correct one.");
                    scanner.nextLine();
                }else{
                    List<Decision> ResultsByImportance = decisionList.stream()
                            .filter(new SearchByImportance(importance)::matches)
                            .toList();
                    if(ResultsByImportance.isEmpty()){
                        System.out.println("There are no decisions for this importance");
                    }else{
                        System.out.println("Results of searching by importance = " + importance + ": ");
                        ResultsByImportance.forEach(System.out::println);
                    }
                    return ResultsByImportance;
                }
            }catch(InputMismatchException e){
                System.out.println("Incorrect format of parsed importance.");
                System.out.println("Please enter importance at (1 - 3) format.");
                scanner.nextLine();
            }
        }

    }
}
