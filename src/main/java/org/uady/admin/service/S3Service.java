package org.uady.admin.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {
	
	public String subirFotoPerfil(String bucketName, String id, MultipartFile file) {
		try {
			File modifiedFile=new File(file.getOriginalFilename());
			FileOutputStream os=new FileOutputStream(modifiedFile);
			os.write(file.getBytes());
			os.close();
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withRegion(Regions.US_EAST_1)
					.withCredentials(new AWSStaticCredentialsProvider(new BasicSessionCredentials(
							"ASIAVNYK52H4ZERFNAOQ",
							"Q+p1TE4PIyDOv5h1huLmg1o1pwPk5S9GKQZOOwh1",
							"FwoGZXIvYXdzENL//////////wEaDAKuzlLD4IXisXM9QSLLAQJx4cnN/3GCvMcee7FF0OnCQ2XV3SVbQ1A9zAiKyhSVaXOzvFWzdFrbgXh2M2AE7+PdHecciuMXLLbm5hLCrv+219zLEk+yct2kGKjhk/3hxGp1yStqQ2bY11juAW36WPc0aSxnuvK1S1dFhLZa9SCiVRKAiVr+vyGIPDZJ9Ym07SBl7PWLz4ow/0o/+k9Ss6KoQcbcqNwgAnbWizA0pbSF4CWbXVFQzWvItw853uTPCxk2IQvURGUJxvR3cC/xCmxEBmcGgGVJFCMPKJ6B5KsGMi1UUpz3itVOxqLfWMHr8uFm8PQZoE/aFv73DHMXlVqFnrHFF6k1B9P383tZu38=")))
					.build();
		    TransferManager tm = null;
        
            tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .withMultipartUploadThreshold((long) (104857600))
                    .withMinimumUploadPartSize((long) (104857600))
                    .build();
            Upload upload = tm.upload(bucketName, id, modifiedFile);
            System.out.println("Object upload started");
            upload.waitForCompletion();
            System.out.println("Object upload complete");
            tm.shutdownNow();
            modifiedFile.delete();
            return s3Client.getUrl(bucketName, id).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
        
	}
	
}
