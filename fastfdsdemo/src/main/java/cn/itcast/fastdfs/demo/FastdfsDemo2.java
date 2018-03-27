package cn.itcast.fastdfs.demo;

import java.io.File;
import java.nio.file.Files;

import org.csource.fastdfs.ClientGlobal;

import cn.itcast.fastdfs.utils.FastDFSClient;

public class FastdfsDemo2 {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\test\\ly.jpg");
        // 加载配置文件
        ClientGlobal.init("fdfs_client.conf");
        // 创建客户端
        FastDFSClient dfsClient = new FastDFSClient("fdfs_client.conf");
        // 上传
        String filePath = dfsClient.uploadFile(Files.readAllBytes(file.toPath()), file.getName());
        
        System.out.println(filePath);
    }
}
