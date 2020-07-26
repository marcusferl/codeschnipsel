package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class Execution {
	ObservableList<String> entries;
	ArrayList<String> fileList = new ArrayList<String>();
	String sourceFolder = System.getProperty("user.home")+ "\\Desktop\\CodeSchnipsel\\";

	@FXML
	TextField input;
	@FXML
	TextArea javaCode;
	@FXML
	TextField search;
	@FXML
	ListView<String> listView;
	@FXML
	Text info;

	Methods methods = new Methods();

	public void saveCode(ActionEvent event) throws IOException {
		methods.saveCode(input, javaCode, info,sourceFolder);
	}

	public void viewFiles(ActionEvent event) {
		methods.viewFiles(listView, entries, fileList,sourceFolder);
	}

	public void openFile(ActionEvent event) {
		methods.openFile(listView, javaCode, input,sourceFolder);
	}

	public void openOnClick(MouseEvent mouseEvent) {
		if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
			if (mouseEvent.getClickCount() == 2) {
				methods.openFile(listView, javaCode, input,sourceFolder);
			}
		}
	}

	public void search(KeyEvent event) {
		methods.search(fileList, search, listView);
	}

	public void editCode(ActionEvent event) {
		methods.editCode(javaCode);
	}

	public void removeFile() {
		methods.removeFile(listView,sourceFolder);
	}

	public void backup() throws IOException {
		methods.backup(info,sourceFolder);
	}
	public void newCode() {
		methods.newCode(input, javaCode);
	}
}
