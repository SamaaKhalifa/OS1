import javax.lang.model.type.NullType;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

class Parser {
    String commandName;
    String[] args;

    //This method will divide the input into commandName and args
//where "input" is the string command entered by the user
    public void parse(String input){
        int x= input.indexOf(" ");
        commandName=input.substring(0,x);
        args=input.substring(x).split(" ");
    };
    public String getCommandName(){
            return commandName;
    };
    public String[] getArgs(){
            return  args;
    };
}
class Terminal {
    private static Parser parser;
   // Parser parser;
    String HomePath=System.getProperty("user.home") + "/Desktop";
    String CurrentPath;
    public static boolean isPathValid(String path) {
        try {

            Paths.get(path);

        } catch (InvalidPathException ex) {
            return false;
        }

        return true;
    }
    //Implement each command in a method, for example:
    public void echo(String arg){
        System.out.println(arg+"\n");
    }
    public void cd(String[] arg){
        if(arg[0]== null){
                CurrentPath=HomePath;
        }else{
            if(arg[0]==".."){
                int x= CurrentPath.lastIndexOf('/');
                if(isPathValid(CurrentPath.substring(0,x)))CurrentPath=CurrentPath.substring(0,x);
            }else{
                if(isPathValid(arg[0])) CurrentPath=arg[0];
                else System.out.println("invalid Path\n");
        }
        }
    }
    public void  ls()  {
        File f = new File(CurrentPath); // current directory

        File[] files = f.listFiles();
        Arrays.sort(files);
        for (File file : files) {
            try {
                System.out.println(file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void lsR(){
        File f = new File(CurrentPath); // current directory

        File[] files = f.listFiles();
        Collections.reverse(Arrays.asList(files));

        for (File file : files) {
            try {
                System.out.println(file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//This method will choose the suitable command method to be called
    public void chooseCommandAction(){

        String CommName=parser.getCommandName();
        String[] argument= parser.getArgs();
        if(CommName=="echo"){
            echo(argument[1]);
        }else if(CommName=="cd"){
            cd(argument);
        }else if(CommName=="ls"){
                ls();

        }else if(CommName=="ls -r"){
                lsR();
        }
    };
    public static void main(String[] args){
        String input;
        Scanner sc=new Scanner(System.in);
        input=sc.nextLine();
        parser.parse(input);

    };
}

