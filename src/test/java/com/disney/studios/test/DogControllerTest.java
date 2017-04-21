package com.disney.studios.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.disney.studios.Application;
import com.disney.studios.exceptions.AlreadyVotedException;
import com.disney.studios.model.Breed;
import com.disney.studios.model.Dog;
import com.disney.studios.model.User;
import com.disney.studios.model.Vote;
import com.disney.studios.model.VoteId;
import com.disney.studios.model.VoteType;
import com.disney.studios.service.DogService;
import com.disney.studios.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
@SpringBootTest
public class DogControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private UserService userService;

	@Mock
	private DogService dogService;
	
	@Autowired
	private WebApplicationContext wac;
	
	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	protected HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
	protected void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}
	
	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testUserCreation() throws Exception{
		User user = new User();
		user.setfName("test");
		user.setlName("test");
		//String userJson = "{'fName':'test', 'lName':'test'}";
		User savedUser = new User();
		savedUser.setfName("test");
		savedUser.setlName("test");
		savedUser.setUserId(1L);
		Mockito.when(userService.createUser(Mockito.isA(User.class))).thenReturn(savedUser);
		ResultActions resultActions = this.mockMvc.perform(post("/users").contentType(contentType).content(json(user)));
		resultActions.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testFetchAllDogBreeds() throws Exception{
		Map<Breed, List<String>> breedMap = new HashMap<>();
		breedMap.put(Breed.YORKIE, Arrays.asList("http://i.imgur.com/oSieVUO.png","http://i.imgur.com/qtXIL.png","http://i.imgur.com/qWLKy8a.jpg"));
		breedMap.put(Breed.LABRADOR, Arrays.asList("http://i.imgur.com/oAieVUO.png","http://i.imgur.com/otXIL.png","http://i.imgur.com/oWLKy8a.jpg"));
			
		Mockito.when(dogService.findAllBreedsWithPictures()).thenReturn(breedMap);
		//Map<Breed, List<String>> result = dogController.getAllDogPicturesByBreed();
		//Assert.assertEquals(breedMap, result);
		ResultActions resultActions = this.mockMvc.perform(get("/dogs"));
		resultActions.andExpect(status().is(HttpStatus.OK.value()));

	}
	
	@Test
	public void testGetPicturesByBreed() throws Exception{
		List<String>pics = new ArrayList<>();
		pics.add("http://i.imgur.com/oSieVUO.png");
		pics.add("http://i.imgur.com/qtXIL.png");
		pics.add("http://i.imgur.com/qWLKy8a.jpg");
		Mockito.when(dogService.findDogsByBreed(Breed.YORKIE)).thenReturn(pics);
		ResultActions resultActions = this.mockMvc.perform(get("/dogs/YORKIE"));
		resultActions.andExpect(status().is(HttpStatus.OK.value()));
	}

	@Test
	public void testVoteForDog() throws Exception{
		Vote vote = new Vote();
		vote.setVoteId(new VoteId("http://i.imgur.com/oSieVUO.png", 1L));
		vote.setVoteType(VoteType.UP);
		Dog dog = new Dog();
		dog.setBreed(Breed.YORKIE);
		dog.setUpCount(1L);
		dog.setUrl("http://i.imgur.com/oSieVUO.png");
		Mockito.when(dogService.vote(vote)).thenReturn(dog);
		//Dog result = dogController.upOrDownVote(vote);
		//Assert.assertEquals(dog, result);
		ResultActions resultActions = this.mockMvc.perform(put("/dogs").contentType(contentType).content(json(vote)));
		resultActions.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void testVoteForDogAgain() throws Exception{
		Vote vote = new Vote();
		vote.setVoteId(new VoteId("http://i.imgur.com/oSieVUO.png", 1L));
		vote.setVoteType(VoteType.DOWN);
		Mockito.when(dogService.vote(vote)).thenThrow(new AlreadyVotedException("You have already voted"));
		ResultActions resultActions = this.mockMvc.perform(put("/dogs").contentType(contentType).content(json(vote)));
		resultActions.andExpect(status().is(HttpStatus.NOT_MODIFIED.value()));
	}
	
}
