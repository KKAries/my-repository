package com.mypackage;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class MyNioExample {

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
		copyFile("E:/mytext.txt", "E:/test/newtext.txt");
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
	
	public static void copyFile (String sourceFile, String destFile) throws IOException {
		Path source = Paths.get(sourceFile);
		Path destination = Paths.get(destFile);
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
	}
}
