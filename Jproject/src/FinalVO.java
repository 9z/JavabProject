
public class FinalVO {

	private String userID;
	private String userName;
	private int userState;
	private String date;
	private String foodName;
	private int foodPrice;
	private String mart;
	public FinalVO(String userID, String userName, int userState, String date, String foodName, int foodPrice,
			String mart) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userState = userState;
		this.date = date;
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.mart = mart;
	}
	
	public FinalVO() {
		
	}
	
	public String getUserID() {
		return userID;
	}
	public String getUserName() {
		return userName;
	}
	public int getUserState() {
		return userState;
	}
	public String getDate() {
		return date;
	}
	public String getFoodName() {
		return foodName;
	}
	public int getFoodPrice() {
		return foodPrice;
	}
	public String getMart() {
		return mart;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserState(int userState) {
		this.userState = userState;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public void setFoodPrice(int foodPrice) {
		this.foodPrice = foodPrice;
	}
	public void setMart(String mart) {
		this.mart = mart;
	}
	
}
