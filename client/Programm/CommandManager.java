package com.company.client.Programm;

import com.company.client.Commands.*;
import com.company.client.Parser.Token;
import com.company.client.Parser.TokenType;
import com.company.client.Parser.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс управляющий командами и содержащий методы для их выполнения
 */
public class CommandManager {
    /**
     * Все доступные в программе команды
     */
    protected HashMap<String,Command> commands = new HashMap<String, Command>();

    public CommandManager(RequestSender requestSender, Scanner scanner){
        registerCommand(new HelpCommand(this));
        registerCommand(new InfoCommand(requestSender));
        registerCommand(new ShowCommand(requestSender));
        registerCommand(new InsertCommand(requestSender, scanner));
        registerCommand(new UpdateCommand(requestSender, scanner));
        registerCommand(new RemoveCommand(requestSender));
        registerCommand(new ClearCommand(requestSender));
        registerCommand(new ExecuteScriptCommand(this, requestSender));
        registerCommand(new ExitCommand());
        registerCommand(new ReplaceIfGreaterCommand(requestSender, scanner));
        registerCommand(new RemoveGreater(requestSender));
        registerCommand(new RemoveLowerKeyCommand(requestSender));
        registerCommand(new CountGreaterThanDistance(requestSender));
        registerCommand(new CountLessThanDistance(requestSender));
        registerCommand(new RemoveAllByDistance(requestSender));
    }

    /**
     * Спарсить введённую строку и выполнить
     * @param input строка ввода
     * @throws Exception
     */
    public void ParseAndExecute(String input) throws Exception {
        ArrayList<Token> tokens;
        try{
            tokens = Tokenizer.tokenize(input);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        if(tokens.size() == 0){
            System.out.println("Введённая строка пуста!");
        }

        Token firstToken = tokens.get(0);

        if(firstToken.type != TokenType.WORD){
            throw new Exception("В строке первым долFжно быть написано название команды, потом её аргументы через пробел.");
        }

        if(!commands.containsKey(firstToken.object)){
            throw new IndexOutOfBoundsException(String.format("Команды \"%s\" не существует!", firstToken.object));
        }

        try{
            commands.get(firstToken.object).execute(tokens);
        }
        catch (Exception e){
            System.out.println("Произошла ошибка: " + e.getMessage());
            System.out.println("Выполнение команды отменено.");
        }
    }

    /**
     * Спарсить новую команду
     * @param newCommand новая команда
     */
    void registerCommand(Command newCommand){
        if(commands.containsKey(newCommand.getName())){
            throw new IllegalArgumentException("Команда с таким именем уже существует!");
        }

        commands.put(newCommand.getName(), newCommand);
    }

    /**
     * Вернуть список команд и описаний
     * @return строка содержащая список команд и их описаний
     */
    public String getCommandsNameWithDescription(){
        String result = "";

        for (Map.Entry<String, Command> e : commands.entrySet()) {
            Command command = e.getValue();
            result +=  command.getName() + " - " + command.getDescription() + "\n";
        }
        return result;
    }
}