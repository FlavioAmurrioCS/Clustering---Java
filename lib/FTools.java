import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Tools
 */
public class FTools {

    public static boolean SHOW_LOG = true;

    public static Scanner fileOpener(String filename) {
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);
            if(SHOW_LOG)
                System.out.println("Openning " + filename + "...");
            return sc;

        } catch (Exception e) {
            System.out.println("FileNotFount : " + filename);
        }
        return null;
    }

    public static ArrayList<String> fileToList(String filename) {
        Scanner sc = fileOpener(filename);
        ArrayList<String> arrList = new ArrayList<>();
        while (sc.hasNext()) {
            arrList.add(sc.nextLine());
        }
        return arrList;
    }

    public static HashSet<String> fileToHashSet(String filename) {
        HashSet<String> hSet = new HashSet<>();
        Scanner sc = FTools.fileOpener(filename);
        while (sc.hasNext()) {
            hSet.add(sc.next());
        }
        sc.close();
        return hSet;
    }

    public static PrintWriter filePrinter(String filename) {
        try {
            File file = new File(filename);
            PrintWriter pw = new PrintWriter(file);
            if(SHOW_LOG)
                System.out.println("Writing " + filename + "...");
            return pw;
        } catch (Exception e) {
            System.out.println("Writing Error");

        }
        return null;
    }

    public static void listToFile(ArrayList<?> arrList, String filename) {
        PrintWriter outStream = filePrinter(filename);
        for (int i = 0; i < arrList.size(); i++) {
            outStream.println(arrList.get(i).toString());
        }
        outStream.close();
    }

    public static void hashSetToFile(HashSet<String> hSet, String filename) {
        ArrayList<String> arrList = new ArrayList<>(hSet);
        Collections.sort(arrList);
        listToFile(arrList, filename);
    }

    public static ArrayList<String> stringToList(String str) {
        Scanner sc = new Scanner(str);
        ArrayList<String> retList = new ArrayList<>();
        while (sc.hasNext()) {
            retList.add(sc.next());
        }
        sc.close();
        return retList;
    }

    public static void tittleMaker(String str) {
        System.out.println("-----------------------------" + str + "-----------------------------");
    }

    public static ArrayList<String> fileToScoreList(String filename) {
        Scanner sc = fileOpener(filename);
        ArrayList<String> retList = new ArrayList<>();
        while (sc.hasNext()) {
            retList.add(sc.next());
            sc.nextLine();
        }
        return retList;
    }

    public static void fileComparator(String actual, String prediction) {
        ArrayList<String> act = fileToScoreList(actual);
        ArrayList<String> pre = fileToScoreList(prediction);
        int correct = 0;
        if (act.size() != pre.size()) {
            System.exit(0);
        }
        for (int i = 0; i < act.size(); i++) {
            if (act.get(i).equals(pre.get(i))) {
                correct++;
            }
        }
        System.out.println(correct + " out of " + act.size());
    }

    public static ArrayList<Integer> fileToIntList(String filename) {
        Scanner sc = fileOpener(filename);
        ArrayList<Integer> retList = new ArrayList<>();
        while (sc.hasNextInt()) {
            retList.add(sc.nextInt());
        }
        return retList;

    }

    public static void appendFile(String filename, String text) {
        try {

            FileWriter fw = new FileWriter(filename, true);
            fw.write("\n" + text);//appends the string to the file
            fw.close();
        } catch (Exception e) {

        }
    }

    public static ArrayList<String> getFileList(String directory) {
        ArrayList<String> fList = new ArrayList<>();
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fList.add(listOfFiles[i].getName());
            }
        }
        return fList;
    }

    // public static void underSampling(String filename) {
    //     ArrayList<Gene> gList = Gene.fileToGeneList(filename);
    //     ArrayList<Gene> posList = new ArrayList<>();
    //     ArrayList<Gene> negList = new ArrayList<>();
    //     for (Gene gn : gList) {
    //         if (gn.score == 1) {
    //             posList.add(gn);
    //         } else {
    //             negList.add(gn);
    //         }
    //     }
    //     Random rd = new Random(System.currentTimeMillis());
    //     while (posList.size() != negList.size()) {
    //         int index = rd.nextInt(negList.size());
    //         negList.remove(index);
    //     }
    //     posList.addAll(negList);
    //     listToFile(posList, "./res/out/underSampleTrain.txt");

    // }

    // @SuppressWarnings("unchecked")
    // public static ArrayList<Object> fileToList(String filename, Class cls)
    // {
    //     ArrayList retList = new ArrayList<>();
    //     Constructor constructor = null;
    //     Scanner sc = FTools.fileOpener(filename);
    //     try {
    //         constructor = cls.getConstructor(String.class);
    //         while (sc.hasNext()) {
    //             retList.add(constructor.newInstance(sc.nextLine()));
    //         }
    //     } catch (Exception e) {
    //         //TODO: handle exception
    //     }
    //     sc.close();
    //     return retList;
    // }

    // public static void fileToList(String filename, Class cls, ArrayList<?> retList)
    // {
    //     Constructor constructor = null;
    //     Scanner sc = FTools.fileOpener(filename);
    //     try {
    //         constructor = cls.getConstructor(String.class);
    //         while (sc.hasNext()) {
    //             retList.add(constructor.newInstance(sc.nextLine()));
    //         }
    //     } catch (Exception e) {
    //         //TODO: handle exception
    //     }
    //     sc.close();
    // }

    public void textify(String input, String features, String output)
    {
        ArrayList<String> strList = new ArrayList<>();
        Scanner sc = fileOpener(features);
        while(sc.hasNext()){
            strList.add(sc.nextLine());
        }
        sc.close();

        ArrayList<String> lines = new ArrayList<>();
        sc = fileOpener(input);


    }

    public static HashMap<String, Integer> fileToHashMap(String filename){
        ArrayList<String> sList = fileToList(filename);
        HashMap<String, Integer> fMap = new HashMap<>();
        for(int i = 0; i < sList.size(); i++)
        {
            fMap.put(sList.get(i), i);
        }
        return fMap;
    }

}