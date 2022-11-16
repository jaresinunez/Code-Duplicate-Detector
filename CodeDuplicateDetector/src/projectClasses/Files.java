/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectClasses;
import java.util.List;

/**
 * @author jares
 * This object represents the file the user uploads and will track the 
 * name, language, and list of found duplicates
 */
public class Files{
    String name = null;
    List<Duplicate> duplicateList = null;
//    
//    Files () 
//    {
//    
//    }
    
    public Files (String n) {
        name = n;
    }
    
    public void Download(String newFileName) 
    {
        //TODO: Allow user to download new file under specified name
        
    }
    
    public void scan() {
        
    }
    
    public void setName(String n) { this.name = n; }
    public String getName() { return this.name; }
    
    public void addDuplicate(Duplicate dupe) { duplicateList.add(dupe); }
    public void removeDuplicate(int index) { duplicateList.remove(index); }
    public void resolveDuplicates() {
        //TODO: iterate through list of duplicates prompting the user to indivate
        //weather or not they would like to resolve duplicate at each index and doing so
    }
   
}
