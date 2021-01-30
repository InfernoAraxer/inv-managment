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
			double unitPrice = rs.getDouble("unit_price");
			System.out.print(unitPrice);
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