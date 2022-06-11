package com.company.client.Commands;

import com.company.common.Net.CommandResult;
import com.company.common.Net.Request;
import com.company.common.Net.ResultStatus;
import com.company.client.Parser.InputHelper;
import com.company.client.Parser.Token;
import com.company.client.Programm.RequestSender;

import java.util.AbstractList;

/**
 * Вывести список элементов коллекции
 */
public class ShowCommand implements Command {
    private RequestSender requestSender;

    public ShowCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(AbstractList<Token> tokens) {
        InputHelper.displayInput(tokens);

        Request<?> request = new Request<Object>(this.getName(), null, requestSender.getUserLogin(), requestSender.getUserPassword());
        CommandResult result = requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
    }
}