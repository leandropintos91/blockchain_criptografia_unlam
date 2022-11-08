package com.grupo2.blockchain.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;

public class FileUtils {
	
	private static final String BASE_PATH = "/blockChain";
	private static String path;

	public static void checkPath() throws UnsupportedEncodingException {
		path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + BASE_PATH;
		path = URLDecoder.decode(path, StandardCharsets.UTF_8.toString());
		
		File fi = new File(path);
		if(!fi.exists()) {
			fi.mkdir();
		}
	}
	
	public static void checkFile(String filename) throws UnsupportedEncodingException {
		checkPath();
		String decodedPath = URLDecoder.decode(path + filename, StandardCharsets.UTF_8.toString());
		File fi = new File(decodedPath);
		
		if(!fi.exists()) {
			try {
				fi.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static File getFile(String filename) throws UnsupportedEncodingException {
		return new File(URLDecoder.decode(path + filename, StandardCharsets.UTF_8.toString()));
	}
	
	public static FileWriter getFileWriter(String filename) throws IOException {
		return new FileWriter(URLDecoder.decode(path + filename, StandardCharsets.UTF_8.toString()));
	}
}
