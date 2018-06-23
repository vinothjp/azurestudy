package com.cloudthat.dbo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import com.cloudthat.model.EmployeeEntity;
import com.cloudthat.util.DBUtil;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.blob.*;
import java.io.*;

public class EmployeeDbo {

	protected static CloudTableClient tableClient = null;
	protected static CloudTable table = null;
	String tableName = "EmployeeTable";

	// TODO Insert the constructor code over here
	public EmployeeDbo()
			throws InvalidKeyException, IllegalArgumentException, RuntimeException, IOException, URISyntaxException {
		tableClient = DBUtil.getTableClientReference();
		// Create a new table if not created
		try {
			table = createTable(tableClient, tableName);
		} catch (StorageException | InvalidKeyException | RuntimeException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	// TODO Insert the addEmployee Method Code over here
	public void addEmployee(EmployeeEntity employee) {
		try {
			// Create and insert new customer entities
			table.execute(TableOperation.insert(employee));
		} catch (StorageException e) {
			e.printStackTrace();
		}
	}

	// TODO Insert the deleteEmployee method Code over here
	public void deleteEmployee(String partitionKey, String rowKey) {
		if (table == null) {
			try {
				table = tableClient.getTableReference(tableName);
			} catch (URISyntaxException | StorageException e) {
				e.printStackTrace();
			}
		}
		// delete an entity
		try {
			// Create an operation to retrieve the entity with partition key of
			// and row key
			TableOperation retrieveEmployee = TableOperation.retrieve(partitionKey, rowKey, EmployeeEntity.class);
			// Retrieve the entity
			EmployeeEntity employeeToDelete = table.execute(retrieveEmployee).getResultAsType();
			table.execute(TableOperation.delete(employeeToDelete));
		} catch (StorageException e) {
			e.printStackTrace();
		}
	}

	// TODO Insert the getAllEmployee method code over here
	public List<EmployeeEntity> getAllEmployee() {
		List<EmployeeEntity> employeesList = new ArrayList<EmployeeEntity>();
		// Create the partition scan query
		TableQuery<EmployeeEntity> partitionScanQuery = TableQuery.from(EmployeeEntity.class);

		if (table == null) {
			try {
				table = tableClient.getTableReference(tableName);
			} catch (URISyntaxException | StorageException e) {
				e.printStackTrace();
			}
		}

		// Iterate through the results
		for (EmployeeEntity entity : table.execute(partitionScanQuery)) {
			EmployeeEntity employee = new EmployeeEntity(entity.getPartitionKey(), entity.getRowKey());
			employee.setEmail(entity.getEmail());
			employee.setProfileURL(entity.getProfileURL());
			employeesList.add(employee);
		}

		return employeesList;
	}

	// TODO Insert the createTable code over here
	private static CloudTable createTable(CloudTableClient tableClient, String tableName)
			throws StorageException, RuntimeException, IOException, InvalidKeyException, IllegalArgumentException,
			URISyntaxException, IllegalStateException {
		// Create a new table
		CloudTable table = tableClient.getTableReference(tableName);
		try {
			if (table.createIfNotExists() == false) {
				throw new IllegalStateException(String.format("Table with name \"%s\" already exists.", tableName));
			}
		} catch (StorageException s) {
			if (s.getCause() instanceof java.net.ConnectException) {
				System.out.println(
						"Caught connection exception from the client. If running with the default configuration please make sure you have started the storage emulator.");
			}
			throw s;
		}
		return table;
	}

	// TODO Insert the uploadImageToBlob code over here
	public String uploadImageToBlob(InputStream inputStream, String blobName) {
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;

		// Create a Blob Client Reference
		try {
			blobClient = DBUtil.getBlobClientReference();
		} catch (InvalidKeyException | RuntimeException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		// Create a container to store all the blobs or if created get its
		// reference
		try {
			container = blobClient.getContainerReference("employeeimg");
			// Create the container and if it was not created then change its
			// permission to allow public access
			if (container.createIfNotExists()) {
				// Set anonymous access on the container.
				BlobContainerPermissions containerPermissions;
				containerPermissions = new BlobContainerPermissions();
				containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
				container.uploadPermissions(containerPermissions);
			}
		} catch (URISyntaxException | StorageException e) {
			e.printStackTrace();
		}

		// Get a reference to the blob
		try {
			blob = container.getBlockBlobReference(blobName);
		} catch (URISyntaxException | StorageException e) {
			e.printStackTrace();
		}

		// File to upload
		// Upload the file to the container
		try {
			blob.upload(inputStream, inputStream.available());
		} catch (StorageException | IOException e) {
			e.printStackTrace();
		}

		// After the images is uploaded to blob storage return its URI
		return blob.getUri().toString();

	}
}
