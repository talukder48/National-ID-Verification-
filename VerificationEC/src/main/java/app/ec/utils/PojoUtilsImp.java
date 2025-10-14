package app.ec.utils;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;


@Service
public class PojoUtilsImp implements PojoUtils {
	@Override
	public <T> T ObjectToClass(Object fromValue, Class<T> toValueType) {
		ObjectMapper mapper = new ObjectMapper();
		return (T) mapper.convertValue(fromValue, toValueType);
	}
	@Override
	public <T> T JsonToClass(String json, Class<T> classOfT) {
		T object = new Gson().fromJson(json, classOfT);
		return Primitives.wrap(classOfT).cast(object);
	}
	@Override
	public String ClassToJson(Object Object) {
		return new Gson().toJson(Object);	    
	  }	
}
