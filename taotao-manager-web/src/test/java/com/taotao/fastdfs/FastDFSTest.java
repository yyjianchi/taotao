package com.taotao.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.managerweb.util.FastDFSClient;

public class FastDFSTest {
	

	//此方法会报FILENotFound异常，原因不详
	//下面使用工具类上传的方法不会报异常
//	public void testFileUpload() throws Exception {
//		String filename="‪D:\\eclipseMars\\workspaces\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf";
//		// 1、加载配置文件，配置文件中的内容就是tracker服务的地址。
//		ClientGlobal.init(filename);
//		// 2、创建一个TrackerClient对象。直接new一个
//		TrackerClient trackerClient=new TrackerClient();
//		// 3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
//		TrackerServer trackerServer=trackerClient.getConnection();
//		// 4、创建一个StorageServer的引用，值为null
//		StorageServer storageServer=null;
//		// 5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
//		StorageClient storageClient=new StorageClient(trackerServer, storageServer);
//		// 6、使用StorageClient对象上传图片。
//		//扩展名不带“.”
//		String[] strings=storageClient.upload_file("‪D:/Documents/Pictures/abc.jpg", "jpg", null);
//		// 7、返回数组。包含组名和图片的路径。
//		for (String string : strings) {
//			System.out.println(string);
//		}
	@Test
	public void testFastDfsClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("D:\\eclipseMars\\workspaces\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf");
		String file = fastDFSClient.uploadFile("D:\\Temp\\abc.jpg");
		System.out.println(file);
	}
}
