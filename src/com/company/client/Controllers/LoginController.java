package com.company.client.Controllers;

import com.company.client.ClientMain;
import com.company.common.DataModels.Account;
import com.company.common.Net.CommandResult;
import com.company.common.Net.Request;
import com.company.common.Net.ResultStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends BaseController {
    @FXML
    private Label accountName;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    @FXML
    private void loginClick(ActionEvent event) throws IOException, InterruptedException {
        String login = loginField.getText();
        String password = passwordField.getText();

        Account account = new Account(login, password);
        Request<Account> request = new Request<Account>("login", account, null, null);

        CommandResult result = ClientMain.requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            ClientMain.requestSender.setUserLogin(login);
            ClientMain.requestSender.setUserPassword(password);

            changeView(event,"/com/company/client/Views/MainView.fxml");
        }
        else{
            createAlert(result.message);
        }
    }

    @FXML
    private void backClick(ActionEvent event) throws IOException {
        changeView(event, "/com/company/client/Views/LoginOrRegisterView.fxml");
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        accountName.setText(ClientMain.resources.getString("AccountName"));
        passwordLabel.setText(ClientMain.resources.getString("Password"));
        cancelButton.setText(ClientMain.resources.getString("Cancel"));
        loginButton.setText(ClientMain.resources.getString("Login"));
    }
}
