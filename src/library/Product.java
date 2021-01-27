package library;

public class Product {
	private int id;
	private String name;
	private String description;
	private int available;
	private double unitPrice;

	public Product (int id, String name, String description, int available, double unitPrice) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.available = available;
		this.unitPrice = unitPrice;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getAvailable() {
		return available;
	}
	
	public void setAvailable(int available) {
		this.available = available;
	}
	
	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void purchaseMe() {
		if (available > 0) {
			available--;
		}
	}

}
