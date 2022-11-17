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
}
