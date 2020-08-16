package top.hxq.taotao.manage.mapper.test;

import org.apache.ibatis.annotations.Mapper;


/**
 * 测试mapper
 * @author wuzm
 * @date 2020年8月16日 上午11:31:26
 */
@Mapper
public interface TestMapper {
	
	/**
	 * 查询当前时间
	 * @return
	 */
	public String queryCurrentDate();

}
