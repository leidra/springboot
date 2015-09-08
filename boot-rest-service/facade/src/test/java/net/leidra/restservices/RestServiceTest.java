package net.leidra.restservices;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import net.leidra.restservices.dtos.CustomerDto;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestService.class)
@WebAppConfiguration
public class RestServiceTest {
	private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private static List<String> customerNames = Arrays.asList("Customer one,Customer two,Customer three".split(","));

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters)
        		.stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void findAllTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(contentType))
			.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(customerNames.get(0))));
	}

	@Test
	public void findByName() throws Exception {		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/" + customerNames.get(0)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(contentType))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(customerNames.get(0))));
	}
	
	@Test
    public void createCustomer() throws Exception {
        String customerJson = json(new CustomerDto(0l, "Customer zero"));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .contentType(contentType)
                .content(customerJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
	
	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}