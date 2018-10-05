package com.chris;

import com.chris.base.modules.sys.entity.SysUserEntity;
import com.chris.base.modules.sys.service.SysUserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	@Autowired
	private SysUserService sysUserService;

	@Test
	public void queryUser() {
		SysUserEntity user = this.sysUserService.queryByUserName("admin");
		System.out.println(ToStringBuilder.reflectionToString(user));
	}

}
