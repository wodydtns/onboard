package com.superboard.onbrd.global.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.ListObjects;
import com.oracle.bmc.objectstorage.model.ObjectSummary;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.ListObjectsRequest;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import com.oracle.bmc.objectstorage.responses.ListObjectsResponse;

public class S3UploadService {
	
	public final String bucketName = "onboard";
	
	public void OciUtil() throws Exception {
		ObjectStorage client = getObjectStorageClient();

		System.out.println("Getting the namespace.");
		String namespaceName = getNameSpaceName(client);

		System.out.println("Creating the source object");
		List<GetBucketRequest.Fields> fieldsList = new ArrayList<>(2);
		fieldsList.add(GetBucketRequest.Fields.ApproximateCount);
		fieldsList.add(GetBucketRequest.Fields.ApproximateSize);
		GetBucketRequest request = GetBucketRequest.builder().namespaceName(namespaceName).bucketName(bucketName)
				.fields(fieldsList).build();

		System.out.println("Fetching bucket details");
		GetBucketResponse response = client.getBucket(request);

		System.out.println("Bucket Name : " + response.getBucket().getName());
		System.out.println("Bucket Compartment : " + response.getBucket().getCompartmentId());
		System.out.println("The Approximate total number of objects within this bucket : "
				+ response.getBucket().getApproximateCount());
		System.out.println("The Approximate total size of objects within this bucket : "
				+ response.getBucket().getApproximateSize());
		
		client.close();
	}

	public void getObjectOne(String fileName) throws Exception {
		ObjectStorage client = getObjectStorageClient();

		String namespaceName = getNameSpaceName(client);
		
		String objectName = "onboard/" + fileName;

		GetObjectRequest request = GetObjectRequest.builder().namespaceName(namespaceName).bucketName(bucketName)
				.objectName(objectName).build();

		GetObjectResponse response = client.getObject(request);
		System.out.println(response);

		client.close();
	}
	
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
