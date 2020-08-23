package top.hxq.taotao.manage.service.impl.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import top.hxq.taotao.manage.pojo.base.BasePojo;
import top.hxq.taotao.manage.service.base.BaseService;

/**
 * 通用公共業務實現類
 * @author wuzm
 * @date 2020年8月22日 上午8:57:38
 */
public abstract class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {

	@Autowired
	private Mapper<T> mapper;

	private Class<T> clazz;
	
	public BaseServiceImpl() {
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
		clazz = (Class<T>)pt.getActualTypeArguments()[0];
	}

	@Override
	public T queryById(Serializable id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryListByWhere(T t) {
		return mapper.select(t);
	}

	@Override
	public long queryCountByWhere(T t) {
		return mapper.selectCount(t);
	}

	@Override
	public List<T> queryListByPage(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		return mapper.selectAll();
	}

	@Override
	public List<T> queryAll() {
		return mapper.selectAll();
	}

	@Override
	public void saveSelective(T t) {
		if (t.getCreated() == null) {
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		}
		if (t.getUpdated() == null) {
			t.setUpdated(new Date());
		}
		mapper.insertSelective(t);
	}

	@Override
	public void updateSelective(T t) {
		if (t.getUpdated() == null) {
			t.setUpdated(new Date());
		}
		mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Serializable id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		Example example = new Example(clazz);
		Criteria criteria = example.createCriteria();
		criteria.andIn("id", Arrays.asList(ids));
		mapper.deleteByExample(example);
	}

}
