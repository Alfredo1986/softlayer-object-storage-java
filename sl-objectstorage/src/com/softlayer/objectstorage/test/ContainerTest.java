package com.softlayer.objectstorage.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.softlayer.objectstorage.Container;
import com.softlayer.objectstorage.ObjectFile;


public class ContainerTest  {
	
	
	 private static String username="<your user here>";
	 private static String apiKey="<your password here>"; 
	 private static String baseUrl="<your baseUrl here>";

	 
	 
	 @Test
	 public void testCheckFileExist() throws Exception {
	    	
	    	Container container = new Container("RestletContainer", baseUrl, username, apiKey, true);
			container.create();
	   
			InputStream inputstream = new FileInputStream("restletupload.txt");
			ObjectFile ofile = new ObjectFile("restletupload.txt","RestletContainer", baseUrl, username, apiKey, true);
			Map<String, String> tags = new HashMap<String, String>();
			tags.put("testtag", "testvalue");
			ofile.uploadFile(inputstream, tags);
			
			
			boolean exist = container.checkFileExist("RestletContainer", "restletupload.txt");
			assertTrue(exist);
		
			ofile.remove();
			exist = container.checkFileExist("RestletContainer", "restletupload.txt");
			assertFalse(exist);
			container.remove();
			
			
	 }	
	 
	 
	 @Test
	 public void testGetSubContainerList() throws Exception {
			
		 	Container container = new Container("RestletContainer", baseUrl, username, apiKey, true);
			container.create();
	   
			InputStream inputstream = new FileInputStream("restletupload.txt");
			ObjectFile ofile = new ObjectFile("restletupload.txt","RestletContainer/subContainer", baseUrl, username, apiKey, true);
			Map<String, String> tags = new HashMap<String, String>();
			tags.put("testtag", "testvalue");
			ofile.uploadFile(inputstream, tags);
			
			
			boolean exist = container.checkFileExist("RestletContainer/subContainer", "restletupload.txt");
			assertTrue(exist);
			
			// Test getSubContainerList
			List<ObjectFile> subContainerList = container.getSubContainerList("subContainer");
			assertEquals(1,subContainerList.size());
			assertEquals("subContainer/restletupload.txt",subContainerList.get(0).getName() );
			
			// Test getObjectFileByName
			ObjectFile myFile = container.getObjectFileByName("RestletContainer/subContainer", "restletupload.txt");
			assertNotNull(myFile);
			assertEquals("restletupload.txt",myFile.getName());
			
			myFile.remove();
			exist = container.checkFileExist("RestletContainer/subContainer", "restletupload.txt");
			assertFalse(exist);
			container.remove();			
			
	    }

}