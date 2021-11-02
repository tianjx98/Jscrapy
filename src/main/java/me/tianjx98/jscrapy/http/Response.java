package me.tianjx98.jscrapy.http;

import javax.xml.bind.Element;

/**
 * @author tianjx98
 * @date 2021/11/2 14:52
 */
public interface Response {

    int statusCode();

    String getBody();

    Request getRequest();

}
