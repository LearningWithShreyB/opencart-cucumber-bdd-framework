package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class ReportOpener {

	public static void openExtentReport() {

		File report = new File("ExtentReport/CucumberExtentReport.html");

		if (report.exists()) {

			try {

				Desktop.getDesktop().browse(report.toURI());

			} catch (IOException e) {

				e.printStackTrace();

			}

		} else {

			System.out.println("Extent Report not found at: " + report.getAbsolutePath());

		}

	}

}