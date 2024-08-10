package WorkingWithFile;

import WorkingWithoutFile.Decision;
import WorkingWithoutFile.SearchByPerson;
import java.util.List;
import java.util.Scanner;

public class SearchingPerson {
    public static List<Decision> searchingPerson(String filePath){
        System.out.println("Give a person to search a decision: ");
        Scanner scanner = new Scanner(System.in);
        String person = scanner.nextLine();
        List<Decision> decisionList = ReadDecisions_fromFile.ReadDecisions(filePath);
        List<Decision> ResultsByPerson = decisionList.stream()
                .filter(new SearchByPerson(person)::matches)
                .toList();
        if(ResultsByPerson.isEmpty()){
            System.out.println("There are no decisions with this person");
        }else{
            System.out.println("Results of searching by name: ");
            ResultsByPerson.forEach(System.out::println);
        }
        return ResultsByPerson;
    }
}
