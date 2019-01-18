package net.aurore.config;

@ConfigClass(format = PropertyFormat.FIELD_NAME, key = "key")
public class TestConfig extends AbstractConfig{

	public TestConfig(String key) {
		super(key);
	}

	@ConfigProperty
	private String field;
	
	@ConfigProperty
	private String field2;
	
	@ConfigProperty
	private String field3;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}
	
	
	@Override
	public String toString() {
		return "f1=" + field + ", f2=" + field2 + ", f3=" + field3;
	}
	
	
	
}