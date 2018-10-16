package com.pc.test.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.pc.model.User;
import com.pc.model.frontend.BasicAccountPayload;
import com.pc.utils.UserUtils;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
		"token.expiration.length.minute=120",
})
public class UserUtilsTest {

	private MockHttpServletRequest mockHttpServletRequest;
	
	private PodamFactory podamFactory;
	
	@Test
    public void mapWebUserToDomainUser() {

        BasicAccountPayload webUser = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
        webUser.setEmail("me@example.com");

        User user = UserUtils.fromWebUserToDomainUser(webUser);
        Assert.assertNotNull(user);

        Assert.assertEquals(webUser.getUsername(), user.getUsername());
        Assert.assertEquals(webUser.getPassword(), user.getPassword());
        Assert.assertEquals(webUser.getFirstName(), user.getFirstName());
        Assert.assertEquals(webUser.getLastName(), user.getLastName());
        Assert.assertEquals(webUser.getEmail(), user.getEmail());
        Assert.assertEquals(webUser.getPhoneNumber(), user.getPhoneNumber());
        Assert.assertEquals(webUser.getCountry(), user.getCountry());
        Assert.assertEquals(webUser.getDescription(), user.getDescription());

    }
	
	@Test
	public void testUserSignUp() {
		podamFactory = new PodamFactoryImpl();
		
		BasicAccountPayload webUser = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
		webUser.setEmail("test@pc.com");
		
		User user = UserUtils.fromWebUserToDomainUser(webUser);
		
		Assert.assertNotNull(user);
		
		Assert.assertEquals(user.getCountry(), webUser.getCountry());
		Assert.assertEquals(user.getEmail(), webUser.getEmail());
		Assert.assertEquals(user.getFirstName(), webUser.getFirstName());
		/*Assert.assertEquals(user.getCountry(), webUser.getCountry());
		Assert.assertEquals(user.getCountry(), webUser.getCountry());*/
		
	}
	
}
