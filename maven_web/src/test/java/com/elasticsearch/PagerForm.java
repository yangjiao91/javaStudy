package com.elasticsearch ;

public class PagerForm{
	
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
	
	
}