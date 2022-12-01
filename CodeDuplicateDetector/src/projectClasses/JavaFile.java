package projectClasses;
import java.util.ArrayList;


/**
 * @author jares
 * This object represents a .java file uploaded by the user
 */
public class JavaFile extends Files{
    public JavaFile(String n){
        super(n);
    }
    
    
    /**
     * Finds where parentheses open and close from code submitted
     * 
     * @param line String line of codes
     * @return new String lines
     */
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
        
    /**
     * Creates a new method based on the inside of the lines of code that 
     * were detected as duplicated
     * 
     * @param availability
     * @param returnType
     * @param name
     * @param params
     * @param content
     * @return 
     */
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
