package com.funshion.dobo.api.rest;

import com.funshion.dobo.common.GlobalAdminConstant.OrderDirection;

/**
 * 带分页功能的页面
 * 
 * @author jiangxu, lirui
 * 
 */
public abstract class AbstractPagerForm {
	/** 当前第几页 */
	private int pageSize = 20;
	/** 每页多少行 */
	private int pageCurrent = 1;
	/** 一共多少条数据 */
	private int totalCount = 0;
	/** 最多多少个可以快捷点击的页数 */
	private int pageNumShown = 5;
	/** 总共多少页 */
	private int totalPageCount = 0;
	/** 排序的字段 */
	private String orderField = "";
	
	/** 在分页查询时，用于limit的参数: limit #{pageStart}, #{numPerPage} */
	private int pageStart = 0;
	/** 在自动分页时，用于查询总共多少条记录 */
	private String sqlHeaderForCount = "select count(0) ";
	/** 在自动分页时，用于查询具体某页内容 */
	private String sqlHeaderForSelect = "select * ";
	/** 是否禁止自动分页(在导出CSV时需要禁止自动分页） */
	private boolean disableAutoPage = false;
	
	/**
	 * 排序的方向
	 * 
	 * @see com.funshion.dobo.common.GlobalAdminConstant.OrderDirection
	 */
	private String orderDirection = OrderDirection.DESC;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getPageNumShown() {
		return pageNumShown;
	}

	public void setPageNumShown(int pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	public String getSqlHeaderForCount() {
		return sqlHeaderForCount;
	}

	public void setSqlHeaderForCount(String sqlHeaderForCount) {
		this.sqlHeaderForCount = sqlHeaderForCount;
	}

	public String getSqlHeaderForSelect() {
		return sqlHeaderForSelect;
	}

	public void setSqlHeaderForSelect(String sqlHeaderForSelect) {
		this.sqlHeaderForSelect = sqlHeaderForSelect;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public boolean isDisableAutoPage() {
		return disableAutoPage;
	}

	public void setDisableAutoPage(boolean disableAutoPage) {
		this.disableAutoPage = disableAutoPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
}
