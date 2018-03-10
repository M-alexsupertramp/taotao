package com.taotao.search.vo;

import java.util.List;

/**
 * 分页结果对象
 * @author Mary
 *
 * @param <T>
 */
public class PageResult<T>{
	private Long total; //总条数
	private List<T> data; //数据集合
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public PageResult(Long total, List<T> data) {
		super();
		this.total = total;
		this.data = data;
	}
	public PageResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PageResult [total=" + total + ", data=" + data + "]";
	}
}
