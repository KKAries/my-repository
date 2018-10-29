package com.mypackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MyNioExample {
	static final Path prefixPath = Paths.get("d:/demo/files");
	static final Path testReadPath = prefixPath.resolve("testread.txt");
	static final Path testWritePath = prefixPath.resolve("testwrite.txt");
	static final Path logFilePath = prefixPath.resolve("wwtools.log");
	static final Path fakeAbsolutePath = Paths.get("e:/dir1/dir2/dir3/dir4/fake.java");
	static final Path fakeRelativePath = Paths.get("dir1/dir2/dir3/dir4/fake.java");
	static final Path symbolicLinkPath = Paths.get("c:/dev");
	static final Path testSourcePath = prefixPath.resolve("source.txt");
	static final Path testDestPath = prefixPath.resolve("dest.txt");
	
	
	public static void main(String[] args) throws IOException {		
		//getPathInfoDemo(fakeAbsolutePath);
		//convertPathDemo(symbolicLinkPath);
		//getJavaFiles("d:/dev/GIT/my-repository/src/com/");
		//getJavaFilesRecursively("d:/dev/GIT");
		//copyFile(testSourcePath, testDestPath);
		//readFileAttributesDemo(logFilePath);
		//setFileAttributeDemo(logFilePath);
		//readWriteSmallFileDemo(testReadPath, testWritePath);
		//bufferedIODemo(testReadPath, testWritePath);
		//UnbufferedStreamsDemo(testReadPath, testWritePath);
		//watchFolderDemo(prefixPath);
		//seekableChannelDemo(testReadPath, testWritePath);
		//FileChannelDemo(testReadPath, testWritePath);
		asyncCallbackStyleDemo(logFilePath);
		//asyncFutureStyleDemo(logFilePath);
	}
	
	public static void getPathInfoDemo(Path path){
		System.out.format("toString: %s%n", path.toString());
		System.out.format("getFileName: %s%n", path.getFileName());
		System.out.format("getName(0): %s%n", path.getName(0));
		System.out.format("getNameCount: %d%n", path.getNameCount());
		System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
		System.out.format("getParent: %s%n", path.getParent());
		System.out.format("getRoot: %s%n", path.getRoot());
	}
	
	public static void convertPathDemo(Path path){
		System.out.format("toString: %s%n", path.toString());
		System.out.format("toUri: %s%n", path.toUri());
		System.out.format("normalize: %s%n", path.normalize());
		System.out.format("toAbsolute: %S%n", path.toAbsolutePath());
		System.out.format("join two paths: %s%n", path.resolve("myproject"));
		try {
			System.out.format("toReal: %s%n", path.toRealPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Path path1 = Paths.get("Alice");
		Path path2 = Paths.get("Bob");
		System.out.format("path from Alice to Bob: %s%n", path1.relativize(path2));
		
		if (path1.equals(path2)) {
			System.out.println("path 1 equals to path 2");
		} else {
			System.out.println("path 1 not equals to path 2");
		}
	
	}
	
	public static void fileToPath(){
		File file = new File("../MyTest");
		Path mypath = file.toPath();
	}
	
	public static void getJavaFiles(String path){
		Path sourcePath = Paths.get(path);
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath, "**/*.java")){
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
	
	public static void readFileAttributesDemo(Path path) {
		try {
			System.out.println(Files.getLastModifiedTime(path));
			System.out.println(Files.size(path));
			System.out.println(Files.isDirectory(path));
			System.out.println(Files.readAttributes(path, "*"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void copyFile (Path source, Path dest) throws IOException {
		Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
	}
	
	public static void setFileAttributeDemo(Path path) {
		try {
			DosFileAttributes attr = Files.readAttributes(path, DosFileAttributes.class);
			System.out.print(attr.isReadOnly());
			
			Files.setAttribute(path, "dos:hidden", false);
			Files.setAttribute(path, "dos:readonly", true);
			
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void bufferedIODemo(Path source, Path dest) {
		try(BufferedReader reader = Files.newBufferedReader(source, StandardCharsets.UTF_8);
				BufferedWriter writer = Files.newBufferedWriter(dest, StandardCharsets.UTF_8)){
			String lineString;
			while((lineString=reader.readLine())!=null){
				System.out.println(lineString);
				writer.write(lineString+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void readWriteSmallFileDemo(Path source, Path dest) {
		try {
			List<String> lines = Files.readAllLines(source, StandardCharsets.UTF_8);
			for(String line:lines){
				System.out.println(line);
			}
			Files.write(dest, lines, StandardOpenOption.WRITE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void UnbufferedStreamsDemo(Path source, Path dest){
		try(InputStream in = Files.newInputStream(source);
				OutputStream out = Files.newOutputStream(dest)){
			int c;
			while((c=in.read()) != -1){
				out.write(c);
			}
		} catch (IOException e){
			System.err.println(e);
		}
		
	}
	
	public static void watchFolderDemo(Path directory) throws InterruptedException{
		try {
			WatchService watcher = 
					FileSystems.getDefault().newWatchService();
			
			WatchKey key = directory.register(watcher, new WatchEvent.Kind<?>[]{StandardWatchEventKinds.ENTRY_MODIFY});
			while(true) {
				key= watcher.take();
				for (WatchEvent<?> event : key.pollEvents()){
					Path contextPath = (Path)event.context();
					System.out.println(event);
					System.out.println("the test dir modified:" + contextPath.getFileName() + " -- " + event.kind());
				}
			
				key.reset();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void FileChannelDemo(Path source, Path dest){
		
		try(SeekableByteChannel rsbc = Files.newByteChannel(source);
				SeekableByteChannel wsbc = Files.newByteChannel(dest, StandardOpenOption.WRITE)){
			ByteBuffer buffer = ByteBuffer.allocate(10);
			while(rsbc.read(buffer)>0){
				buffer.flip();
				wsbc.write(buffer);
				buffer.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void seekableChannelDemo(Path source, Path dest){
		ByteBuffer buffer = ByteBuffer.allocate(10);
		try (FileChannel channel = FileChannel.open(source, StandardOpenOption.READ);
				FileChannel writeChannel = FileChannel.open(dest, StandardOpenOption.WRITE)){
			channel.read(buffer, channel.size()-10);
			
			buffer.flip();
			
			writeChannel.write(buffer);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void asyncFutureStyleDemo(Path logFile){
		try{
			
			AsynchronousFileChannel channel = AsynchronousFileChannel.open(logFile);
			ByteBuffer buffer = ByteBuffer.allocate(20_000_000);
			System.out.println("file read start");
			Future<Integer> result = channel.read(buffer, 0);
			
			while(!result.isDone()){
				someOtherWork();
			}
			System.out.println("file read end");
			Integer bytesRead = result.get();
			System.out.println("Bytes read [" + bytesRead + "]");
		}
		catch (IOException | ExecutionException | InterruptedException e){
			System.out.println(e);
		}
	}
	
	private static void someOtherWork(){
		try {
			System.out.println("perform other work");
			Thread.sleep(5000);
			System.out.println("other work done");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void asyncCallbackStyleDemo(Path logFile){
		try {
			
			AsynchronousFileChannel channel = AsynchronousFileChannel.open(logFile);
			ByteBuffer buffer = ByteBuffer.allocate(20_000_000);
			
			channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>(){
				public void completed(Integer result, ByteBuffer attachment){
					System.out.println("Bytes read ["+result+"]");
				}
				
				public void failed(Throwable exception, ByteBuffer attachment) {
					System.out.println(exception.getMessage());
				}
			});
			someOtherWork();
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
}
