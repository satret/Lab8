package com.company.client.Commands;

import com.company.common.Net.CommandResult;
import com.company.common.Net.Request;
import com.company.common.Net.ResultStatus;
import com.company.client.Parser.InputHelper;
import com.company.client.Parser.Token;
import com.company.client.Programm.RequestSender;


import java.util.AbstractList;

/**
 *
 * Команда, очищающая коллекцию
 */
public class ClearCommand implements Command {
    private RequestSender requestSender;

    public ClearCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public void execute(AbstractList<Token> tokens) {
        InputHelper.displayInput(tokens);

        Request<?> request = new Request<String>(this.getName(), null, requestSender.getUserLogin(), requestSender.getUserPassword());
        CommandResult result = requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
    }
}