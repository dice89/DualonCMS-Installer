package de.beepublished.client.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPFileFilters;
import org.apache.commons.net.ftp.FTPReply;


//TODO create class description
public class FTPTarget {
	
	private FTPLoginInformation loginInformation;
	private FTPClient ftpClient;
	private boolean firsttime = true;
	private boolean firsttimedownload = true;
	public FTPTarget(FTPLoginInformation loginInformation){
		// TODO create method description
		// TODO create test case
		// TODO implement method
		
		this.loginInformation = loginInformation;
	}
	
	public void connect() throws SocketException, IOException {
		// TODO create method description
		// TODO create test case
		// TODO implement method
		
		ftpClient = new FTPClient();
		ftpClient.connect(loginInformation.getHost(), loginInformation.getPort());
		
		int replyCode = ftpClient.getReplyCode();
		if(!FTPReply.isPositiveCompletion(replyCode)){
			throw new RuntimeException("Reply code "+replyCode);
		}
	}
	
	public boolean login() throws IOException{
		// TODO create method description
		// TODO create test case
		// TODO implement method
		assert(isConnected());
		return ftpClient.login(loginInformation.getUserName(), loginInformation.getPassword());	
	}
	
	public void logout() throws IOException{
		assert(isConnected());
		ftpClient.logout();
	}
	
	public void disconnect() throws IOException{
		// TODO create method description
		// TODO create test case
		// TODO implement method
		ftpClient.disconnect();
	}
	
	public void uploadFile(String localFilePath, String remoteFilePath) throws FileNotFoundException, IOException{	
		assert(this.isConnected());
		File f = new File(localFilePath);
		
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		if(!ftpClient.storeFile(remoteFilePath, new FileInputStream(f)))
			System.out.println("File not stored!");
	}
	
	public void uploadFolder(File localFolder, String remoteFolderPath) throws FileNotFoundException, IOException{
		assert(ftpClient.isConnected());
		// TODO create method description
		// TODO create test case
		// TODO implement method

		System.out.println(localFolder.getName()+" started");
		
		assert(localFolder.isDirectory());
		
		if(!firsttime){
			ftpClient.makeDirectory(localFolder.getName());
			ftpClient.changeWorkingDirectory(localFolder.getName());
		}else{
			ftpClient.changeWorkingDirectory(remoteFolderPath);
		}
		
		firsttime = false;
		for(File f : localFolder.listFiles()){
			if(f.isFile()){
				this.uploadFile(f.getAbsolutePath(), f.getName());
			}
			if(f.isDirectory()){
				this.uploadFolder(f, f.getAbsolutePath());
				ftpClient.changeToParentDirectory();
			}	
		}
		System.out.println(localFolder.getName()+" uploaded");

	//	throw new RuntimeException("Not yet implemented!");
	}
	
	/**
	 * Lädt die Datei aus dem aktuellen FTP-Verzeichniss in das aktuelle Arbeitsverzeichnis
	 * @param remoteFilename
	 * @throws IOException 
	 */
	public void downloadFile(String remoteFilename,File localTarget) throws IOException{
		FileOutputStream stream = new FileOutputStream(localTarget);
		System.out.println(ftpClient.retrieveFile(remoteFilename, stream));
		stream.flush();
		stream.close();
	}
	
	public void downloadFTP(File localFolder) throws IOException{
		downloadDirectory(localFolder, "");
	}
	public void downloadFTP(File localFolder, String remoteDirectory) throws IOException{
		downloadDirectory(localFolder, remoteDirectory);
	}
	
	public void downloadDirectory(File localTargetDirectory, String remoteDirectory) throws IOException{
		
		if(!remoteDirectory.equals("")){
			System.out.println(ftpClient.changeWorkingDirectory(remoteDirectory));
			if(!firsttimedownload){
				System.out.println(localTargetDirectory.mkdirs());
			}
			firsttimedownload = false;
		
		}
		FTPFile[] files = ftpClient.listFiles("", FTPFileFilters.ALL);
		
		try{
			FileOutputStream stream = new FileOutputStream(localTargetDirectory+"/"+".htaccess");
			ftpClient.retrieveFile(".htaccess", stream);
			stream.flush();
			stream.close();
			System.out.println("[x] .htaccess");
		} catch (Exception e){
			System.out.println("[ ] .htaccess");
		}
		for(FTPFile f : files){
			System.out.println(f.getName());
			if(f.isFile())
				this.downloadFile(f.getName(), new File(localTargetDirectory+"/"+f.getName()));
			if(f.isDirectory() && !f.getName().equals(".") && !f.getName().equals(".."))
				this.downloadDirectory(new File(localTargetDirectory+"/"+f.getName()), f.getName());
		}
		System.out.println(ftpClient.changeToParentDirectory());
	}
	
	public boolean isConnected(){
		return ftpClient.isConnected();
	}
	
	public boolean isLogedIn() throws IOException{
		ftpClient.logout();
		return ftpClient.login(loginInformation.getUserName(), loginInformation.getPassword());
	}
	
	public boolean containsFile(String filename) throws IOException{
		// TODO create method description
		// TODO create test case
		// TODO implement method
		
		// ACHTUNG, nur level 0!
		for(FTPFile file : ftpClient.listFiles()){
			if(file.getName().equals(filename))
				return true;
		}
		return false;
	}
	
	public void deleteFile(String filename) throws IOException{
		// TODO create method description
		// TODO create test case
		// TODO implement method
		ftpClient.deleteFile(filename);
	}

}
