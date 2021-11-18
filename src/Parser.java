
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.lang.Object;
import static java.nio.file.StandardCopyOption.*;

class Parser {
    String commandName;
    String[] args = new String[] {};

    // This method will divide the input into commandName and args
    // where "input" is the string command entered by the user
    public void parse(String input) {
        if (input.length() > 0) {
            int x = input.indexOf(" ");
            if (x >= 0) {
                commandName = input.substring(0, x);
                args = input.substring(x + 1).split(" ");
            } else {
                commandName = input;
                args = null;
            }
        }
    };

    public String getCommandName() {
        return commandName;
    };

    public String[] getArgs() {
        return args;
    };
}

class Terminal {
    // private static Parser parser;
    Parser parser = new Parser();
    String HomePath = System.getProperty("user.home");
    File h = new File(HomePath);

    public static String CurrentPath;

    public Terminal() {
        if (h.isDirectory()) {
            System.setProperty("user.dir", h.getAbsolutePath());
        }
        CurrentPath = HomePath;
    }

    public static boolean isPathValid(String path) {
        File s = new File(path);
        if (s.exists())
            return true;
        else
            return false;
    }

    // Implement each command in a method, for example:
    public void echo(String arg) {
        System.out.println(arg);
    }

    public void cd(String[] arg) {
        String st = CurrentPath;
        if (arg == null) {
            CurrentPath = HomePath;
        } else {
            // System.out.println(arg[0]);
            if (arg[0].equals("..")) {
                // System.out.println("case ..");
                int x = CurrentPath.lastIndexOf('\\');
                if (isPathValid(CurrentPath.substring(0, x)))
                    CurrentPath = CurrentPath.substring(0, x);
            } else {
                if (arg[0].contains("\\")) {
                    System.out.println("with \\");
                    CurrentPath = arg[0];

                } else {
                    System.out.println("without");
                    CurrentPath = CurrentPath + "\\" + arg[0];

                }

            }
        }
        if (!isPathValid(CurrentPath)) {
            CurrentPath = st;
        }
        System.setProperty("user.dir", CurrentPath);
        System.out.println(CurrentPath);
    }

    public void ls() {
        File dir = new File(CurrentPath);
        File dir1 = new File(System.setProperty("user.dir", dir.getAbsolutePath()));
        String childs[] = dir.list();
        for (String child : childs) {
            System.out.println(child);
        }
    }

    public void lsR() {
        File f = new File(CurrentPath); // current directory

        File[] files = f.listFiles();
        Collections.reverse(Arrays.asList(files));

        for (File file : files) {
            try {
                int x = file.getCanonicalPath().lastIndexOf("\\");
                System.out.println(file.getCanonicalPath().substring(x + 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pwd() {
        String pwd = System.getProperty("user.dir");
        System.out.println(pwd);
    }

    public void mkdir(String[] fileName) {
         for (int i = 0; i < fileName.length;i++){
              File dir; 
              if (fileName.length == 0) {
      System.out.println("you didn't enter arguments"); }
       else { 
           if(fileName[i].contains("\\")) 
           { dir = new File(fileName[i]); } 
           else { 
               dir =new File(CurrentPath + "\\" + fileName[i]); }
                if (!dir.exists()) {
                     boolean result = dir.mkdir(); // Create the directory
                    if (result) {
                    System.out.println("directory " + (i + 1) + " created ");
                     } 
                }
                else {
                     System.out.println("The directory " + (i + 1) + " already exists"); 
                     }
            } 
        }
        return;

    }

    public void rmdir(String dir) {
        File file = new File(dir);
        boolean f = file.delete();

    }

    public void touch(String args) throws IOException {
        File t = new File(args);
        boolean rst;
        rst = t.createNewFile();
    }

    public void cp(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("please enter two files");
        } else {

            Path src = Path.of(CurrentPath + "\\" + args[0]);
            Path dest = Path.of(CurrentPath + "\\" + args[1]);
            Files.copy(src, dest, REPLACE_EXISTING);
        }

    }

    public void cpr(String[] args) { // two arguments need to be handeled

        if (args.length < 2) {
            System.out.println("please enter two directories");
        } else {
            String source = CurrentPath + "\\" + args[0];
            File srcDir = new File(source);

            String destination = CurrentPath + "\\" + args[1];
            File destDir = new File(destination);

            try {

                copyDirectory(srcDir, destDir);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }
        for (String f : sourceDirectory.list()) {
            copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        }
    }

    public static void copyDirectoryCompatibityMode(File source, File destination) throws IOException {
        if (source.isDirectory())
            copyDirectory(source, destination);

    }

    public void rm(String[] args) {
        File file = new File(CurrentPath + args[1]);
        if (file.exists()) {
            file.delete();
        } else {
            System.out.println("file not exist");
        }

    }

    public void cat(String[] args) throws IOException {
        if (args.length == 1) { // one arg
            File file = new File(CurrentPath + "\\" + args[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            while ((st = br.readLine()) != null)

                // Print the string
                System.out.println(st);

            br.close();

        } else if (args.length == 2) {// two arg need to be handeled
            File file1 = new File(CurrentPath + "\\" + args[0]);
            File file2 = new File(CurrentPath + "\\" + args[1]);
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));

            String st;

            while ((st = br1.readLine()) != null)

                // Print the string
                System.out.println(st);

            while ((st = br2.readLine()) != null)

                // Print the string
                System.out.println(st);

            br1.close();
            br2.close();

        }
    }

    // This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException {

        String CommName = parser.getCommandName();
        String[] argument = parser.getArgs();
        // System.out.println(CommName+" Hello");
        if (CommName.contains("echo")) {
            this.echo(argument[0]);
        } else if (CommName.contains("cd")) {
            cd(argument);
        } else if (CommName.contains("ls") & !CommName.contains("ls-r")) {
            this.ls();
        } else if (CommName.contains("ls-r")) {
            this.lsR();
        } else if (CommName.contains("cp")) {
            this.cp(argument);
        }
        else if (CommName.contains("cp -r")) {
            this.cat(argument);
        }
 
        else if (CommName.contains("cat")) {
            this.cat(argument);
        }

        else if (CommName.contains("rm")) {
            this.rm(argument);
        }
     

        else if (CommName.contains("pwd")) {
            this.pwd();
        }

        else if (CommName.contains("mkdir")) {
            this.mkdir(argument);
        } else if (CommName.contains("rmdir")) {
            this.rmdir(argument[1]);
        } else if (CommName.contains("touch")) {
            try {
                this.touch();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else {
            System.out.println("not a valid command");
        }
    };

    public static void main(String[] args) throws IOException {
        String input;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the input:");
        Terminal t = new Terminal();
        while (true) {
            input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
            }
            t.parser.parse(input);
            System.out.println("command name " + t.parser.commandName);
            System.out.println(Arrays.toString(t.parser.args));
            t.chooseCommandAction();
        }

    }
}
