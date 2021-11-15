class Parser {
    String commandName;
    String[] args;

    //This method will divide the input into commandName and args
//where "input" is the string command entered by the user
    public boolean parse(String input){};
    public String getCommandName(){};
    public String[] getArgs(){};
}
public class Terminal {
    Parser parser;
    //Implement each command in a method, for example:
    public String pwd(){...}
    public void cd(String[] args){...}
    // ...
//This method will choose the suitable command method to be called
    public void chooseCommandAction(){...}
    public static void main(String[] args){...}
}

