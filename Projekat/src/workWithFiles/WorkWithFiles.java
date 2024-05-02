package workWithFiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JFileChooser;
import dataAccess.DataAccess;
import modelDB.Data;

public class WorkWithFiles {

	private static final String REPOSITORY = "C:\\Users\\Bojan\\Documents\\Repository\\";

	public static void showFileDialog(String userID) {
		// get user files from a database
		List<Data> userFiles = DataAccess.getUserFiles(userID);
		// get all files from a database
		List<String> allFiles = DataAccess.getAllFiles();
		// files of other users
		//List<String> filesForHiding = findDifference(allFiles, userFiles);
		// hideOtherFiles(filesForHiding);
		// showAllFiles(allFiles);

		// file chooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String extension = getExtension(selectedFile);
			System.out.println(selectedFile.getName());
			System.out.println(extension);
		}

	}

	private static String getExtension(File file) {
		String extension = "";
		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');
		if (index > 0 && index < fileName.length() - 1) {
			extension = fileName.substring(index + 1).toLowerCase();
		}
		return extension;
	}

	private static List<String> findDifference(List<String> allFiles, List<String> userFiles) {
		allFiles.removeAll(userFiles);

		return allFiles;
	}

	public static void hideOtherFiles(List<String> files) {
		try {
			for (String s : files) {
				File file = new File(REPOSITORY + s + ".txt");
				Path p = Paths.get(REPOSITORY + s + ".txt");
				Files.setAttribute(p, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
				if (file.isHidden())
					System.out.println("File is hidden");
				else
					System.out.println("File isn't hidden");

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("EXCEPTION: Ovaj fajl ne postoji!");
		}
	}

	public static void showAllFiles(List<String> files) {
		try {
			for (String s : files) {
				File file = new File(REPOSITORY + s + ".txt");
				Path p = Paths.get(REPOSITORY + s + ".txt");
				Files.setAttribute(p, "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
				if (file.isHidden())
					System.out.println("File is hidden");
				else
					System.out.println("File isn't hidden");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
