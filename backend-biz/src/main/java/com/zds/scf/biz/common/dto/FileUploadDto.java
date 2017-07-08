package com.zds.scf.biz.common.dto;

/**
 * Created by yy at 2017/2/8 14:12
 */
public class FileUploadDto {

    private String filePath;
    private  String fileName;
    private  String fileSize;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
