package WorkingWithFile;
import WorkingWithoutFile.Decision;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class WriteDecisions_toFile {
    public static void writeDecisionsToFile(String filePath, List<Decision> decisionList) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(decisionList);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
