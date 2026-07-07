package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class ReportOpener {

	public static void openExtentReport() {

		try {

			File reportsFolder = new File("test-output");

			File[] reportFolders = reportsFolder.listFiles(file ->
					file.isDirectory() && file.getName().startsWith("SparkReport"));

			if (reportFolders == null || reportFolders.length == 0) {
				System.out.println("No SparkReport folder found.");
				return;
			}

			File latestFolder = Arrays.stream(reportFolders)
					.max(Comparator.comparingLong(File::lastModified))
					.orElse(null);

			if (latestFolder == null) {
				System.out.println("No latest SparkReport folder found.");
				return;
			}

			File report = new File(latestFolder, "ExtentReport.html");

			if (report.exists()) {
				Desktop.getDesktop().browse(report.toURI());
			} else {
				System.out.println("Extent Report not found: " + report.getAbsolutePath());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}