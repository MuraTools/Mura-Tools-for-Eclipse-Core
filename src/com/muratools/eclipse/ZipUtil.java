/**
 * 
 */
package com.muratools.eclipse;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author steve.good
 *
 */
public class ZipUtil {
	
	private String targetDirectory;
	
	public ZipUtil(String targetDirectory){
		setTargetDirectory(targetDirectory);
	}
	
	public void zipDirectory(String dir2zip, ZipOutputStream zos) {
		try {
			File zipDir = new File(dir2zip);
			String[] dirList = zipDir.list();
			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;
			for (int i = 0; i < dirList.length; i++) {
				File f = new File(zipDir, dirList[i]);
				if (f.isDirectory() && f.getName().equals(".svn") == false && f.isHidden() == false) {
					String filePath = f.getPath();
					zipDirectory(filePath, zos);
					continue;
				}
				String ext = f.getName().substring(f.getName().lastIndexOf(".") + 1);
				if (ext.equals("zip") == false && ext.equals("project") == false && f.isHidden() == false) {
					String shortPath = f.getPath().replace(getTargetDirectory(), "");
					shortPath = shortPath.replace("\\", "/");
					System.err.println(shortPath);
					FileInputStream fis = new FileInputStream(f);
					ZipEntry zEntry = new ZipEntry(shortPath);
					zos.putNextEntry(zEntry);
					while ((bytesIn = fis.read(readBuffer)) != -1) {
						zos.write(readBuffer, 0, bytesIn);
					}
					fis.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTargetDirectory() {
		return targetDirectory;
	}

	public void setTargetDirectory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}
	
}
