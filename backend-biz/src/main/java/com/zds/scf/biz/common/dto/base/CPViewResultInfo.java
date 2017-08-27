
package com.zds.scf.biz.common.dto.base;

import com.zds.common.lang.result.ResultInfo;
import com.zds.common.lang.result.Status;
import com.zds.common.util.ToString;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class CPViewResultInfo implements Serializable {

    private static final long serialVersionUID = -4366897250108397918L;

    /**
     * 数据
     */
    private Object data;

    /**
     * 处理结果
     */
    private boolean success;

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;


    public CPViewResultInfo data(Object data) {
        if (data instanceof PageImpl) {
            PageImpl page = (PageImpl) data;
            this.data = new CPPageImpl(page,null);
        } else {
            this.data = data;
        }
        return this;
    }

    /**
     * 得到数据。
     */
    public Object getData() {
        return this.data;
    }

    /**
     * 设置数据。
     */
    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }

    /**
     * 把ResultInfo转换成ViewResultInfo
     */
    public static CPViewResultInfo form(ResultInfo resultInfo) {
        CPViewResultInfo viewResultInfo = new CPViewResultInfo();
        viewResultInfo.setMessage(resultInfo.getDescription());
        viewResultInfo.setSuccess(resultInfo.getStatus() == Status.SUCCESS);
        viewResultInfo.setCode(resultInfo.getCode());
        if(resultInfo instanceof CPSingleValueResponse){
            CPSingleValueResponse response=(CPSingleValueResponse)resultInfo;
            if (response.getData() instanceof PageImpl) {
                PageImpl page = (PageImpl) response.getData();
                viewResultInfo.setData(new CPPageImpl(page,response.getExtraData()));
            } else {
                viewResultInfo.setData(response.getData());
            }
        }
        return viewResultInfo;
    }

    @Deprecated //属性名的字符串目前没有了
    private static String buildMsg(ResultInfo resultInfo){
        StringBuilder sb = new StringBuilder();
        if(resultInfo.getStatus()==Status.FAIL){
            String[] failedMsgs = resultInfo.getDescription().split(",");
            boolean notFirst = false;
            for(int i = 0 ; i<failedMsgs.length;i++){
                if(notFirst)sb.append(",");
                String [] failedMsg =failedMsgs[i].split(":");
                sb.append(failedMsg[failedMsg.length-1]);
                notFirst = true;
            }
        }else{
            sb.append(resultInfo.getDescription());
        }
        return sb.toString();
    }
    /**
     * 用于json序列化
     */
   public static  class CPPageImpl {
        private PageImpl page;
        private Object extra;  //扩展参数

        public CPPageImpl(PageImpl page, Object extra) {
            this.page = page;
            this.extra = extra;
        }

        public List getResult() {
            return page.getContent();
        }

        public long getTotalCount() {
            return page.getTotalElements();
        }

        public int getTotalPageCount() {
            return page.getTotalPages();
        }

        public int getCurrentPageNo() {
            return page.getNumber() + 1;
        }

        public int getPageSize() {
            return page.getSize();
        }

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }
    }
    }
