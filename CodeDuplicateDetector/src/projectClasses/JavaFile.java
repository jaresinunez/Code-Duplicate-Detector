/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectClasses;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;

/**
 * @author jares
 * This object represents a .java file uploaded by the user
 */
public class JavaFile extends Files{
    public JavaFile(String n){
        super(n);
    }
    //String toReturn = "";
    ArrayList<String> toReturn = new ArrayList<>();
    
    // @Override
    public String scan(ArrayList<String> fileData) {
        //---------------------------
        //TODO: scan the .java file for duplicates and add to the file's duplicates list
        //ArrayList<ArrayList<String>> fileData = getFileData();
        //        System.out.println(fileData);
        //---------------------------
        // ArrayList<ArrayList<String>> fileData = getFileData();
        // ArrayList<String> fileData = new ArrayList<String>();
        
        for (int i = 0; i < fileData.size(); i++) {
            for (int k = i + 1; k < fileData.size(); k++) {
                if (fileData.get(i) != fileData.get( k)){
                    System.out.println("at i: " + fileData.get( i));
                    System.out.println("at k: " + fileData.get( k)); 
                    //System.out.println(compute_Levenshtein_distanceDP(test.get( i), test.get( k)));
                    System.out.println("Your Strings are Matched="+percentage(fileData.get( i), fileData.get( k))+"%");
                    toReturn.add("\nYour Strings are Matched="+ percentage(fileData.get( i), fileData.get( k))+"%");
                }
            }
        }
        String strReturn = Arrays.toString(toReturn.toArray());
        return strReturn;
    }
    
    
    public static int percentage(String s, String t) {
        int tWord = wordCount(s);
        if (tWord != 0){
            int total = 100;
            int percentW = total / tWord;
            int initPerWord = 0;

            if (!s.equals(t)) {
                for (int i = 1; i <= tWord; i++) {
                    if (simMatch(splitStr(s, i), t) == 1) {
                        initPerWord = ((percentW * (total - 10)) / total) + initPerWord;
                    } else if (aTotalMatch(splitStr(s, i), t) == 1) {
                        initPerWord = ((percentW * (total - 20)) / total) + initPerWord;
                    } else if (aMatch(splitStr(s, i), t) == 1) {
                        initPerWord = ((percentW * (total - 30)) / total) + initPerWord;
                    } else {
                        initPerWord = ((percentW * smart_match(splitStr(s, i), t)) / total) + initPerWord;
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

        //----------Work Body----------//
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
        //---------END---------------//
        if (len == 0) {
            x = 0;
        }
        return x;
    }

    public static int simMatch(String s, String t) {
        int x = 0;
        String tempt;
        int len = s.length();


        //----------Work Body----------//
        for (int i = 1; i <= wordCount(t); i++) {
            tempt = splitStr(t, i);
            if (tempt.length() == s.length()) {
                if (s.contains(tempt)) {
                    x = 1;
                    break;
                }
            }
        }
        //---------END---------------//
        if (len == 0) {
            x = 0;
        }
        return x;
    }
    
    public static int smart_match(String ts, String tt) {

        char[] s = new char[ts.length()];
        s = ts.toCharArray();
        char[] t = new char[tt.length()];
        t = tt.toCharArray();


        int slen = s.length;
        //number of 3 combinations per word//
        int combs = (slen - 3) + 1;
        //percentage per combination of 3 characters//
        int ppc = 0;
        if (slen >= 3) {
            ppc = 100 / combs;
        }
        //initialising an integer to store the total % this class genrate//
        int x = 0;
        //declaring a temporary new source char array
        char[] ns = new char[3];
        //check if source char array has more then 3 characters//
        if (slen < 3) {
            
        } 
        else {
            for (int i = 0; i < combs; i++) {
                for (int j = 0; j < 3; j++) {
                    ns[j] = s[j + i];
                }
                if (cross_full_match(ns, t) == 1) {
                    x = x + 1;
                }
            }
        }
        x = ppc * x;
        return x;
    }
    
    /**
     *
     * @param s
     * @param t
     * @return
     */
    public static int  cross_full_match(char[] s, char[] t) {
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
                        x = 0; // character gots not matches
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
    
    public ArrayList<String> findVars(String line){
        ArrayList<String> vars = new ArrayList<>();
        int start = -1, end = -1;
        Boolean parenthasesOpen = false;
        for(int i = 0; i < line.length(); i++){
            if(!parenthasesOpen && line.charAt(i) == '('){
                parenthasesOpen = true;
                start = i;
            } else if(parenthasesOpen && line.charAt(i) == ')'){
                parenthasesOpen = false;
                end = i;
            }
            
            if(start > -1 && end > -1){
                vars.add(line.substring(start, end));
                start = -1;
                end = -1;
            }
        }
        return vars;
    }
        
    public String createMethod(String availability, String returnType, String name, ArrayList<String> params, String content){
        String newMethod = availability + " " + returnType + " " + name + "(";
        
        for(int i = 0; i < params.size(); i++){
            if(i < params.size() - 1)
                newMethod = newMethod.concat(params.get(i) + ", ");
            else 
                newMethod = newMethod.concat(params.get(i));
        }
        
        newMethod = newMethod.concat("){\n");
        newMethod = newMethod.concat(content);
        
        return newMethod;
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
