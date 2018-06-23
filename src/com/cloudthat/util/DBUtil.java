package com.cloudthat.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.table.CloudTableClient;

public class DBUtil {

	//TODO Add Your Storage Account Connection String
	public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=empstg;AccountKey=A4gr4eaot2R9JztO9tjP2boBy0/MR56RKNHg4n1t4f8ex5GUglkwohkwcsWLTAIZbGVVrhaT08IkLcdPG70niw==;EndpointSuffix=core.windows.net";
	protected static CloudTableClient tableClient = null;
	protected static CloudBlobClient blobClient = null;

	/**
	 * Validates the connection string and returns the storage table client. The
	 * connection string must be in the Azure connection string format.
	 *
	 * @return The newly created CloudTableClient object
	 *
	 * @throws RuntimeException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws IllegalArgumentException
	 * @throws InvalidKeyException
	 */
	public static CloudTableClient getTableClientReference()
			throws RuntimeException, IOException, IllegalArgumentException, URISyntaxException, InvalidKeyException {

		CloudStorageAccount storageAccount;
		try {
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
		} catch (IllegalArgumentException | URISyntaxException e) {
			System.out.println("\nConnection string specifies an invalid URI.");
			System.out.println("Please confirm the connection string is in the Azure connection string format.");
			throw e;
		} catch (InvalidKeyException e) {
			System.out.println("\nConnection string specifies an invalid key.");
			System.out.println("Please confirm the AccountName and AccountKey in the connection string are valid.");
			throw e;
		}

		return storageAccount.createCloudTableClient();
	}

	/**
	 * Validates the connection string and returns the storage blob client. The
	 * connection string must be in the Azure connection string format.
	 *
	 * @return The newly created CloudBlobClient object
	 *
	 * @throws RuntimeException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws IllegalArgumentException
	 * @throws InvalidKeyException
	 */
	public static CloudBlobClient getBlobClientReference()
			throws RuntimeException, IOException, IllegalArgumentException, URISyntaxException, InvalidKeyException {
		CloudStorageAccount storageAccount;
		try {
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
		} catch (IllegalArgumentException | URISyntaxException e) {
			System.out.println("\nConnection string specifies an invalid URI.");
			System.out.println("Please confirm the connection string is in the Azure connection string format.");
			throw e;
		} catch (InvalidKeyException e) {
			System.out.println("\nConnection string specifies an invalid key.");
			System.out.println("Please confirm the AccountName and AccountKey in the connection string are valid.");
			throw e;
		}
		return storageAccount.createCloudBlobClient();
	}

}
