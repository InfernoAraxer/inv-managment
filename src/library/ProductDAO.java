package library;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDAO {
	private final String url;
	private final String username;
	private final String password;
	
	public ProductDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Product getProduct(int id) throws SQLException {
		final String sql = "SELECT * FROM products WHERE product_id = ?";
		
		Product product = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			String productName = rs.getString("product_name");
			String description = rs.getString("description");
			int available = rs.getInt("available");
			double unitPrice = rs.getInt("unit_price");
			
			product = new Product(id, productName, description, available, unitPrice);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return product;
	}
	
	public List<Product> getProducts() throws SQLException {
		final String sql = "SELECT * FROM products ORDER BY product_id ASC";
		
		List<Product> products= new ArrayList<Product>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			int id = rs.getInt("product_id");
			String productName = rs.getString("product_name");
			String description = rs.getString("description");
			int available = rs.getInt("available");
			double unitPrice = rs.getDouble("unit_price");
			
			products.add(new Product(id, productName, description, available, unitPrice));
		}
		
		rs.close();
		stmt.close();
		conn.close();
		
		return products;
	}
	
	public boolean insertProduct(String productName, String description, int available, double unitPrice) throws SQLException {       
		final String sql = "INSERT INTO products (product_name, description, available, unit_price) " +
			"VALUES (?, ?, ?, ?)";
		
        Connection conn = getConnection();        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, productName);
        pstmt.setString(2, description);
        pstmt.setInt(3, available);
        pstmt.setDouble(4, unitPrice);
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }
	
    public boolean updateProduct(Product product) throws SQLException {
    	final String sql = "UPDATE products SET product_name = ?, description = ?, available = ?, unit_price = ? " +
    		"WHERE product_id = ?";
    			
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
                
        pstmt.setString(1, product.getName());
        pstmt.setString(2, product.getDescription());
        pstmt.setInt(3, product.getAvailable());
        pstmt.setDouble(4, product.getUnitPrice());
        pstmt.setInt(5, product.getId());
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }
	
    public List<Product> searchProduct(String keyWord) throws SQLException {
    	int keyValue = -1;
    	double keyPrice = -1;
    	try {
    		keyValue = Integer.valueOf(keyWord);
    	} catch (Exception e) {
    		keyValue = -1;
    	}
    	try {
    		keyPrice = Double.valueOf(keyWord);
    	} catch (Exception e) {
    		keyPrice = -1;
    	}
    	
    	
    	String sql1 = "SELECT * FROM products WHERE product_id = ?";
    	String sql2 = "SELECT * FROM products WHERE product_name = ?";
    	String sql3 = "SELECT * FROM products WHERE description = ?";
    	String sql4 = "SELECT * FROM products WHERE unit_price = ?";
    	
    	List<Product> products= new ArrayList<Product>();
    	Connection conn = getConnection();
    	PreparedStatement pstmt1 = conn.prepareStatement(sql1);
    	PreparedStatement pstmt2 = conn.prepareStatement(sql2);
    	PreparedStatement pstmt3 = conn.prepareStatement(sql3);
    	PreparedStatement pstmt4 = conn.prepareStatement(sql4);
    	
    	pstmt1.setInt(1, keyValue);
		ResultSet rs = pstmt1.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("product_id");
			String productName = rs.getString("product_name");
			String description = rs.getString("description");
			int available = rs.getInt("available");
			double unitPrice = rs.getDouble("unit_price");
			
			products.add(new Product(id, productName, description, available, unitPrice));
		}
		
		pstmt2.setString(1, keyWord);
		rs = pstmt2.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("product_id");
			String productName = rs.getString("product_name");
			String description = rs.getString("description");
			int available = rs.getInt("available");
			double unitPrice = rs.getDouble("unit_price");
			
			products.add(new Product(id, productName, description, available, unitPrice));
		}
		
		pstmt3.setString(1, keyWord);
		rs = pstmt3.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("product_id");
			String productName = rs.getString("product_name");
			String description = rs.getString("description");
			int available = rs.getInt("available");
			double unitPrice = rs.getDouble("unit_price");
			
			products.add(new Product(id, productName, description, available, unitPrice));
		}
		
		pstmt4.setDouble(1, keyPrice);
		rs = pstmt4.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("product_id");
			String productName = rs.getString("product_name");
			String description = rs.getString("description");
			int available = rs.getInt("available");
			double unitPrice = rs.getDouble("unit_price");
			
			products.add(new Product(id, productName, description, available, unitPrice));
		}
		
		rs.close();
		pstmt1.close();
		pstmt2.close();
		pstmt3.close();
		pstmt4.close();
		conn.close();
		
		return products;
    }
    
    public boolean deleteProduct(Product product) throws SQLException {
    	final String sql = "DELETE FROM products WHERE product_id = ?";
    	
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setInt(1, product.getId());
        int affected = pstmt.executeUpdate();
        
        pstmt.close();
        conn.close();
        
        return affected == 1;
    }

	private Connection getConnection() throws SQLException {
		final String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, username, password);
	}
}