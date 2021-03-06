package com.company.client.Controllers;

import com.company.client.ClientMain;
import com.company.common.Net.CommandResult;
import com.company.common.Net.Request;
import com.company.common.Net.ResultStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class InformationController extends BaseController{
    @FXML
    private Button backButton;
    @FXML
    private Label infoLabel;

    @Override
    public void initialize() {
        super.initialize();

        updateCollectionInfo();
    }

    private void updateCollectionInfo() {
        Request<?> request = new Request<String>("info", ClientMain.selectedLanguage, ClientMain.requestSender.getUserLogin(), ClientMain.requestSender.getUserPassword());
        CommandResult result = ClientMain.requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            infoLabel.setText(result.message);
        }
        else{
            createAlert(result.message);
        }
    }

    @FXML
    private void backButtonClick(javafx.event.ActionEvent event) throws IOException {
        changeView(event, "/com/company/client/Views/MainView.fxml");
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        updateCollectionInfo();
        backButton.setText(ClientMain.resources.getString("Back"));
    }
}
