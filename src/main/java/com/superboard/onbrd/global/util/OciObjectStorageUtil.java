package com.superboard.onbrd.global.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public void UploadObject() throws Exception {
		ObjectStorage client = getObjectStorageClient();
		UploadConfiguration uploadConfiguration =UploadConfiguration.builder().allowMultipartUploads(true).allowParallelUploads(true).build();
		UploadManager uploadManager = new UploadManager(client, uploadConfiguration);
		String namespaceName = getNameSpaceName(client);
		
		// bucket에 넣을 파일 이름
        String objectName = "review/test_object.txt";
        Map<String, String> metadata = null;
        String contentType = "text/plain";
        
        String contentEncoding = null;
        String contentLanguage = null;
        
        // 실제 파일
        File body = new File("D:/onbrd/repo.txt");

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
        
        client.close();
	}
	/**
	 * @throws Exception
	 * @method : 파일 삭제
	 */
	public void deleteObject() throws Exception {
		ObjectStorage client = getObjectStorageClient();
		
		String namespaceName = getNameSpaceName(client);
		
        DeleteObjectRequest request = 
        		DeleteObjectRequest.builder()
        			.bucketName(bucketName)
        			.namespaceName(namespaceName)
        			.objectName("test_object.txt")
        			.build();
        		
        
        client.deleteObject(request);
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
