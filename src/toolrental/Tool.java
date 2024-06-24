package toolrental;

public class Tool {

	private String toolType;
	private String brand;
	private String toolCode;
	/**
	 * @param toolType - the type of tool, used to cross reference to a type class.
	 * @param brand -  brand of the tool
	 * @param toolCode - unique code for looking up the tool.
	 */
	public Tool(String toolType, String brand,String toolCode) {
		this.toolType = toolType;
		this.brand = brand;
		this.toolCode = toolCode;
	}
	
	public String getType() {
		return this.toolType;
	}
	public String getCode() {
		return this.toolCode;
	}
	public String getBrand() {
		return this.brand;
	}

}
