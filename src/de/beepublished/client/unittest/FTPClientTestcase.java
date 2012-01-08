package de.beepublished.client.unittest;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.zip.ZipEntry;

import org.junit.Test;

import de.beepublished.client.exceptions.ZipVocationException;
import de.beepublished.client.ftp.FTPLoginInformation;
import de.beepublished.client.ftp.FTPTarget;
import de.beepublished.client.zip.ZipEngine;


public class FTPClientTestcase {

	private static FTPLoginInformation login = new FTPLoginInformation() {
		
		@Override
		public String getUserName() {
			return "f005f379";
		}
		
		@Override
		public int getPort() {
			return 21;
		}
		
		@Override
		public String getPassword() {
			return "3PwtGY9UqcyQzVCo";
		}
		
		@Override
		public String getHost() {
			return "dualon-cms.brickit-mod.de";
		}
	};
	
	private static FTPLoginInformation loginLocalhost = new FTPLoginInformation() {
		
		@Override
		public String getUserName() {
			return "newuser";
		}
		
		@Override
		public int getPort() {
			return 21;
		}
		
		@Override
		public String getPassword() {
			return "xampp";
		}
		
		@Override
		public String getHost() {
			return "localhost";
		}
	};
	
	@Test
	public void loginTest() throws SocketException, IOException {
		FTPTarget target = new FTPTarget(login);
		
		target.connect();
		assertEquals(true, target.isConnected());
		
		target.disconnect();
		assertEquals(false, target.isConnected());
	}
	
	@Test
	public void uploadAndDeleteTest() throws SocketException, IOException {
		FTPTarget target = new FTPTarget(login);
		target.connect();
		boolean login = target.login();
		
		assertFalse(target.containsFile("testfile.txt"));
		
		target.uploadFile("FilesUnittest/FileUpload/testfile.txt", "testfile.txt");
		assertTrue(target.containsFile("testfile.txt"));
		
		target.deleteFile("testfile.txt");
		assertFalse(target.containsFile("testfile.txt"));
		
		target.logout();
		target.disconnect();
	}
	
	@Test
	public void uploadFolder() throws SocketException, IOException {
		FTPTarget target = new FTPTarget(login);
		target.connect();
		target.login();
		
		target.uploadFolder(new File("FilesUnittest/FolderUpload/UploadFolderTest/"), "/");
	}
	
	@Test
	public void downloadFile() throws SocketException, IOException{
		FTPTarget target = new FTPTarget(login);
		target.connect();
		target.login();
		
		File targetFile = new File("DownloadTest/zipDownloader.php");
		File targetDirectory = new File(targetFile.getParent());
		targetDirectory.mkdirs();
		
		target.downloadFile("zipDownloader.php", targetFile);
	}
	
	@Test
	public void downloadFTP() throws SocketException, IOException{
		FTPTarget target = new FTPTarget(loginLocalhost);
		target.connect();
		target.login();
		
		File localTarget = new File("ftpDownloadTest");
		localTarget.mkdirs();
		
		target.downloadFTP(localTarget);
		
		try {
			ZipEngine.zip(localTarget.getPath(), "ftp.zip");
		} catch (ZipVocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
