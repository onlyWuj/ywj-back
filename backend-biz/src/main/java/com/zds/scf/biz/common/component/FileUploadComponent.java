package com.zds.scf.biz.common.component;

import com.cp.boot.core.EnvironmentHolder;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zds.common.lang.exception.Exceptions;
import com.zds.common.util.StringUtils;
import com.zds.scf.biz.common.dto.FileUploadDto;
import com.zds.scf.biz.common.util.UtilPub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by yy at 2017/2/25 12:15
 */
@Component
public class FileUploadComponent {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadComponent.class);
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 文件上传
     *
     * @param inputStream      输入流
     * @param fileSize         文件大小
     * @param extensionName    扩展名 :如 png,pdf
     * @param originalFilename 原始文件名
     * @return 返回dto对象
     */
    public FileUploadDto upload(InputStream inputStream, Long fileSize, String extensionName, String originalFilename) {
        String fileUrl = upload(inputStream, fileSize, extensionName);
        FileUploadDto fileUploadDto = new FileUploadDto();
        fileUploadDto.setFileName(originalFilename);
        fileUploadDto.setFilePath(fileUrl + "?attname=" + originalFilename);
        fileUploadDto.setFileSize(UtilPub.formatFileSize(fileSize));
        return fileUploadDto;
    }

    /**
     * 文件上传
     *
     * @param inputStream   输入流
     * @param fileSize      文件大小
     * @param extensionName 扩展名 :如 png,pdf
     * @return fastDFS 文件资源路径
     */
    public String upload(InputStream inputStream, Long fileSize, String extensionName) {
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, fileSize, extensionName, null);
        return getResAccessUrl(storePath);
    }

    /**
     * 删除文件
     * @param fileUrl 文件资源路径
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            throw Exceptions.newRuntimeException("文件路径为空");
        }
        StorePath storePath = StorePath.praseFromUrl(fileUrl);
        fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
    }

    private String getResAccessUrl(StorePath storePath) {
        String outPort = EnvironmentHolder.get().getProperty("zds.common.fdfsOutPort");
        String trackerDomain = EnvironmentHolder.get().getProperty("zds.common.fdfsTrackerServerDomain");
        String fileUrl = StringUtils.isEmpty(outPort) ? trackerDomain + "/" + storePath.getFullPath() : trackerDomain + ":" + outPort + "/" + storePath.getFullPath();
        logger.info("文件路径:{}", fileUrl);
        return fileUrl;
    }
}
