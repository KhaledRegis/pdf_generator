package stc.pdf_generator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.exceptions.PDFExportException;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;

public class PDFGenerator {
//	String executable = WrapperConfig.findExecutable();
	private Logger logger = LoggerFactory.getLogger(PDFGenerator.class);
	
	
	// must start with / for some reason...
	final static String EXECUTABLE_BIN = "/wkhtmltopdf.bin";

	final static String EXECUTABLE_EXE = "/wkhtmltopdf.exe";
	
	Pdf pdf = null;

	Path tempFile = null;
	Path tempPath = null;
	public byte[] getPDF(String template) throws IOException, InterruptedException, PDFExportException {
		try {
			boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
			String EXECUTABLE = isWindows?EXECUTABLE_EXE:EXECUTABLE_BIN;
			
			InputStream exeStream = PDFGenerator.class.getResourceAsStream(EXECUTABLE);
			
			if (exeStream == null ) throw new IOException("["+EXECUTABLE+"]exeStream Is Null!");
			// Create a temporary local copy of the executable
			
			 byte[] buffer = new byte[16384];
             int bytesRead;
             
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while ((bytesRead = exeStream.read(buffer)) !=-1) {
            	byteArrayOutputStream.write(buffer, 0, bytesRead);;
            }

             byte[] bytes = byteArrayOutputStream.toByteArray();
			
			tempPath = getTempDirectory();
			
            
            String Extention = isWindows ? ".exe":".bin";
            tempFile = Files.createTempFile(tempPath, "wkhtmltopdf",Extention );
            
            Files.write(tempFile,bytes);
            
            


//			FileOutputStream fos = new FileOutputStream(tempExeFile);
//			byte[] buffer = new byte[1024];
//			int bytesRead;
//			while ((bytesRead = exeStream.read(buffer)) != -1) {
//				fos.write(buffer, 0, bytesRead);
//			}
            
			pdf = new Pdf(new WrapperConfig(tempFile.toAbsolutePath().toString()));
			File tempDir = new File("src/main/resources/");
			pdf.setTempDirectory(tempDir);
			logger.info(tempDir.toString());
			pdf.addPageFromString(template);
			pdf.addParam(new Param("--enable-local-file-access"));
			pdf.addParam(new Param("--dpi"), new Param("300"));
//			pdf.addParam(new Param("--encoding"), new Param("utf-8"));
			pdf.addParam(new Param("--disable-smart-shrinking"));
			pdf.addParam(new Param("--margin-right"), new Param("1mm"));
			pdf.addParam(new Param("--margin-left"), new Param("1mm"));
			
			File jarFile = new File(PDFGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			pdf.saveAs(jarFile.getParent()+"/pdf.pdf");
			
			return pdf.getPDF();
		} catch (Exception e) {
			logger.error("Error While Creating PDF...");
			e.printStackTrace();
			return null;			
		}
		finally {
			try {
				Files.deleteIfExists(tempFile);
				Files.deleteIfExists(tempPath);			
				logger.info("Cleaning...");
			}
			catch(IOException e) {
				logger.error("Path and File " +tempFile.getFileName() + " and "+tempPath+" Must be removed!");
				logger.error("Absolute path of folder: "+tempPath.toAbsolutePath().toString());
			}
		}
//		pdf.saveAs("pdf.pdf");
	}
	
	private  Path getTempDirectory() throws IOException {
        // Get the system's temporary directory
        Path tempDir = Files.createTempDirectory("htmltopdf-jar");

        return tempDir;
    }

}