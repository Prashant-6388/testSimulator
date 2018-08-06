package com.pc.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.DemoApplication;
import com.pc.config.I18NConfig;
import com.pc.i18n.I18NService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	I18NService i18nService;
	
	
	@Test
	public void contextLoads() {
		System.out.println("Test for application");
	}
	
	@Test
	public void testI18NService()
	{
		System.out.println("Testing I18NService");
		String messageId="index.main.callout";
		String expected = "Bootstrap starter template";
		String actual = i18nService.getMessage(messageId);
		Assert.assertEquals("Actual and expected results does not match",expected, actual);
	}

}
