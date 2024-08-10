package WorkingWithFile;
import WorkingWithoutFile.Decision;
import WorkingWithoutFile.SearchBy;
import WorkingWithoutFile.SearchByComponent;

import java.util.List;
import java.util.Scanner;

public class SearchingComponent {
    public static List<Decision> searchingComponent(String filePath){
        System.out.println("Give a component to search a decision: ");
        Scanner scanner = new Scanner(System.in);
        String component = scanner.nextLine();
        List<Decision> decisionList = ReadDecisions_fromFile.ReadDecisions(filePath);
        List<Decision> ResultsByComponent = decisionList.stream()
                .filter(new SearchByComponent(component)::matches)
                .toList();
        if(ResultsByComponent.isEmpty()){
            System.out.println("There are no decisions for this component");
        }else{
            System.out.println("Results of searching by component: ");
            ResultsByComponent.forEach(System.out::println);
        }
        return ResultsByComponent;
    }
}

