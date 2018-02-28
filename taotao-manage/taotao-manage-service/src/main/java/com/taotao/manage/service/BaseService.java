package com.taotao.manage.service;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;
import com.taotao.manage.pojo.Item;

@SuppressWarnings("all")
public abstract class BaseService<T extends BasePojo> {

	//spring4的泛型注入
	@Autowired
	public Mapper<T> mapper;
	
	/*public abstract Mapper<T> get.mapper();*/
	
	private Class<T> clazz;
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public T queryById(Long id){
		return this.mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<T> queryAll(){
		return this.mapper.selectAll();
	}
	
	/**
	 * 根据条件查询一条数据
	 * @param record
	 * @return
	 */
	public T queryOne(T record){
		return this.mapper.selectOne(record);
	}
	
	/**
	 * 根据条件查询多条数据
	 * @param record
	 * @return
	 */
	public List<T> queryListByRecord(T record){
		return this.mapper.select(record);
	}
	
	/**
	 * 根据条件分页查询
	 * @param record
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(T record,Integer page, Integer rows){
		PageHelper.startPage(page, rows);
		List<T> list = this.mapper.select(record);
		return new PageInfo<>(list);
	}
	
	/**
	 * 根据条件分页查询并排序
	 * @param page
	 * @param rows
	 * @param orderByClause  排序的语句
	 * @return
	 */
	public PageInfo<T> queryPageListAndSort(Integer page, Integer rows,
			String orderByClause) {
		
		Example example = new Example(clazz);
		example.setOrderByClause(orderByClause);
		PageHelper.startPage(page, rows);
		List<T> list = this.mapper.selectByExample(example);
		return new PageInfo<>(list);
	}
	
	/**
	 * 插入数据
	 * @param record
	 * @return
	 */
	public Integer save(T record){
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return this.mapper.insert(record);
	}
	
	/**
     * 插入数据,只操作record中的非空属性
     * @param record
     * @return
     */
	public Integer saveSelective(T record){
		record.setCreated(new Date());  //泛型限定可以用set方法，<T extends BasePojo>擦除后，T变成了BasePojo，所以有set方法。
		record.setUpdated(record.getCreated());
		return this.mapper.insertSelective(record);
	
	}
	
	 /**
     * 更新数据
     * @param record
     * @return
     */
	public Integer update(T record){
		record.setUpdated(new Date());
		return this.mapper.updateByPrimaryKey(record);
	}
	
	/**
     * 更新数据,只操作record中的非空属性
     * @param record
     * @return
     */
    public Integer updateSelective(T record){
        record.setUpdated(new Date());
        // 保证created字段不会被修改
        record.setCreated(null);
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据主键进行删除
     * @param id
     * @return
     */
    public Integer deleteById(Long id){
    	return this.mapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 批量删除
     * @param property
     * @param ids
     * @return
     */
    public Integer deleteByIds(String property,List<Object>ids){
    	Example example=new Example(clazz);
    	example.createCriteria().andIn(property, ids);
    	return this.mapper.deleteByExample(example);
    }
    
    /**
     * 根据条件删除
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record){
    	return this.mapper.delete(record);
    }
    
    /**
     * ParameterizedType 表示参数化类型
     * Type getGenericSuperclass() 
                                返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。 
       Type[] getGenericInterfaces() 
          	返回表示某些接口的 Type，这些接口由此对象所表示的类或接口直接实现。
	   Type[] getActualTypeArguments()
	   		返回表示此类型实际类型参数的 Type 对象的数组。 
			注意，在某些情况下，返回的数组为空。如果此类型表示嵌套在参数化类型中的非参数化类型，则会发生这种情况。 
 
     */
	{
		//获取此类的泛型类
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz =(Class<T>) type.getActualTypeArguments()[0];
	}
	/**
	 * 
	 *  java.lang.Comparable<com.taotao.manage.pojo.Item>
		typecom.taotao.manage.service.BaseService<com.taotao.manage.pojo.ItemCat>
		===========================
		class com.taotao.manage.pojo.ItemCat
		com.taotao.manage.pojo.ItemCat
		ItemCat
		===========================
	 */
	
}
