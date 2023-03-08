package com.superboard.onbrd.global.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.ListObjects;
import com.oracle.bmc.objectstorage.model.ObjectSummary;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.ListObjectsRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;
import com.oracle.bmc.objectstorage.transfer.UploadConfiguration;
import com.oracle.bmc.objectstorage.transfer.UploadManager;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadRequest;
import com.oracle.bmc.objectstorage.transfer.UploadManager.UploadResponse;

@Component
public class OciObjectStorageUtil {
	
	public final String bucketName = "onboard";
	
	/**
	 * @param fileName 
	 * @throws Exception
	 * @method : 버킷의 특정 파일 가져오기
	 */
	public void getObjectOne(String fileName) throws Exception {
		ObjectStorage client = getObjectStorageClient();

		String namespaceName = getNameSpaceName(client);
		
		String objectName = "boardgame/" + fileName;

		GetObjectRequest request = GetObjectRequest.builder().namespaceName(namespaceName).bucketName(bucketName)
				.objectName(objectName).build();

		GetObjectResponse response = client.getObject(request);
		System.out.println(response);
		System.out.println(response.getETag());

		client.close();
	}
	
	/**
	 * @throws Exception
	 * @method : bucket의 모든 파일 가져오기
	 */
	public void getObjectList() throws Exception{
		ObjectStorage client = getObjectStorageClient();
		
		String namespaceName = getNameSpaceName(client);
        
        ListObjectsRequest request =
        		ListObjectsRequest.builder()
        			.namespaceName(namespaceName)
        			.bucketName(bucketName)
        			.build();
        
        ListObjectsResponse response = client.listObjects(request);
        
        ListObjects list = response.getListObjects();
        List<ObjectSummary> objectList = list.getObjects();
        
        
        for(int i=0; i<10; i++) {
        	System.out.println("====================");
        	System.out.println("@@@@@@@@@@@@@@@@@ getName : " + objectList.get(i).getName());
        	System.out.println("@@@@@@@@@@@@@@@@@ getArchivalState : " + objectList.get(i).getArchivalState());
        	System.out.println("@@@@@@@@@@@@@@@@@ getSize : " + objectList.get(i).getSize());
        	System.out.println("@@@@@@@@@@@@@@@@@ getTimeCreated : " + objectList.get(i).getTimeCreated());
        	System.out.println("@@@@@@@@@@@@@@@@@ getTimeModified : " + objectList.get(i).getTimeModified());
        	System.out.println("@@@@@@@@@@@@@@@@@ getTimeModified : " + objectList.get(i).getEtag());
        }
        
        System.out.println(response.getListObjects());
        client.close();
	}
	/**
	 * @throws Exception
	 * @method : 파일 업로드
	 */
	public boolean UploadObject(MultipartFile file) throws Exception {
		boolean successFlag = false;
		ObjectStorage client = getObjectStorageClient();
		FileOutputStream outputStream = null;
		
		BufferedOutputStream bos = null;
		
		try {
			UploadConfiguration uploadConfiguration =UploadConfiguration.builder().allowMultipartUploads(true).allowParallelUploads(true).build();
			UploadManager uploadManager = new UploadManager(client, uploadConfiguration);
			String namespaceName = getNameSpaceName(client);
			
			// bucket에 넣을 파일 이름
			// 경로 추가 필요
	        String objectName = "boardgame/" +file.getOriginalFilename();
	        Map<String, String> metadata = null;
	        
	        String contentType = file.getContentType();
	        
	        String contentEncoding = null;
	        String contentLanguage = null;
	        
	        // 실제 파일
	        
	        InputStream inputStream = file.getInputStream();
	        
	        File body = new File(file.getName());
	        outputStream = new FileOutputStream(body);
	        
	        bos = new BufferedOutputStream(outputStream);
	        
	        bos.write(file.getBytes());
	        inputStream.close();
	        outputStream.close();
	        bos.close();
	        
	        PutObjectRequest request =
		                PutObjectRequest.builder()
		                        .bucketName(bucketName)
		                        .namespaceName(namespaceName)
		                        .objectName(objectName)
		                        .contentType(contentType)
		                        .contentLanguage(contentLanguage)
		                        .contentEncoding(contentEncoding)
		                        .opcMeta(metadata)
		                        .build();
			
			
			UploadRequest uploadDetails =
		                UploadRequest.builder(body).allowOverwrite(true).build(request);
			
			UploadResponse response = uploadManager.upload(uploadDetails);
			System.out.println(response);
			if(response.getETag() != null) {
				successFlag = true;
			}
			
			client.close();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			if(client != null ) {
				client.close();
			}
			if(outputStream != null) {
				outputStream.close();
			}
			if(bos != null) {
				bos.close();
			}
		}
		return successFlag;
		
        
	}
	/**
	 * @throws Exception
	 * @method : 파일 삭제
	 */
	public void deleteObject(List<String> imageList) throws Exception {
		ObjectStorage client = getObjectStorageClient();
		
		String namespaceName = getNameSpaceName(client);
		for (String imageName : imageList) {
			String objectName = "boardgame/" + imageName;
			DeleteObjectRequest request = 
					DeleteObjectRequest.builder()
					.bucketName(bucketName)
					.namespaceName(namespaceName)
					.objectName(objectName)
					.build();
			
			client.deleteObject(request);
		}
        client.close();
	}
	
	private ObjectStorage getObjectStorageClient() throws IOException {
		ConfigFile config = ConfigFileReader.parse("~/.oci/config", "DEFAULT");

		AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(config);

		ObjectStorage client = new ObjectStorageClient(provider);
		client.setRegion(Region.AP_SEOUL_1);
		return client;
	}
	
	private String getNameSpaceName(ObjectStorage client) {
		GetNamespaceResponse namespaceResponse = client.getNamespace(GetNamespaceRequest.builder().build());
		String namespaceName = namespaceResponse.getValue();
		return namespaceName;
	}

}
