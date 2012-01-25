package de.beepublished.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import de.beepublished.client.zip.ZipEngine;

public class HostedBackup implements FileEndPoint {

	private File backupFile;
	private File dbFile;
	private File filesRoot;
	
	@Override
	public String getName() {
		return "webserver";
	}

	@Override
	public int getType() {
		return EndPointType.FILE;
	}

	@Override
	public File getFiles() {
		return filesRoot;
	}

	@Override
	public File getSQLDump() {
		return dbFile;
	}

	@Override
	public File getBackupFile() {
		return backupFile;
	}

	@Override
	public void process() {
		try {
		
			File f = File.createTempFile("tmp_download_folder_", "");
			f.delete();
			f.mkdir();
			
			URLConnection con = new URL("http://localhost/archive.zip").openConnection();
			System.out.println(con.getContentType());
			
			InputStream in = con.getInputStream();
			this.backupFile = new File(f.getAbsolutePath()+"/installation.zip");
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(backupFile));
			byte data[] = new byte[1024];
			while(in.read(data) != -1){
				bout.write(data);
			}
			bout.flush();
			bout.close();
					
			File extracted = File.createTempFile("tmp_installation", "");
			extracted.delete();
			extracted.mkdir();
			
			ZipEngine.unzip(backupFile, extracted);
			
			filesRoot = new File(extracted.getAbsolutePath()+"/files/");
			dbFile = new File(extracted.getAbsolutePath()+"/db/cake.sql");
			
			assert(filesRoot.isDirectory());
			assert(dbFile.isFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
