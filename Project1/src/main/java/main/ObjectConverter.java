package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectConverter
{
	private static ObjectConverter controller;
	private ObjectMapper objectMapper;
	
	private ObjectConverter()
	{
		this.objectMapper = new ObjectMapper();
	}
	
	private static ObjectConverter getInstance()
	{
		if (controller == null)
			controller = new ObjectConverter();
		
		return controller;
	}
	
	private static ObjectMapper objectMapper()
	{
		return getInstance().objectMapper;
	}

	public static <T> T read(String body, Class<T> class1) throws JsonMappingException, JsonProcessingException
	{
		return objectMapper().readValue(body, class1);
	}
	
	public static <T> String write(T object) throws JsonProcessingException
	{
		return objectMapper().writeValueAsString(object);
	}

}
