package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Methods {

	public void saveCode(TextField input, TextArea javaCode, Text info, String sourceFolder) throws IOException {
		File javaFile = new File(sourceFolder + input.getText());
		if (javaFile.createNewFile()) {
			FileWriter writeInFile = new FileWriter(sourceFolder + input.getText());
			writeInFile.write(javaCode.getText());
			writeInFile.close();
			input.clear();

			info.setText("Code saved");
		} else if (javaFile.exists()) {
			info.setText("File edited!");

			FileWriter writeInFile = new FileWriter(sourceFolder + input.getText());
			writeInFile.write(javaCode.getText());
			writeInFile.close();

		}
	}

	public void viewFiles(ListView<String> listView, ObservableList<String> entries, ArrayList<String> fileArrayList,
			String sourceFolder) {
		fileArrayList.clear();
		File dir = new File(sourceFolder);
		String[] filelist = dir.list();
		String[] theNamesOfFiles = new String[filelist.length];
		for (int i = 0; i < theNamesOfFiles.length; i++) {
			theNamesOfFiles[i] = filelist[i].toLowerCase();
			fileArrayList.add(theNamesOfFiles[i]);
		}

		ListProperty<String> listProperty = new SimpleListProperty<>();
		listProperty.set(FXCollections.observableArrayList(filelist));
		listView.itemsProperty().bind(listProperty);

	}

	public void openFile(ListView<String> listView, TextArea javaCode, TextField input, String sourceFolder) {
		javaCode.clear();
		try {
			File javafile = new File(sourceFolder + listView.getSelectionModel().getSelectedItem().toString());
			Scanner myReader = new Scanner(javafile);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				javaCode.appendText(data + "\n");
				javaCode.setEditable(false);
				input.setText(listView.getSelectionModel().getSelectedItem().toString());

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public void editCode(TextArea javaCode) {
		javaCode.setEditable(true);
	}

	public void search(ArrayList<String> fileArrayList, TextField search, ListView<String> listView) {
		ObservableList<String> data = FXCollections.observableArrayList(fileArrayList);
		FilteredList<String> filteredData = new FilteredList<>(data, s -> true);
		search.textProperty().addListener(obs -> {
			String filter = search.getText().toLowerCase();
			if (filter == null || filter.length() == 0) {
				filteredData.setPredicate(s -> true);
			} else {
				filteredData.setPredicate(s -> s.contains(filter));
			}
			listView.getItems().clear();
			listView.getItems().addAll(filteredData);

		});
	}

	public void backup(Text info, String sourceFolder) throws IOException {
		String sourceFile = sourceFolder;
		FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\Desktop\\CodeSchnipsel.zip");
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		File fileToZip = new File(sourceFile);

		zipFile(fileToZip, fileToZip.getName(), zipOut);
		zipOut.close();
		fos.close();
		info.setText("Backup Done!");
	}

	private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		if (fileToZip.isDirectory()) {
			if (fileName.endsWith("/")) {
				zipOut.putNextEntry(new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(new ZipEntry(fileName + "/"));
				zipOut.closeEntry();
			}
			File[] children = fileToZip.listFiles();
			for (File childFile : children) {
				zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
			}
			return;
		}
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}

	

	public void removeFile(ListView<String> listView, String sourceFolder) {
		File javafile = new File(sourceFolder + listView.getSelectionModel().getSelectedItem().toString());
		javafile.delete();

		int index = listView.getSelectionModel().getSelectedIndex();
		listView.getItems().remove(index);

	}

	public void newCode(TextField input, TextArea javaCode) {
		input.clear();
		javaCode.clear();
		javaCode.setEditable(true);
	}

	public void firstStart() throws Exception {
		File file = new File(System.getProperty("user.home"), "Desktop\\CodeSchnipsel");

		if (!file.exists()) {
			if (file.mkdir()) {

			}
			System.out.println("Directory created");

		} else {
			System.out.println("Directory already exists! ");
		}
	}
	public void downloadBackupOnStart() throws IOException, Exception {
		InputStream in = new URL(
				"https://onedrive.live.com/download?cid=827A382E14E2F4E1&resid=827A382E14E2F4E1%211057&authkey=AL0OfIlIbTisbPA")
						.openStream();
		File file = new File(System.getProperty("user.home"), "Desktop\\CodeSchnipsel\\OnlineBackup.zip");
		Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

		InputStream thisZip = new FileInputStream(file);
		ZipInputStream zipInputStream = new ZipInputStream(thisZip);

		Path targePath = Paths.get(System.getProperty("user.home"), "Desktop\\CodeSchnipsel\\");

		for (ZipEntry zipEntry; (zipEntry = zipInputStream.getNextEntry()) != null;) {
			Path reslovedPath = targePath.resolve(zipEntry.getName());
			if (zipEntry.isDirectory()) {
				Files.createDirectories(reslovedPath);
			} else {
				Files.createDirectories(reslovedPath.getParent());
				Files.copy(zipInputStream, reslovedPath, StandardCopyOption.REPLACE_EXISTING);
			}

		}
		zipInputStream.close();
		file.delete();

	}

}
