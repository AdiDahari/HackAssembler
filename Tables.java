import java.util.HashMap;
public class Tables {
    static int symbolCount;
    public static HashMap<String, Integer> symbols;
    public static HashMap<String, String> comps;
    public static HashMap<String, String> dests;
    public static HashMap<String, String> jmps;
    public static void putSymbol(String str, int index){
        if(symbols.containsKey(str)) return;
        symbols.put(str, index);
    }
    public static void runtimeSymbol(String str){
        symbols.put(str, symbolCount);
        ++symbolCount;
    }
    public Tables(){
        symbols = new HashMap<String, Integer>();
        symbols.put("R0", 0);
        symbols.put("R1", 1);
        symbols.put("R2", 2);
        symbols.put("R3", 3);
        symbols.put("R4", 4);
        symbols.put("R5", 5);
        symbols.put("R6", 6);
        symbols.put("R7", 7);
        symbols.put("R8", 8);
        symbols.put("R9", 9);
        symbols.put("R10", 10);
        symbols.put("R11", 11);
        symbols.put("R12", 12);
        symbols.put("R13", 13);
        symbols.put("R14", 14);
        symbols.put("R15", 15);
        symbols.put("SCREEN", 16384);
        symbols.put("KBD", 24576);
        symbolCount = 16;
        comps = new HashMap<String, String>();
        comps.put("0", "101010");
        comps.put("1", "111111");
        comps.put("-1", "111010");
        comps.put("D", "001100");
        comps.put("A", "110000");
        comps.put("!D", "001101");
        comps.put("!A", "110001");
        comps.put("-D", "001111");
        comps.put("-A", "110011");
        comps.put("D+1", "011111");
        comps.put("A+1", "110111");
        comps.put("D-1", "001110");
        comps.put("A-1", "110010");
        comps.put("D+A", "000010");
        comps.put("D-A", "010011");
        comps.put("A-D", "000111");
        comps.put("D&A", "000000");
        comps.put("D|A", "010101");
        comps.put("M", "110000");
        comps.put("!M", "110001");
        comps.put("-M", "110011");
        comps.put("M+1", "110111");
        comps.put("M-1", "110010");
        comps.put("D+M", "000010");
        comps.put("D-M", "010011");
        comps.put("M-D", "000111");
        comps.put("D&M", "000000");
        comps.put("D|M", "010101");
    
        dests = new HashMap<String, String>();
        dests.put(null, "000");
        dests.put("M", "001");
        dests.put("D", "010");
        dests.put("MD", "011");
        dests.put("A", "100");
        dests.put("AM", "101");
        dests.put("AD", "110");
        dests.put("AMD", "111");
    
        jmps = new HashMap<String, String>();
        jmps.put(null, "000");
        jmps.put("JGT", "001");
        jmps.put("JEQ", "010");
        jmps.put("JGE", "011");
        jmps.put("JLT", "100");
        jmps.put("JNE", "101");
        jmps.put("JLE", "110");
        jmps.put("JMP", "111");
    }
    
}
