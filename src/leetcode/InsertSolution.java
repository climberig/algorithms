package leetcode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
public class InsertSolution{
    static String WORKING_DIR = "/home/igor/projects/algos/src/leetcode";
    static String SOLUTION_PREFIX = "    static class s";
    static Logger log = Logger.getLogger("InsertSolution");

    public static void main(String[] args) throws IOException{
        log.info("Reading a.java");
        List<String> insertLines = solutionFromA();
        int problemNo = problemNo(insertLines.get(0));
        String updateFile = "p" + (problemNo / 100) + ".java";
        log.info(String.format("Inserting problem %s into %s", problemNo, updateFile));
        List<String> oldLines = Files.readAllLines(Path.of(WORKING_DIR, updateFile)), newLines = new ArrayList<>();
        boolean inserted = false;
        for(String line : oldLines){
            if(!inserted && (problemNo(line) >= problemNo || line.startsWith("}"))){
                if(problemNo(line) == problemNo){
                    log.warning(String.format("solution s%s already exists in %s", problemNo, updateFile));
                    return;
                }
                log.info("Inserting before " + line);
                newLines.addAll(insertLines);
                if(line.startsWith("}"))
                    newLines.add(0, "");
                else newLines.add("");
                inserted = true;
            }
            newLines.add(line);
        }
        log.info("Updating the file...");
        Files.write(Path.of(WORKING_DIR, updateFile), newLines);
        log.info("Clearing a.java");
        Files.writeString(Path.of(WORKING_DIR, "a.java"), """
                package leetcode;
                                
                public class a{
                                
                }
                """);
    }

    static List<String> solutionFromA() throws IOException{
        List<String> lines = Files.readAllLines(Path.of(WORKING_DIR, "a.java")), solutionLines = new ArrayList<>();
        for(int i = 0; i < lines.size() && solutionLines.isEmpty(); i++)
            if(lines.get(i).startsWith(SOLUTION_PREFIX))
                for(int j = i; j < lines.size(); j++)
                    if(lines.get(j).startsWith("}"))
                        break;
                    else solutionLines.add(lines.get(j));
        return solutionLines;
    }

    static int problemNo(String s){
        if(!s.startsWith(SOLUTION_PREFIX))
            return 0;
        int n = 0;
        for(int i = SOLUTION_PREFIX.length(); s.charAt(i) != '{'; i++)
            n = 10 * n + s.charAt(i) - '0';
        return n;
    }
}