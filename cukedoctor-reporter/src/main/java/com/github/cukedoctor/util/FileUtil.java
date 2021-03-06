package com.github.cukedoctor.util;

import org.apache.maven.shared.utils.io.DirectoryScanner;
import org.apache.maven.shared.utils.io.FileUtils;
import org.apache.maven.shared.utils.io.IOUtil;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pestano on 02/06/15.
 */
public class FileUtil {

	public static Logger log = Logger.getLogger(FileUtil.class.getName());
	public static final Pattern ADOC_FILE_EXTENSION = Pattern.compile("([^\\s]+(\\.(?i)(ad|adoc|asciidoc|asc))$)");

	/**
	 * @param path full path to the json feature result
	 * @return absolute path to to json result file
	 */
	public static String findJsonFile(String path) {
		try {
			if (path == null) {
				path = "";
			}

			if (path.startsWith("/")) {//remove slash to use relative paths
				path = path.substring(1);
			}
			return Paths.get(path.trim()).toAbsolutePath().toString();
		} catch (Exception e) {
			log.log(Level.SEVERE, "Could not load feature from " + path, e);
			return null;
		}

	}

	/**
	 * @param startDir initial directory to scan for features
	 * @return all found json files path that represent cucumber features
	 */
	public static List<String> findJsonFiles(String startDir) {
		try {
			if (startDir == null) {
				startDir = "";
			}

			if (startDir.startsWith("/")) {//remove slash to use relative paths
				startDir = startDir.substring(1);
			}
			DirectoryScanner scanner = new DirectoryScanner();
			scanner.setIncludes(new String[]{"**/*.json"});
			scanner.setBasedir(new File(Paths.get(startDir.trim()).toAbsolutePath().toString()));
			scanner.scan();
			List<String> absolutePaths = new ArrayList<>(scanner.getIncludedFiles().length);
			for (int i = 0; i < scanner.getIncludedFiles().length; i++) {
				absolutePaths.add(new File(scanner.getBasedir(), scanner.getIncludedFiles()[i]).getAbsolutePath());
			}
			//scanner.getIncludedFiles()
			return absolutePaths;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Could not load features from " + startDir, e);
			return null;
		}

	}

	public static File saveFile(String name, String data) {
		if (name == null) {
			name = "";
		}

		if (!name.startsWith("/")) {
			name = "/"+name;
		}
		try {
			String relativePath = Paths.get("").toAbsolutePath().toString();
			//String relativePath = new File(FileUtil.class.getClassLoader().getResource("").toURI()).getAbsolutePath();

			File f = new File(relativePath + name.substring(0, name.lastIndexOf("/")));//create subdirs (if there any)
			f.mkdirs();
			File file = new File(relativePath + name);
			file.createNewFile();
			FileUtils.fileWrite(file, "UTF-8", data);
			log.info("Wrote: " + file.getAbsolutePath());
			return file;
		} catch (IOException e) {
			log.log(Level.SEVERE, "Could not create file " + name, e);
			return null;
		}
	}

	public static File loadFile(String path) {
		if (path == null) {
			path = "/";
		}

		if(!path.startsWith("/")){
			path = "/"+path;
		}

		return new File(Paths.get("").toAbsolutePath().toString() + path.trim());
	}

	public static void removeFile(String path) {
		 File fileToRemove = new File(path);

		 if(!fileToRemove.exists()){
			 log.warning("Could not find file:"+fileToRemove.getAbsolutePath());
		 }else{
			 fileToRemove.delete();
		 }
	}

	public static InputStream loadResourceFromClasspath(String resourceName){
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
	}

	public static File copyFile(String source, String dest) {

		if (source != null && dest != null) {

			if (!source.startsWith("/")) {
				source = source.substring(source.indexOf("/")+1);//add leading slash to load from classpath (using resources as relative folder)
			}
			/*if (dest.startsWith("/")) { //remove slash to use relative paths. Dest file is saved using folder where Cukedoctor is executed as relative path
				dest = dest.substring(1);
			}*/
			try {
				InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(source);
				//OutputStream out = new FileOutputStream(new File(Paths.get(dest).toAbsolutePath().toString()));
				//IOUtil.copy(in, out);
				return saveFile(dest, IOUtil.toString(in));
			} catch (FileNotFoundException e) {
				log.log(Level.SEVERE, "Could not copy source file: " + source + " to dest file: " + dest, e);
			} catch (IOException e) {
				log.log(Level.SEVERE, "Could not copy source file: " + source + " to dest file: " + dest, e);
			}
		}
		return null;


	}
}
