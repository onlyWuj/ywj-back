package com.zds.scf.biz.common.report;

import com.zds.scf.biz.common.dto.base.BaseDto;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 出参：生成报表
 * @author wuxiang@zdsoft.cn
 */
public class ReportOutDto extends BaseDto {

    @NotNull
    private String fileName;//文件名称

    @NotNull
    private ByteArrayOutputStream inputStream;//数据流

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ReportOutDto(String fileName, ByteArrayOutputStream inputStream) {
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

    /**
     * 使用流
     */
    public void response(DoUot doUot) {
        doUot.out(this.inputStream);
        if (null != inputStream) {
            try {
                inputStream.close();
                inputStream.flush();
                inputStream = null;
            } catch (IOException ignored) {
            }
        }
    }

    public interface DoUot {

        void out(ByteArrayOutputStream inputStream);
    }
}
