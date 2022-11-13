/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jares
 * This object represents a .java file uploaded by the user
 */
public class JavaFile extends Files{
    public JavaFile(String n){
        super(n);
    }
    
    @Override
    public void scan() {
        //TODO: scan the .java file for duplicates and add to the file's duplicates list
        ArrayList<ArrayList<String>> fileData = getFileData();
        System.out.println(fileData);
    }
    
    public ArrayList<ArrayList<String>> getFileData(){
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
    }
}
