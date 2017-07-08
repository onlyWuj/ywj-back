package com.zds.scf.biz.common.convert;

import com.zds.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yy at 2017/2/17 9:42
 */
public class DateConverter implements Converter<String, Date> {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public Date convert(String text) {
        Date date = null;
        try {
            if (StringUtils.isNotBlank(text)) {
                if (text.contains("-")) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sf.parse(text);
                } else {
                    SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                    date = sf.parse(text);
                }
            }
        } catch (ParseException e) {
            logger.error("Controller Get请求转换日期出错",e);
            e.printStackTrace();
        }
        return date;
    }
}
