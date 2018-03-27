package cn.itcast.fastdfs.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
/**
 * FastDFS测试类
 * @author zhy
 *
 */
public class FastdfsDemo {
    
    public static void main(String[] args) throws IOException, MyException {
        File file = new File("D:\\test\\ly.jpg");
        // 加载配置文件
        ClientGlobal.init("fdfs_client.conf");
        // 创建 tracker客户端
        TrackerClient trackerClient = new TrackerClient();
        // 获取tracker连接
        TrackerServer trackerServer = trackerClient.getConnection();

        // 查看storage客户端
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);

        // 获取扩展名
        String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);

        // 上传
        String filePath = storageClient.upload_file1(Files.readAllBytes(file.toPath()), extension, null);

        System.out.println(filePath);
    }
}
