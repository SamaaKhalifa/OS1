import javax.lang.model.type.NullType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

class Parser {
    String commandName;
    String[] args=new String[]{};

    //This method will divide the input into commandName and args
//where "input" is the string command entered by the user
    public void parse(String input){
        if (input.length()>0){
        int x= input.indexOf(" ");
        if(x>=0){
            commandName=input.substring(0,x);
            args=input.substring(x+1).split(" ");
        }
        else{
           commandName=input;
           args=null;
        } }
    };
    public String getCommandName(){
            return commandName;
    };
    public String[] getArgs(){
            return  args;
    };
}
class Terminal {
    //private static Parser parser;
    Parser parser=new Parser();
    String HomePath=System.getProperty("user.home")+"\\OS";
    File h=new File(HomePath);
    String CurrentPath;
    public Terminal(){
        if(h.isDirectory()){
            System.setProperty("user.dir",h.getAbsolutePath());
        }
        CurrentPath=HomePath;
    }
    public static boolean isPathValid(String path) {
        File s= new File(path);
        if(s.exists()) return true;
        else return false;
    }
    //Implement each command in a method, for example:
    public void echo(String arg){
        System.out.println(arg);
    }
   public void cd(String[] arg) {
       String st=CurrentPath;
       if (arg == null) {
           CurrentPath = HomePath;
       }
        else {
           // System.out.println(arg[0]);
            if (arg[0].equals("..")) {
                //System.out.println("case ..");
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
       if (!isPathValid(CurrentPath)){
           CurrentPath=st;
       }
       System.setProperty("user.dir", CurrentPath);
       System.out.println(CurrentPath);
   }
    public void  ls()  {
        File dir=new File(CurrentPath);
        File dir1 = new File(System.setProperty("user.dir",dir.getAbsolutePath()));
        String childs[] = dir.list();
        for(String child: childs){
            System.out.println(child);
        }
    }
    public void lsR(){
        File f = new File(CurrentPath); // current directory

        File[] files = f.listFiles();
        Collections.reverse(Arrays.asList(files));

        for (File file : files) {
            try {
                int x=file.getCanonicalPath().lastIndexOf("\\");
                System.out.println(file.getCanonicalPath().substring(x+1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void pwd() {
        String pwd = System.getProperty("user.dir");
        System.out.println(pwd);
    }
//This method will choose the suitable command method to be called
    public void chooseCommandAction(){

        String CommName=parser.getCommandName();
        String[] argument= parser.getArgs();
        //System.out.println(argument);
        if(CommName.contains("echo")){
            this.echo(argument[1]);
        }else if(CommName.contains("cd")){
            cd(argument);
        }else if(CommName.contains("ls")&!CommName.contains("ls-r")){
               this.ls();
        }else if(CommName.contains("ls-r")){
                this.lsR();
        }else if(CommName.equals("pwd")){
            this.pwd();
        }
    };
    public static void main(String[] args){
        String input;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the input:");
        Terminal t=new Terminal();
        while(true){
            input=sc.nextLine();
            if(input.contains("exit")){
                break;
            }
            t.parser.parse(input);
            t.chooseCommandAction();
        }

    };
}

