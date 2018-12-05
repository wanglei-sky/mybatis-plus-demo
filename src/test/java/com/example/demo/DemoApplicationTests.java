package com.example.demo;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.busd.DemoApplication;
import com.busd.entity.AdaAccount;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoApplication.class)
public class DemoApplicationTests {

	@Test
	public void testInsertAccount() {
		AdaAccount adaAccount = new AdaAccount();
		adaAccount.setAccountId("123456");
		adaAccount.setAccountName("測試");
		adaAccount.setAddress("7878979889798798");
		adaAccount.setCreateTime(new Timestamp(System.currentTimeMillis()));
		adaAccount.insert();
		System.out.println(adaAccount.getId());
	}
	
	
	@Test
	public void testUpdateAccount() {
		AdaAccount adaAccount = new AdaAccount();
		adaAccount.setAddress("11111111111");
		adaAccount.setId(5l);
		adaAccount.updateById();
		System.out.println(adaAccount.getId());
	}
	
	@Test
	public void testListAccount() {
		AdaAccount adaAccount = new AdaAccount();
		List<AdaAccount> list = adaAccount.selectList(new QueryWrapper<AdaAccount>().eq("address", "11111111111"));
		list.forEach((item) -> {
			System.out.println(item.getAccountId());
		});
	}
	
	@Test
	public void testListPageAccount() {
		AdaAccount adaAccount = new AdaAccount();
		Long count = adaAccount.selectPage(new Page<AdaAccount>(1, 10), 
				new QueryWrapper<AdaAccount>().eq("address", "7878979889798798")).getTotal();
		System.out.println(count);
	}
	
}
