package top.hxq.taotao.manage.service.base;

import java.io.Serializable;
import java.util.List;

/**
 * 通用公共service
 * @author wuzm
 * @date 2020年8月22日 上午8:30:28
 */
public interface BaseService<T> {
	
	/**
	 * 根据id查询
	 */
	public T queryById(Serializable id);
	
	/**
	 * 根据条件查询
	 */
	public List<T> queryListByWhere(T t);
	
	
	/**
	 * 根据条件查询总数
	 */
	public long queryCountByWhere(T t);
	
	/**
	 * 分页查询
	 */
	public List<T> queryListByPage(Integer page,Integer rows);
	
	
	/**
	 * 查询全部
	 */
	public List<T> queryAll();
	
	
	/**
	 * 选择性新增
	 */
	public void saveSelective(T t);
	
	/**
	 * 选择性更新
	 */
	public void updateSelective(T t);

	/**
	 * 根据主键删除
	 */
	public void deleteById(Serializable id);
	
	
	/**
	 * 批量删除
	 */
	public void deleteByIds(Serializable[] ids);
}
