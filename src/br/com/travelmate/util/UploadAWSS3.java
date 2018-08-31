package br.com.travelmate.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

public class UploadAWSS3 {
	
	private String bucket;
	private String clientRegion;
	private String accesskey;
	private String secretaccesskey;
	
	public UploadAWSS3(String tipo) {
		this.clientRegion="us-east-1";
		accesskey = "AKIAIMOELM7MJGHUZRAQ";
		secretaccesskey = "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";
		if (tipo.equalsIgnoreCase("orcamento")) {
				bucket = "orcamentos.systm.com.br";
		}else if (tipo.equalsIgnoreCase("arquivos")){
			bucket = "arquivos.systm.com.br";
		}else if (tipo.equalsIgnoreCase("docs")) {
			
		}else if (tipo.equalsIgnoreCase("imagens")) {
			
		}else if (tipo.equalsIgnoreCase("remessa")) {
			bucket = "remessa.systm.com.br";
		}
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	
	public boolean uploadFile(File file) {
		
		 
		 String keyName = file.getName();
		 
	        

	        try {
	        	
	        	BasicAWSCredentials awsCreds = new BasicAWSCredentials(accesskey, secretaccesskey);
	        	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	        			 				.withRegion(clientRegion)
	        	                       .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
	        	                       .build();
	            TransferManager tm = TransferManagerBuilder.standard()
	                    .withS3Client(s3Client)
	                    .build();
	            Upload upload = tm.upload(bucket, keyName, file);
	            upload.waitForCompletion();
	            return true;
	        }
	        catch(AmazonServiceException e) {
	            e.printStackTrace();
	        }
	        catch(SdkClientException e) {
	            e.printStackTrace();
	        } catch (AmazonClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return false;
		
	}
	
	public List<S3ObjectSummary> list() {
	
	List<S3ObjectSummary> lista = new ArrayList<S3ObjectSummary>();
    
    try {    
    	BasicAWSCredentials awsCreds = new BasicAWSCredentials(accesskey, secretaccesskey);
    	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
    			 				.withRegion(clientRegion)
    	                       .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
    	                       .build();

        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucket).withMaxKeys(2);
        ListObjectsV2Result result;

        do {
            result = s3Client.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                lista.add(objectSummary);
            }
            String token = result.getNextContinuationToken();
            req.setContinuationToken(token);
        } while (result.isTruncated());
    }
    catch(AmazonServiceException e) {
        e.printStackTrace();
    }
    catch(SdkClientException e) {
        e.printStackTrace();
    }
    return lista;
}
	
	public boolean delete(S3ObjectSummary objectSummary) {
		if (objectSummary!=null) {
			try {
				BasicAWSCredentials awsCreds = new BasicAWSCredentials(accesskey, secretaccesskey);
	        	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	        			 				.withRegion(clientRegion)
	        	                       .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
	        	                       .build();

	            s3Client.deleteObject(new DeleteObjectRequest(bucket,objectSummary.getKey()));
	            return true;
	        }
	        catch(AmazonServiceException e) {
	            e.printStackTrace();
	        }
			catch(SdkClientException e) {
	            e.printStackTrace();
	        }
		}
		return false;
		
	}
		

}
