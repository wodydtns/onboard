package com.superboard.onbrd.global.util;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreateBucketDetails;
import com.oracle.bmc.objectstorage.requests.CreateBucketRequest;
import com.oracle.bmc.objectstorage.requests.GetBucketRequest;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetBucketResponse;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;

public class S3UploadService {

	public void OciUtil() throws Exception {
		String configurationFilePath = "~/.oci/config";
		String profile = "DEFAULT";

		final String compartmentId = "ocid1.user.oc1..aaaaaaaa6bawppxr4ykbigc7gyahga4w7vcpj3mapl6wrew5opite2a4hibq";
		final String bucket = "onboard";
		final String object = "onboard";

		// Configuring the AuthenticationDetailsProvider. It's assuming there is a
		// default OCI config file
		// "~/.oci/config", and a profile in that config with the name "DEFAULT". Make
		// changes to the following
		// line if needed and use ConfigFileReader.parse(CONFIG_LOCATION,
		// CONFIG_PROFILE);

		final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();

		final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);

		ObjectStorage client = new ObjectStorageClient(provider);
		client.setRegion(Region.AP_SEOUL_1);

		System.out.println("Getting the namespace.");
		GetNamespaceResponse namespaceResponse = client.getNamespace(GetNamespaceRequest.builder().build());
		String namespaceName = namespaceResponse.getValue();

		System.out.println("Creating the source object");
		List<GetBucketRequest.Fields> fieldsList = new ArrayList<>(2);
		fieldsList.add(GetBucketRequest.Fields.ApproximateCount);
		fieldsList.add(GetBucketRequest.Fields.ApproximateSize);
		GetBucketRequest request = GetBucketRequest.builder().namespaceName(namespaceName).bucketName(bucket)
				.fields(fieldsList).build();

		System.out.println("Fetching bucket details");
		GetBucketResponse response = client.getBucket(request);

		System.out.println("Bucket Name : " + response.getBucket().getName());
		System.out.println("Bucket Compartment : " + response.getBucket().getCompartmentId());
		System.out.println("The Approximate total number of objects within this bucket : "
				+ response.getBucket().getApproximateCount());
		System.out.println("The Approximate total size of objects within this bucket : "
				+ response.getBucket().getApproximateSize());
	}
}
