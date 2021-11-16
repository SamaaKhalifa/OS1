class Parser {
    String commandName;
    String[] args;

    //This method will divide the input into commandName and args
//where "input" is the string command entered by the user
    public boolean parse(String input){};
    public String getCommandName(){
            return commandName;
    };
    public String[] getArgs(){
            return  args;
    };
}
public class Terminal {
    Parser parser;
    String HomePath=System.getProperty("user.home") + "/Desktop";
    String CurrentPath;
    //Implement each command in a method, for example:
    public void echo(String arg){
        System.out.println(arg+"\n");
    }
    public void cd(String[] arg){
        if(arg.length()==0){
                CurrentPath=HomePath;
        }else{
            if(arg[0]==".."){
                int x= CurrentPath.lastIndexOf('/');
                CurrentPath=CurrentPath.substring(0,x);
            }else{
                CurrentPath=arg[0];
            }
        }
    }
//This method will choose the suitable command method to be called
    public void chooseCommandAction(){

        String CommName=parser.getCommandName();
        String[] arg= parser.getArgs();
        if(CommName=="echo"){
            echo(arg[1]);
        }else if(CommName=="cd"){
            cd(arg);
        }
    };
    public static void main(String[] args){...};
}

