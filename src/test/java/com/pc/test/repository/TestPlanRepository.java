package com.pc.test.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.amazonaws.services.s3.model.Bucket;
import com.pc.controller.SignupController;
import com.pc.model.frontend.ProAccountPayload;
import com.pc.service.S3Service;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPlanRepository {

	@Autowired
	S3Service s3Service;
	
	MockMvc  mockmvc;
	
	@Before
	public void setUp() {
		mockmvc = MockMvcBuilders.standaloneSetup(SignupController.class).build();
	}
	
	
/*	@Autowired 
	PlanRepository planRepository;
	
	@Autowired 
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordRestTokenRepository tokenRepository;
//	
//	@Autowired
//	SimpleMailService simpleMailService;
	
	@Value("${token.expiration.length.minute}")
	private int expirationTime=120;
	
	@Before
    public void init() {
        Assert.assertFalse(expirationTime == 0);
    }
	
	@Test
	@Transactional
	public void testBasicPlan()
	{
		Plan plan = createBasicPlan(PlansEnum.BASIC);
		planRepository.save(plan);
		Plan p = planRepository.getOne(PlansEnum.BASIC.getId());
		Assert.assertEquals(p.getPlanname(), PlansEnum.BASIC.getName());
		
	}
	
	public Plan createBasicPlan(PlansEnum planEnum)
	{
		Plan plan = new Plan();
		plan.setId(planEnum.getId());
		plan.setPlanname(planEnum.getName());
		return plan;
	}
	
	@Test
	@Transactional
	public void createUser()
	{
		Role basicRole = new Role(RolesEnum.BASIC);
		User basicUser = UserUtils.createBasicUser();
		Plan plan = createBasicPlan(PlansEnum.ADVANCED);
		planRepository.save(plan);
		
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setUser(basicUser);
		userRole.setRole(basicRole);
		userRoles.add(userRole);
		
		basicUser.getUserRoles().addAll(userRoles);
		
		for(UserRole role: userRoles)
			roleRepository.save(role.getRole());
		
		userRepository.save(basicUser);
		System.out.println("User = "+userRepository.getOne( basicUser.getId()).getUsername());
	}
	
	@Test
	@Transactional
	public void testUserService()
	{
		User user = userService.createUser();
		System.out.println("User -----> "+user.getUserRoles().size());
	}
	
	@Test
	@Transactional
	public void testGetUserByEmail()
	{
		User user = userService.createUser();
		User usersFromDB = userRepository.findByEmail(user.getEmail());
		System.out.println("user from dB = "+usersFromDB.getEmail());
		Iterator userIterator = usersFromDB.iterator();
		while(userIterator.hasNext())
		{
			User u = (User) userIterator.next();
			System.out.println("User email ="+u.getEmail());
		}
	}

	@Test
	@Transactional
	public void testTokenService()
	{
		User user = userService.createUser();
		
		LocalDateTime currentTime = LocalDateTime.now();
		String token = UUID.randomUUID().toString();
		System.out.println("token generated = "+token);
		
		String token1 = UUID.randomUUID().toString();
		System.out.println("token generated = "+token1);
		
		String token2 = UUID.randomUUID().toString();
		System.out.println("token generated = "+token2);
		
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, currentTime, expirationTime);
		PasswordResetToken passwordResetToken1 = new PasswordResetToken(token1, user, currentTime, expirationTime);
		PasswordResetToken passwordResetToken2 = new PasswordResetToken(token2, user, currentTime, expirationTime);
		
		tokenRepository.save(passwordResetToken);
		tokenRepository.save(passwordResetToken1);
		tokenRepository.save(passwordResetToken2);

		Set<PasswordResetToken> tokensFromDb = tokenRepository.findAllByUserId(user.getId());
		Iterator<PasswordResetToken> itr = tokensFromDb.iterator();
		while(itr.hasNext())
		{
			System.out.println("tokenFromDb = "+((PasswordResetToken)itr.next()).getToken());
			System.out.println("tokenFromDb = "+((PasswordResetToken)itr.next()).getExpiryDate());
		}
	}
	
	@Test
	@Transactional
	public void testUpdatePassword(){
		User user = userService.createUser();
		System.out.println("current Password = "+user.getPassword());
		
		String newPassword = UUID.randomUUID().toString();
		System.out.println("gnerated password = "+newPassword);
		userRepository.updateUserPassword(user.getId(), newPassword);
		
		Optional<User> user1 = userRepository.findById(user.getId());
		System.out.println("New password="+user1.get().getPassword());
	}*/
	
	/*@Test
	@Transactional
	public void testSendEmail(){
		simpleMailService.sendMail("prashant.6388@gmail.com","testResetURL");
	}*/
	
	@Test
	public void s3ServiceTest() {
		List<Bucket> bucketList = s3Service.getS3BucketList();
		for(Bucket bucket : bucketList) {
			System.out.println("Bucket = "+bucket.getName());
		}
	}
	//not working
	@Ignore
	@Test
	public void s3ServiceTestBucketCheck() {
		s3Service.checkIfBucketExists("developernic");
	}
	
	@Test
	public void chekifS3BucketExists() {
		s3Service.ensureBucketExists("developernic");
	}
	
	@Test
	//methods gives error of cyclic view path but in our scenario its correct
	//for testing purpose we can return different view name in SignupController
	public void test_signUp_get() throws Exception {
		mockmvc.perform(get("/signup?planId=1"))
			.andExpect(status().isOk())
	        .andExpect(view().name(("signupPost")))
	        .andDo(print());
	}
	
	@Test
	//in this test method signupcontroller is invoked correctly but in further processing userservice is null
	//so method fails
	public void test_signup_post() throws Exception {
		ProAccountPayload payLoad = new ProAccountPayload();
		payLoad.setUsername("test");
		payLoad.setEmail("test@test.com");
		payLoad.setPassword("test");
		payLoad.setConfirmPassword("test");
		payLoad.setFirstName("te");
		payLoad.setLastName("st");
		payLoad.setCountry("IN");
		payLoad.setDescription("This is simple object for testing");
		payLoad.setPhoneNumber("1111111111");
		
		System.out.println("PayLoad: "+payLoad);
		mockmvc.perform(post("/signup")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("planId", "1")
						.flashAttr("payload", payLoad))
				.andExpect(status().isOk());
		
		//if method is not working and gettins some request errors,
		//below code can be used to check for more detailed error
		/*MvcResult result = mockmvc.perform(post("/signup")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("planId", "1")
				.flashAttr("payload", payLoad))
		.andReturn();
		
		result.getResolvedException().printStackTrace();*/
	}
}
