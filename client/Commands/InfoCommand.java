package com.company.client.Commands;

import com.company.common.Net.CommandResult;
import com.company.common.Net.Request;
import com.company.common.Net.ResultStatus;
import com.company.client.Parser.InputHelper;
import com.company.client.Parser.Token;
import com.company.client.Programm.RequestSender;

import java.util.AbstractList;

/**
 * Вывести информацию о коллекции
 */
public class InfoCommand implements Command{
    private RequestSender requestSender;

    public InfoCommand(RequestSender requestSender) {
        this.requestSender = requestSender;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
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