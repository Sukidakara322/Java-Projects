package WorkingWithFile;

import WorkingWithoutFile.Decision;
import WorkingWithoutFile.SearchByDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class SearchingDate {
    public static List<Decision> searchingDate(String filePath){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Scanner scanner = new Scanner(System.in);
        List<Decision> decisionList = ReadDecisions_fromFile.ReadDecisions(filePath);
        while(true){
            try{
                System.out.println("Give a date to search a decision: ");
                String date = scanner.nextLine();
                List<Decision> ResultsByDate = decisionList.stream()
                        .filter(new SearchByDate(LocalDate.parse(date, formatter))::matches)
                        .toList();
                if(ResultsByDate.isEmpty()){
                    System.out.println("There is no decision from this date");
                }else{
                    System.out.println("Results of searching by date: ");
                    ResultsByDate.forEach(System.out::println);
                }
                return ResultsByDate;
            }catch (java.time.format.DateTimeParseException e){
                System.out.println("Incorrect format of parsed date.");
                System.out.println("Please enter date at yyyy-MM-dd format.");
            }
        }
    }
}