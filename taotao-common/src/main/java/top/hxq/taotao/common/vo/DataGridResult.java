package top.hxq.taotao.common.vo;

import java.io.Serializable;
import java.util.List;

public class DataGridResult implements Serializable {
	
	private long total;
	
	//？类型占位符：并且在使用之后，如果对该属性赋值了则里面的内容只读
	private List<?> rows;

	public DataGridResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
