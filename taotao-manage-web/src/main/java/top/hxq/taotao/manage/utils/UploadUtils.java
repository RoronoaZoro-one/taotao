package top.hxq.taotao.manage.utils;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {

	/**
	 * 上传图片并返回url
	 * 
	 * @param fileName
	 * @return
	 */
	public static String uploadImage(MultipartFile multipartFile) throws Exception {
		String conf_filename = UploadUtils.class.getClassLoader().getResource("tracker.conf").getPath();
		// 初始化配置，设置tracker的服务器地址
		ClientGlobal.init(conf_filename);
		// 获得存储服务器对象，可以为null
		StorageServer storageServer = null;
		// 创建trackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		// 获得追踪服务器
		TrackerServer trackerServer = trackerClient.getConnection();
		// 获得strorageClient对象
		StorageClient strorageClient = new StorageClient(trackerServer, storageServer);
		// 获得文件后缀名
		String file_ext_name = StringUtils.substringAfter(multipartFile.getOriginalFilename(), ".");
		// 上传文件（文件字节数组，文件后缀名，文件信息）
		String[] file = strorageClient.upload_appender_file(multipartFile.getBytes(), file_ext_name, null);
		// 上传成功后的组名
		String groupName = file[0];
		// 上传成功后的文件路径名
		String filename = file[1];
		// 获得追踪服务器的信息
		ServerInfo[] storages = trackerClient.getFetchStorages(trackerServer, groupName, filename);
		// 组合可访问路径
		String url = "http://" + storages[0].getIpAddr() + "/" + groupName + "/" + filename;
		return url;
	}

}
