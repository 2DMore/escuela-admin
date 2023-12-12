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
							"ASIAVNYK52H4Q37WQM5I",
							"82EA5DGpuM+BNbszhKlXaa+z2ptlaMSJHIyq//ty",
							"FwoGZXIvYXdzEM3//////////wEaDLsnvEXMdQVhRZzkJCLLAZdJInHo9xFMGdz+dWyQOvpHWMtFRggNK5m4Y0d5EyNlPMTFZKSP8ZmPxdFWf+32I/Q0+yKA+vmLDulo8InCPDP6EXyPhERlWF8nTfSqHHnzvzJ7RpCDMuG+/cxfYabfxdLvk63XEdCDonfd3BvnV/8F1SUlI3hdpvZBSbtMBgTL7MbaGAeOVR6fu3jgqTis1KcKCxA5Y5cjikFmh6FpJ3yf/d4f4f0y8+dDuxE3IFKsWXhsp7WFw9jcMrCLWeG+BAd3JcMWmV7r3Y+qKLr44qsGMi04XpmibuQyGKUSxDHQ7P4JDsEWjbyKQITCdvUo5C3eaRxsSqMA+K+bvTtZeHI=")))
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
