package cn.tianjx98.jscrapy.duplicatefilter;

import java.io.Serializable;

import cn.tianjx98.jscrapy.http.Request;

public interface DuplicateFilter extends Serializable {
    /**
     * 检验请求之前是否访问过 如果访问过，返回 true 否则返回false，并将当前请求的url记录下来
     *
     * @param request 请求类
     * @return 请求重复返回true，否则返回false
     */
    boolean isDuplicate(Request request);
}
