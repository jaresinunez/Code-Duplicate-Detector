/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectClasses;

/**
 * @author jares
 * This object will represent an instance of duplicate code and
 * will store the type of duplicate that occurred
 */
class Duplicate {
    Boolean resolve;
    String dupe = "";
       
    public Duplicate(Boolean fix, String duplicate) {
        this.resolve = fix; 
        dupe = duplicate;
    }
    
    public Duplicate(String duplicate){
        dupe = duplicate;
    }
}
