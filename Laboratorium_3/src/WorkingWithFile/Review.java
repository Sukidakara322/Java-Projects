package WorkingWithFile;
import WorkingWithoutFile.Decision;
import java.util.List;
public class Review {
    public static void review(String filePath){
        List<Decision> decisionList = ReadDecisions_fromFile.ReadDecisions(filePath);
        for(Decision decision: decisionList){
            System.out.println(decision);
        }
    }
}
