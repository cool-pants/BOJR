package com.application.archive.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.application.archive.ArchiveEntry;
import com.application.archive.ArchiveFormat;
import com.application.archive.ArchiveStream;
import com.application.archive.Archiver;
import com.application.archive.ArchiverFactory;

public class ExtractArchive {

	public static void main(String[] args) {
		
		// Hardcoded for candidate testing purposes
		String sourceFile = "test.zip";
		String destinationFolder = "testZipExtracted";
		
		File archive = new File( sourceFile );
		File destination = new File( destinationFolder );

		Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.ZIP);
		try {
			ArchiveStream stream = archiver.stream(archive);
			ArchiveEntry entry;

			while((entry = stream.getNextEntry()) != null) {
				if(entry.isDirectory()){
					String path = destinationFolder+"/"+entry.getName();
					if(!Files.isDirectory(Paths.get(path))){
						new File(path).mkdirs();
						continue;
					}
				}
				entry.extract(destination);
			}
			stream.close();
			System.out.println("Done.");
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}
}
