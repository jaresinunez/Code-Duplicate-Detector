package projectClasses;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author jares
 * This object represents the file the user uploads and will track the 
 * name, language, and list of found duplicates
 */
public class Files{
    String name = null;
    ArrayList<Duplicate> duplicateList = new ArrayList<>();
    ArrayList<String> toReturn = new ArrayList<>();
    ArrayList<ArrayList<String>> uniqueLinesAndCounts = new ArrayList<>();
    
    public Files (String n) {
        name = n;
    }
    
    
    public void setName(String n) { this.name = n; }
    public String getName() { return this.name; }
    
    public void addDuplicate(Duplicate dupe) { duplicateList.add(dupe); }
    public void removeDuplicate(int index) { duplicateList.remove(index); }
    
    /**
     * Method scans or reads through the lines of code (string) and 
     * calls other methods that uses an algorithm similar to the LevenshteinDistance.
     * It also displays results on the duplication amount and the similarity 
     * percent found based on ~40% or >70% over duplicate.
     * 
     * @param fileData src lines of code 
     * @return the String code
     */
    public String scan(ArrayList<String> fileData) {

        //goes through each line 
        for (int i = 0; i < fileData.size(); i++) {
           
            for (int k = i + 1; k < fileData.size(); k++) {
              
                if (fileData.get(i) != fileData.get( k)){

                    int DupePerc = percentage(fileData.get( i), fileData.get( k));
                    
                    //if % is between 48% and < 70%
                    if (DupePerc >= 48 && DupePerc < 70){
                    toReturn.add("Line "+(i+1)+": "+fileData.get( i) +"\n" + "Line "+(k+1)+": "+fileData.get( k) 
                            +"\nDuplication of "+percentage(fileData.get( i), fileData.get( k)) +"%"
                            + "\n\n-> RECOMMENDED TO MAKE CHANGES"
                            +"\n---------------------------------------------------------------------------------------------------\n");
                    }
                    
                    //if % is > or = to 70%
                    if (DupePerc >= 70){
                    toReturn.add("Line "+(i+1)+": "+fileData.get( i) +"\n" + "Line "+(k+1)+": "+fileData.get( k) 
                            +"\nDuplication of "+percentage(fileData.get( i), fileData.get( k)) +"%"
                            + "\n\n-> RECOMMENDED TO MAKE A METHOD"
                            +"\n---------------------------------------------------------------------------------------------------\n");
                    }
                    
                }
            }
        }
        //Replaces/removes the symbols from the string
        String strReturn = Arrays.toString(toReturn.toArray())
                .replace("[", "")
                .replace("]", "")
                .replace(",", "");
        return strReturn;
    }
    
    /**
     * 
     * @param data
     * @return 
     */
    public String alternateScan(ArrayList<String> data){

        for(int i = 0; i < data.size(); i++){
            data.set(i,data.get(i).trim());
            if(data.get(i).contains("//")){ // removing comments from the lines
                data.set(i, data.get(i).substring(0, data.get(i).indexOf("//")));
            }
            if(uniqueLinesAndCounts.size() == 0){
                ArrayList<String> tempU = new ArrayList<>();
                tempU.add(data.get(i));
                uniqueLinesAndCounts.add(tempU);
                ArrayList<String> tempN = new ArrayList<>();
                tempN.add("1");
                uniqueLinesAndCounts.add(tempN);
            }
            else if(!uniqueLinesAndCounts.get(0).contains(data.get(i))){
                uniqueLinesAndCounts.get(0).add(data.get(i));
                uniqueLinesAndCounts.get(1).add("1");
            }
            else if(data.get(i).equals("}") || data.get(i).equals("{")){
                //do nothing these should not be added to the lines of code list
            }
            else{
                int indexOfLine = uniqueLinesAndCounts.get(0).indexOf(data.get(i));
                int currentCount = Integer.parseInt(uniqueLinesAndCounts.get(1).get(indexOfLine));
                uniqueLinesAndCounts.get(1).set(indexOfLine, Integer.toString(currentCount + 1));
            }
           
        }
        

        
        String uniqueLineCountsReturn = "Line of code and its recorded frequency\n";
        for(int i = 0; i < uniqueLinesAndCounts.get(0).size(); i++){

            uniqueLineCountsReturn = uniqueLineCountsReturn.concat("----------------------------------------------------------\n");
            uniqueLineCountsReturn = uniqueLineCountsReturn.concat(uniqueLinesAndCounts.get(0).get(i) + "\nRepeated: " 
                    + uniqueLinesAndCounts.get(1).get(i)+"\n");
        }

        return uniqueLineCountsReturn;
    }
    
    public static int percentage(String s, String t) {
        int totalWord = wordCount(s);
        
        if (totalWord != 0)
        {
            int total = 100;
            int percentW = total / totalWord;
            int initPerWord = 0;

            if (!s.equals(t)) {
                for (int i = 1; i <= totalWord; i++) {
                    if (simMatch(splitStr(s, i), t) == 1) {
                        initPerWord = ((percentW * (total - 10)) / total) + initPerWord;
                    } else if (aTotalMatch(splitStr(s, i), t) == 1) {
                        initPerWord = ((percentW * (total - 20)) / total) + initPerWord;
                    } else if (aMatch(splitStr(s, i), t) == 1) {
                        initPerWord = ((percentW * (total - 30)) / total) + initPerWord;
                    } else {
                        initPerWord = ((percentW * quickMatch(splitStr(s, i), t)) / total) + initPerWord;
                    }
                }
            } else {
                initPerWord = 100;
            }
            return initPerWord;
        }
        else{
            return 0;
        }
    }

    public static int aMatch(String s, String t) {
        int x = 0;
        if (t.contains(s)) {
            x = 1;
        }
        return x;
    }
    
    public static int aTotalMatch(String s, String t) {
        int x = 0;
        String tempt;
        int len = s.length();

        for (int i = 1; i <= wordCount(t); i++) {
            tempt = splitStr(t, i);
            if (tempt.length() >= s.length()) {
                tempt = tempt.substring(0, len);
                if (s.contains(tempt)) {
                    x = 1;
                    break;
                }
            }
        }

        if (len == 0) {
            x = 0;
        }
        return x;
    }

    public static int simMatch(String s, String t) {
        int x = 0;
        String tempTStr;
        int len = s.length();

        for (int i = 1; i <= wordCount(t); i++) {
            tempTStr = splitStr(t, i);
            if (tempTStr.length() == s.length()) {
                if (s.contains(tempTStr)) {
                    x = 1;
                    break;
                }
            }
        }

        if (len == 0) {
            x = 0;
        }
        return x;
    }
    
    public static int quickMatch(String ts, String tt) {

        char[] s = new char[ts.length()];
        s = ts.toCharArray();
        char[] t = new char[tt.length()];
        t = tt.toCharArray();

        int strLen = s.length;
        //looks at 3 chars combinations that match per word
        int comboArr = (strLen - 4) + 1;
        //combination percentage 
        int perc = 0;
        if (strLen >= 4) {
            perc = 100 / comboArr;
        }
        
        int totPerc = 0; //total percentage
        char[] tempSrc = new char[4];
        //check if source char array has more then 3 characters
        if (strLen < 4) {
            
        } 
        else {
            for (int i = 0; i < comboArr; i++) {
                for (int j = 0; j < 4; j++) {
                    tempSrc[j] = s[j + i];
                }
                if (charCompMatch(tempSrc, t) == 1) {
                    totPerc = totPerc + 1;
                }
            }
        }
        totPerc = perc * totPerc;
        return totPerc;
    }
    
    /**
     *
     * @param s
     * @param t
     * @return
     */
    public static int  charCompMatch(char[] s, char[] t) {
        int z = t.length - s.length;
        int x = 0;
        if (s.length > t.length) {
            return x;
        } 
        else {
            for (int i = 0; i <= z; i++) {
                for (int j = 0; j <= (s.length - 1); j++) {
                    if (s[j] == t[j + i]) {
                       
                        x = 1;
                    } else {
                        x = 0; // char has no similar or dupe matches
                        break;
                    }
                }
                if (x == 1) {
                    break;
                }
            }
        }
        return x;
    }

    public static String splitStr(String s, int n) {
        int index;
        String temp;
        temp = s;
        String temp2 = null;

        int temp3 = 0;

        for (int i = 0; i < n; i++) {
            int strlen = temp.length();
            index = temp.indexOf(" ");
            if (index < 0) {
                index = strlen;
            }
            temp2 = temp.substring(temp3, index);
            temp = temp.substring(index, strlen);
            temp = temp.trim();
        }
        return temp2;
    }
    
    public static int wordCount(String s) {
        int x = 1;
        int c;
        s = s.trim();
        if (s.isEmpty()) {
            x = 0;
        } 
        else {
            if (s.contains(" ")) {
                for (;;) {
                    x++;
                    c = s.indexOf(" ");
                    s = s.substring(c);
                    s = s.trim();
                    if (s.contains(" ")) {
                    } else {
                        break;
                    }
                }
            }
        }
        return x;
    }
    
    public ArrayList<ArrayList<String>> getFileData(){
        //-------------------------------------------------
        try {
            ArrayList<ArrayList<String>> fileData = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(this.name));
            String st;
            
            while ((st = br.readLine()) != null){ // This is a line in the file
                ArrayList<String> characters = new ArrayList<>();
                for(int i = 0; i < st.length(); i++){ // This will go through each character in the line
                    if (st.charAt(i) != ' ') {
                        characters.add(Character.toString(st.charAt(i)));
                    }
                }
                fileData.add(characters);
            }
            return fileData;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(JavaFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        //---------------------------------------------------------------
    }  
}
