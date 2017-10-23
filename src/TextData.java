import java.util.*;

/**
 * TextData
 */
@SuppressWarnings("serial")
public class TextData extends VectMap<Integer> implements Comparable<TextData> {

    private int label = -1;
    private String lineStr;
    private final int itemID;

    public static boolean TF_IDF = true;

    // public TextData(String str) {
    //     super();
    //     this.itemID = TextData.count;
    //     TextData.count++;
    //     this.lineStr = str;
    //     Scanner sc = new Scanner(str);
    //     while (sc.hasNext()) {
    //         Integer id = sc.nextInt();
    //         double value = (double) sc.nextInt();
    //         this.addPlus(id, value);
    //     }
    //     sc.close();
    // }

    public TextData(String str) {
        super(str);
        this.lineStr = str;
        this.itemID = 0;
    }

    public TextData(VectMap<Integer> vect)
    {
        super();
        this.putAll(vect);
        this.itemID = -1;
    }


    public TextData()
    {
        super();
        this.itemID = -1;
    }

    public void setLabel(int k) {
        this.label = k;
    }

    public int getLabel() {
        return this.label;
    }

    public void clearLabel() {
        this.label = -1;
    }

    public String getString() {
        return this.lineStr;
    }

    public String toString() {
        return "" + (this.label + 1);
    }

    public int compareTo(TextData td) {
        return this.itemID - td.itemID;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<TextData> fileToList(String filename) {
        FTimer ft = new FTimer("Reading Input File");
        Scanner sc = FTools.fileOpener(filename);
        ArrayList<TextData> retList = new ArrayList<>();
        while (sc.hasNext()) {
            retList.add(new TextData(sc.nextLine()));
        }
        sc.close();
        ft.time();

        // TextData docCount = new TextData();
        // docCount.setAsOccurence(retList);
        // for(Integer key : docCount.keySet())
        // {
        //     double value = docCount.getValue(key);
        //     System.out.println(key + " , " + value + "\n");
        // }

        if (TF_IDF) {
            FTimer ft2 = new FTimer("Normalizing Data");
            VectMap idfMap = new VectMap<>();
            idfMap.setAsIdf(retList);
            for (TextData vect : retList) {
                vect.tfIdf(idfMap);
            }
            ft2.time();
        }
        return retList;
    }
}