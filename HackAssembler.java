import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.*;
import static java.lang.Integer.parseInt;

public class HackAssembler {
    private int currLine = 1;
    private Tables tables;
    private String noComments = "";
    private String binaryExpression = "";
    public HackAssembler(String init){
        tables = new Tables();
        remove_comments(init);
        init_symbols();
        assemble();
    }
    
    public void assemble(){
        Scanner s = new Scanner(noComments);
        String line;
        while(s.hasNext()){
            line = s.nextLine();
            if (line.charAt(0) == '@') {
                codeAddress(line);
            }
            else if(line.charAt(0) != '(') {
                codeComputation(line);
            }
            currLine++;
        }
    }
    
    public void remove_comments(String str){
        Scanner s = new Scanner(str);
        String line;
        while(s.hasNext()) {
            if(!(line = s.nextLine()).startsWith("//") && line.length() > 0) {
                while(line.charAt(0) == '\t' || line.charAt(0) == ' ' || line.charAt(0) == '\r'){
                    line = line.substring(1);
                }
                String[] arr = line.split(" ");
                noComments += (arr[0] + "\n");
            }
        }
    }
    
    public void codeAddress(String str){
        int addr;
        if(!isNumeric(str.substring(1))){
            if(!Tables.symbols.containsKey(str.substring(1))){
                Tables.runtimeSymbol(str.substring(1));
            }
            addr = Tables.symbols.get(str.substring(1));
            
        }
        else {
            addr = parseInt(str.substring(1));
        }
        StringBuilder newLine = new StringBuilder();
        for(int i = 0; i < 16; i++){
            newLine.insert(0, addr%2);
            addr/=2;
        }
        binaryExpression += newLine + "\n";
    }
    
    private boolean isNumeric(String substring) {
            try{
                Integer.parseInt(substring);
                return true;
            }
            catch (Exception e){
                return false;
            }
        
    }
    
    public void codeSymbol(String str, int index){
        str = str.substring(1);
        String[] arr = str.split("\\)");
        Tables.putSymbol(arr[0], index);
    }
    
    public void codeComputation(String str){
        StringBuilder newLine = new StringBuilder("111");
        String [] arr = str.split("[=;]");
        if(arr.length > 2 && arr[1].contains("M")) newLine.append("1");
        else if(arr.length > 1 && str.contains("=") && arr[1].contains("M")) newLine.append("1");
        else if(arr.length == 1 && str.contains("M")) newLine.append("1");
        else newLine.append("0");
        String comp;
        String dest;
        String jmp;
        if(arr.length > 2){
            jmp = arr[2];
            comp = arr[1];
            dest = arr[0];
        }
        else if(arr.length > 1 && str.contains("=")) {
            jmp = null;
            comp = arr[1];
            dest = arr[0];
        }
        else if(arr.length > 1 && str.contains(";")) {
            dest = null;
            comp = arr[0];
            jmp = arr[1];
        }
        else {
            jmp = null;
            dest = null;
            comp = arr[0];
        }
        newLine.append(Tables.comps.get(comp));
        newLine.append(Tables.dests.get(dest));
        newLine.append(Tables.jmps.get(jmp));
        binaryExpression += newLine + "\n";
    }
    
    public void init_symbols(){
        int lineCounter = 0;
        Scanner scanner = new Scanner(noComments);
        String line;
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            if(line.charAt(0) == '('){
                codeSymbol(line, lineCounter);
            }
            else
                lineCounter++;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length != 1){
            System.out.println("Couldn't start operation!");
            System.out.println("Please use the HackAssembler as following: java HackAssembler <filename>.asm");
            System.exit(-1);
        }
        StringBuilder data = new StringBuilder();
        String line;
        try{
            File f = new File(args[0]);
            Scanner s = new Scanner(f);
            while(s.hasNext()){
                line = s.nextLine();
                if(line.length() < 1) continue;
                data.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File: "+ args[0] + " not Found!\n Please make sure the file is in the same folder as the program. else: enter full path of file");
            System.exit(-1);
//            e.printStackTrace();
        }
        HackAssembler ha = new HackAssembler(data.toString());
        String[] arr = args[0].split("\\.");
        try {
            File myObj = new File(arr[0] + ".hack");
            if (myObj.createNewFile()) {
                FileWriter fw = new FileWriter(myObj.getName());
                fw.write(ha.binaryExpression);
                fw.close();
                System.out.println("Hack file successfully created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }
}
