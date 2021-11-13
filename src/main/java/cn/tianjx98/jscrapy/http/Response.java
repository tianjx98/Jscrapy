package cn.tianjx98.jscrapy.http;


/**
 * @author tianjx98
 * @date 2021/11/2 14:52
 */
public interface Response {

    int statusCode();

    String getBody();

    Request getRequest();

}
