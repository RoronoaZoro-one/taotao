package top.hxq.taotao.manage.service.impl.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.hxq.taotao.manage.mapper.test.TestMapper;
import top.hxq.taotao.manage.service.test.TestService;


/**
 * 测试实现类
 * @author wuzm
 * @date 2020年8月16日 上午11:33:36
 */
@Service
public class TestServiceImpl implements TestService {
	
	
	@Autowired
	private TestMapper testMapper;

	/**
	 * 获得当前系统时间
	 */
	@Override
	public String queryCurrentDate() {
		return testMapper.queryCurrentDate();
	}

}
