package com.company.client.Commands;

import com.company.client.Parser.InputHelper;
import com.company.client.Parser.Parser;
import com.company.client.Parser.Token;
import com.company.client.Programm.CommandManager;
import com.company.client.Programm.RequestSender;
import com.company.client.Programm.Programm;

import java.io.*;
import java.util.*;

public class ExecuteScriptCommand implements Command{
    CommandManager commandManager;
    RequestSender requestSender;

    public static Set<String> executingScripts = new HashSet<>();

    public ExecuteScriptCommand(CommandManager commandManager, RequestSender requestSender){
        this.commandManager = commandManager;
        this.requestSender = requestSender;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме / execute_script \"file_name\"";
    }

    @Override
    public void execute(AbstractList<Token> tokens) throws Exception {
        InputHelper.displayInput(tokens);

        String path;
        try{
            path = Parser.parseString(tokens.get(1));
        }
        catch (Exception e){
            throw new Exception("Парсинг агрумента file_name не удался. " + e.getMessage());
        }

        File script = null;
        try {
            script = new File(path);
        }
        catch (Exception exc){
            throw new Exception("Не удалось получить доступ к файлу, возможно указана директория вместо файла!");
        }

        if(!script.exists()){
            throw new Exception("Файла со скриптом не существует!");
        }
        if(!script.canRead()){
            throw new Exception("Нет прав на чтение файла со скриптом!");
        }
        if(executingScripts.contains(path)){
            throw new Exception("Этот скрипт уже выполняется, в целях избежания рекурсии его выполнение запрещено.");
        }

        Scanner fileScanner = new Scanner(new BufferedInputStream(new FileInputStream(script)));

        System.out.println("Началось выполнение скрипта");
        executingScripts.add(path);

        CommandManager cm = new CommandManager(requestSender, fileScanner);
        Programm.run(cm, fileScanner);

        executingScripts.remove(path);
        System.out.println("Выполение скрипта завершено");
    }
}