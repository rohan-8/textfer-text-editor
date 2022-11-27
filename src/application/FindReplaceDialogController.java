package application;

import document.Document;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.List;

/**
 * Created by HP on 29-07-2022.
 */
public class FindReplaceDialogController {
    private Stage dialogStage;
    private MainApp mainApp;
    private
    int startIndex=0;
    int select_start=-1;

    @FXML
    private TextField word1;

    @FXML
    private TextField word2;

    @FXML
    private Button okButton;

    private AutoSpellingTextArea textBox;

    @FXML
    private void initialize() {
        okButton.setDefaultButton(true);
    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTextArea(AutoSpellingTextArea textBox) {
        this.textBox = textBox;
    }

    @FXML
    private void handleOk() {
        this.dialogStage.close();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        return !(word1.getText().equals("") ||word2.getText().equals(""));
    }

    @FXML
    private void handleFind() {
        System.out.println("String is : " +textBox.getText());
        System.out.println("Find word is :"+word1.getText());
        select_start = textBox.getText().indexOf(word1.getText().toLowerCase());
        System.out.println("Select_start is :"+ select_start);
        if(select_start == -1)
        {
            startIndex = 0;
            mainApp.showInputErrorDialog("Could not find \"" + word1.getText() + "\"!");
            System.out.println("No such word found");
        }
        if(select_start == textBox.getText().lastIndexOf(word1.getText().toLowerCase()))
        {
            startIndex = 0;
        }
        int select_end = select_start + word1.getText().length();
        System.out.println("Select end is"+ select_end);
        this.textBox.selectRange(select_start, select_end);
    }

    @FXML
    private void handleFindNext() {
        String selection = textBox.getSelectedText();
        try
        {
            selection.equals("");
        }
        catch(NullPointerException e)
        {
            System.out.println("no selection yet");
        }
        try
        {
            int select_start = textBox.getText() .indexOf(selection , startIndex);
            int select_end = select_start+selection.length();
            textBox.selectRange(select_start, select_end);
            startIndex = select_end+1;

            if(select_start == textBox.getText().lastIndexOf(selection.toLowerCase()))
            {
                startIndex = 0;
            }
        }
        catch(NullPointerException e)
        {}
    }
    @FXML
    private void handleReplace() {
        try
        {
            handleFind();
            if (select_start != -1)
                textBox.replaceSelection(word2.getText());
        }
        catch(NullPointerException e)
        {
            System.out.print("Null Pointer Exception: "+e);
        }
    }

    @FXML
    private void handleReplaceAll(){
        String setText=textBox.getText().replaceAll(word1.getText() , word2.getText());
        textBox.clear();
        textBox.appendText(setText);
    }
}
