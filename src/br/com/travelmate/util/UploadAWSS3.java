package br.com.travelmate.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.UploadedFile;

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
	private AWSPropertie awsPropertie;
	
	public UploadAWSS3(String tipo, String caminho) {
		awsPropertie = new AWSPropertie();
		awsPropertie.carregarInformacoes(caminho);
		if (tipo.equalsIgnoreCase("orcamento")) {
				bucket = awsPropertie.getBucketOrcamento();
		}else if (tipo.equalsIgnoreCase("arquivos")){
			bucket = awsPropertie.getBucketArquivo();
		}else if (tipo.equalsIgnoreCase("docs")) {
			bucket = awsPropertie.getBucketDocs();
		}else if (tipo.equalsIgnoreCase("imagens")) {
			bucket = awsPropertie.getBucketImagem();
		}else if (tipo.equalsIgnoreCase("remessa")) {
			bucket = awsPropertie.getBucketRemessa();
		}else if (tipo.equalsIgnoreCase("treinamento")) {
			bucket = awsPropertie.getBucketTreinamento();
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
	        	
	        	BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsPropertie.getAccesskey(), awsPropertie.getSecretaccesskey());
	        	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	        			 				.withRegion(awsPropertie.getClientRegion())
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
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
	        return false;
		
	}
	
	public List<S3ObjectSummary> list() {
	
	List<S3ObjectSummary> lista = new ArrayList<S3ObjectSummary>();
    
    try {    
    	BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsPropertie.getAccesskey(), awsPropertie.getSecretaccesskey());
    	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
    			 				.withRegion(awsPropertie.getClientRegion())
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
				BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsPropertie.getAccesskey(), awsPropertie.getSecretaccesskey());
	        	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	        			 				.withRegion(awsPropertie.getClientRegion())
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
	
	public File getFile(UploadedFile fileNome, String nomeArquivo) {
		File file = null;
		try {
			//InputStream in = new BufferedInputStream(fileNome.getInputstream());
			file = new File(nomeArquivo);
			OutputStream outputStream = new FileOutputStream(file);
			outputStream.write(fileNome.getContents());
			outputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return file;
	}
		

}
