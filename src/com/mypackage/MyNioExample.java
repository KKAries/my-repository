package com.mypackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyNioExample {
	static final Path testPath = Paths.get("E:/mytext.txt");
	
	
	public static void main(String[] args) throws IOException {
		Path prefixPath = Paths.get("/src/");
		Path path = Paths.get("com");
		Path completePath = prefixPath.resolve(path);
		Path path1 = Paths.get("c:/dev/project/log");
		Path path2 = Paths.get("c:/dev");

		
		File file = new File("../MyTest");
		Path mypath = file.toPath();
		
		//getJavaFiles("E:/Dev Folder/JavaWorkplace/Interview/src/com/mypackage");
		//getJavaFilesRecursively("E:/Dev Folder/JavaWorkplace/");
		//copyFile("E:/mytext.txt", "E:/test/newtext.txt");
		//readFileAttributesDemo("E:/mytext.txt");
		//setFileAttributeDemo();
		//writeFileDemo();
		//readFileDemo();
		//writeFileEasyDemo();
		//readFileEasyDemo();
		//watchFileDemo();
		seekableChannelDemo();
	}
	
	public static void getJavaFiles(String path){
		Path sourcePath = Paths.get(path);
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath, "*.java")){
			for (Path entry:stream) {
				System.out.println(entry.getFileName());
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void getJavaFilesRecursively(String path) throws IOException {
		Path dir = Paths.get(path);
		Files.walkFileTree(dir, new FindJavaVisitor());
	}
	
	private static class FindJavaVisitor extends SimpleFileVisitor<Path> {
		
		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
			if (path.toString().endsWith("java")){
				System.out.println(path.getFileName());
			}
			return FileVisitResult.CONTINUE;
		}
	}
	
	public static void readFileAttributesDemo(String path) {
		Path dir = Paths.get(path);
		try {
			System.out.println(Files.getLastModifiedTime(dir));
			System.out.println(Files.size(dir));
			System.out.println(Files.isDirectory(dir));
			System.out.println(Files.readAttributes(dir, "*"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void copyFile (String sourceFile, String destFile) throws IOException {
		Path source = Paths.get(sourceFile);
		Path destination = Paths.get(destFile);
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static void setFileAttributeDemo() {
		try {
			Path testFilePath = Paths.get("E:/mytext.txt");
			PosixFileAttributes attrs = Files.readAttributes(testFilePath, PosixFileAttributes.class);
			Set<PosixFilePermission> permissions = attrs.permissions();
			String permsString = PosixFilePermissions.toString(permissions);
			System.out.println(permsString);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void readFileDemo() {
		try(BufferedReader reader = Files.newBufferedReader(testPath, StandardCharsets.UTF_8)){
			String lineString;
			while((lineString=reader.readLine())!=null){
				System.out.println(lineString);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeFileDemo() {
		try(BufferedWriter writer = Files.newBufferedWriter(testPath, 
				StandardCharsets.UTF_8, StandardOpenOption.APPEND)){
			writer.write("Hellow world!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readFileEasyDemo() {
		try {
			List<String> lines = Files.readAllLines(testPath, StandardCharsets.UTF_8);
			for(String line:lines){
				System.out.println(line);
			}
			byte[] bytes = Files.readAllBytes(testPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeFileEasyDemo() {
		List<String> lines = new ArrayList<String>();
		lines.add("of");
		lines.add("everything");
		try {
			Files.write(testPath, lines, StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void watchFileDemo(){
		try {
			WatchService watcher = 
					FileSystems.getDefault().newWatchService();
			
			Path path = Paths.get("E:/test/");
			WatchKey key = path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
			while(true) {
				key = watcher.take();
				for (WatchEvent<?> event:key.pollEvents()){
					if (event.kind()== StandardWatchEventKinds.ENTRY_MODIFY){
						Path contextPath = (Path)event.context();
						System.out.println("the test dir modified:" + contextPath.getFileName());
					}
				}
				key.reset();
			}
		}
		catch (IOException | InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public static void seekableChannelDemo(){
		ByteBuffer buffer = ByteBuffer.allocate(10);
		try {
			FileChannel channel = FileChannel.open(testPath, StandardOpenOption.READ);
			channel.read(buffer, channel.size()-10);
			byte[] bytes = buffer.array();
			Path path = Paths.get("E:/test/newtext.txt");
			Files.write(path, bytes, StandardOpenOption.WRITE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
