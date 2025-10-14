package app.ec.utils;

public interface PojoUtils {
	public <T> T ObjectToClass(Object fromValue, Class<T> toValueType);	
	public <T> T JsonToClass(String json, Class<T> classOfT);	
	public String ClassToJson(Object Object);
}
