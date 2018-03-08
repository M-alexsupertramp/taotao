package com.taotao.web.httpclient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleResponseHandler<T> implements ResponseHandler<T> {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    // 记录响应结果的类型
    private Class<T> clazz;

    public SimpleResponseHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        // 判断返回状态是否小于300
        if (response.getStatusLine().getStatusCode() < 300) {
            // 解析响应，获取数据
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            // 将json转为Bean对象
            return mapper.readValue(content, clazz);
        }
        return null;
    }
}
