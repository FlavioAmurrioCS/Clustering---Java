import java.util.ArrayList;

/**
 * Trial
 */
public class Trial {

    public static void main(String args[]) {

    }

    public static void idtotxt() {
        ArrayList<String> features = FTools.fileToList(Miner.FEATURES_FILE);
        features.add(0, "null");
        ArrayList<String> lines = FTools.fileToList(Miner.INPUT_FILE);
        ArrayList<String> outLines = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split(" ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tokens.length; i += 2) {
                int iD = Integer.parseInt(tokens[i]);
                int count = Integer.parseInt(tokens[i + 1]);
                String word = features.get(iD);
                for (int j = 0; j < count; j++) {
                    sb.append(word + " ");
                }
            }
            outLines.add(sb.toString());
        }
        FTools.listToFile(outLines, Miner.MAP_FILE);
    }
}