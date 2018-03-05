package com.taotao.manage.handler;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.web.vo.DataGridResult;

/**
 * 自定义响应处理器，把响应结果处理为DataGridResult对象
 * @author 虎哥
 * @param <T> DataGridResult中的List元素类型
 */
@SuppressWarnings("all")
public class DataGridResultResponseHandler<T> implements ResponseHandler<DataGridResult> {
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    // 记录响应DataGridResult中的rows集合的元素类型
    private Class<T> clazz;

    public DataGridResultResponseHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

	@Override
    public DataGridResult handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 解析响应，获取数据
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            // 解析Json数据
            JsonNode jsonNode = mapper.readTree(content);
            // 获取所有行数据
            JsonNode data = jsonNode.get("rows");
            // 准备集合，用来封装所有行数据
            List<T> list = null;
            if (data.isArray() && data.size() > 0) {
                // 将Json数据转为集合数据
                list = mapper.readValue(data.traverse(),
                        mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return new DataGridResult(jsonNode.get("total").longValue(), list);
            
        }
        return null;
    }
}