package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.Product;
import library.ProductDAO;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	
	private ProductDAO dao;
	
	public void init( ) {
		final String url = getServletContext().getInitParameter("JDBC-URL");
		final String username = getServletContext().getInitParameter("JDBC-USERNAME");
		final String password = getServletContext().getInitParameter("JDBC-PASSWORD");
		
		dao = new ProductDAO(url, username, password);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getServletPath();
		
		try {
			switch (action) {
			case "/add": //intentionally fall through
			case "/edit": showEditForm(request, response); break;
			case "/insert": insertProduct(request, response); break;
			case "/update": updateProduct(request, response); break;
			case "/search": searchProduct(request, response); break;
			case "/filter": filterProducts(request, response); break;
			default: viewProducts(request, response); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	private void viewProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
				? request.getParameter("action")
				: "null";
		List<Product> products = dao.getProducts();
		ArrayList<String> nameList = new ArrayList<>();
		ArrayList<Integer> numberList = new ArrayList<>();
		ArrayList<Double> priceList = new ArrayList<>();
		ArrayList<Product> newList = new ArrayList<>();
		
		if (action.charAt(0) == ('N')) {
			for (int x = 0; x < products.size(); x++) {
				nameList.add(products.get(x).getName());
			}
			if (action.substring(1).equals("up")) {
				Collections.sort(nameList);
			} else {
				Collections.sort(nameList, Collections.reverseOrder());
			}
			for (String name: nameList) {
				for (Product product: products) {
					if (name.equals(product.getName())) {
						newList.add(product);
						products.remove(products.indexOf(product));
						break;
					}
				}
			}
			products = newList;
		} else if (action.charAt(0) == ('D')) {
			for (int x = 0; x < products.size(); x++) {
				nameList.add(products.get(x).getDescription());
			}
			if (action.substring(1).equals("up")) {
				Collections.sort(nameList);
			} else {
				Collections.sort(nameList, Collections.reverseOrder());
			}
			for (String description: nameList) {
				for (Product product: products) {
					if (description.equals(product.getDescription())) {
						newList.add(product);
						products.remove(products.indexOf(product));
						break;
					}
				}
			}
			products = newList;
		} else if (action.charAt(0) == ('A')) {
			for (int x = 0; x < products.size(); x++) {
				numberList.add(products.get(x).getAvailable());
			}
			if (action.substring(1).equals("up")) {
				Collections.sort(numberList);
			} else {
				Collections.sort(numberList, Collections.reverseOrder());
			}
			for (int available: numberList) {
				for (Product product: products) {
					if (available == product.getAvailable()) {
						newList.add(product);
						products.remove(products.indexOf(product));
						break;
					}
				}
			}
			products = newList;
		} else if (action.charAt(0) == ('I')) {
			for (int x = 0; x < products.size(); x++) {
				numberList.add(products.get(x).getId());
			}
			if (action.substring(1).equals("up")) {
				Collections.sort(numberList);
			} else {
				Collections.sort(numberList, Collections.reverseOrder());
			}
			for (int id : numberList) {
				for (Product product: products) {
					if (id == product.getId()) {
						newList.add(product);
						products.remove(products.indexOf(product));
						break;
					}
				}
			}
			products = newList;
		} else if (action.charAt(0) == ('U')) {
			for (int x = 0; x < products.size(); x++) {
				priceList.add(products.get(x).getUnitPrice());
			}
			if (action.substring(1).equals("up")) {
				Collections.sort(priceList);
			} else {
				Collections.sort(priceList, Collections.reverseOrder());
			}
			for (double price: priceList) {
				for (Product product: products) {
					if (price == product.getUnitPrice()) {
						newList.add(product);
						products.remove(products.indexOf(product));
						break;
					}
				}
			}
			products = newList;
		}
		
		request.setAttribute("products", products);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request, response);
	}
	
	private void searchProduct (HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String keyword = request.getParameter("search_bar") != null
				? request.getParameter("search_bar").toUpperCase()
				: null;
		final String type = request.getParameter("search_attribute") != null
				? request.getParameter("search_attribute")
				: "";
				
		if (keyword != null) {
			List<Product> products = dao.getProducts();
			List<Product> tempList = new ArrayList<>();
			
			//Searches by type and if no type is selected then it defaults to ID
			
			if (type.equals("id")) {
				for (Product product: products) {
					if (String.valueOf(product.getId()).toUpperCase().equals(keyword)) {
						tempList.add(product);
					}
				}
			} else if (type.equals("description")) {
				for (Product product: products) {
					if (String.valueOf(product.getDescription()).toUpperCase().contains(keyword)) {
						tempList.add(product);
					}
				}
			} else if (type.equals("name")) {
				for (Product product: products) {
					if (String.valueOf(product.getName()).toUpperCase().contains(keyword)) {
						tempList.add(product);
					}
				}
			} else if (type.equals("unit_price")) {
				for (Product product: products) {
					if (String.valueOf(product.getUnitPrice()).toUpperCase().contains(keyword)) {
						tempList.add(product);
					}
				}
			} else {
				for (Product product: products) {
					if (String.valueOf(product.getId()).toUpperCase().equals(keyword)) {
						tempList.add(product);
					}
				}
			}
			
			products = tempList;
			tempList = new ArrayList<>();
			
			for (int i = 0; i < products.size(); i++) {
				if (!tempList.contains(products.get(i))) {
					tempList.add(products.get(i));
				}
			}

			products = tempList;
			tempList = new ArrayList<>();
			
			int productSize = products.size();
			int indexHolder = -1;
			if (products.size() > 0) {
				indexHolder = 0;
			}

			for (int j = 0; j < productSize; j++) {
				for (int i = 0; i < products.size(); i++) {
					if (products.get(i).getId() < products.get(indexHolder).getId()) {
						indexHolder = i;
					}
				}
				tempList.add(products.get(indexHolder));
				products.remove(indexHolder);
				indexHolder = 0;
			}
			products = tempList;
			
			request.setAttribute("products", products);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		} else {
			List<Product> products = dao.getProducts();
			request.setAttribute("products", products);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void filterProducts (HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final double keyNumber = Double.valueOf(request.getParameter("filter_bar"));
		final String attribute = request.getParameter("filter_attribute") != null
				? request.getParameter("filter_attribute")
				: "";
		final String type = request.getParameter("filter_type") != null
				? request.getParameter("filter_type")
				: "";
				
		List<Product> products = dao.getProducts();
		List<Product> tempList = new ArrayList<>();
		
		//Searches by type and if no type is selected then it defaults to Available and greater than
		
		if (attribute.equals("price")) {
			if (type.equals("less_than")) {
				for (Product product: products) {
					if (product.getUnitPrice() < keyNumber) {
						tempList.add(product);
					}
				}
			} else {
				for (Product product: products) {
					if (product.getUnitPrice() > keyNumber) {
						tempList.add(product);
					}
				}
			}
		} else {
			if (type.equals("less_than")) {
				for (Product product: products) {
					if (product.getAvailable() < keyNumber) {
						tempList.add(product);
					}
				}
			} else {
				for (Product product: products) {
					if (product.getAvailable() > keyNumber) {
						tempList.add(product);
					}
				}
			}
		}
		
		products = tempList;
		tempList = new ArrayList<>();
		
		for (int i = 0; i < products.size(); i++) {
			if (!tempList.contains(products.get(i))) {
				tempList.add(products.get(i));
			}
		}

		products = tempList;
		tempList = new ArrayList<>();
		
		int productSize = products.size();
		int indexHolder = -1;
		if (products.size() > 0) {
			indexHolder = 0;
		}

		for (int j = 0; j < productSize; j++) {
			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).getId() < products.get(indexHolder).getId()) {
					indexHolder = i;
				}
			}
			tempList.add(products.get(indexHolder));
			products.remove(indexHolder);
			indexHolder = 0;
		}
		
		products = tempList;
		
		request.setAttribute("products", products);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		int available = Integer.parseInt(request.getParameter("available"));
		double unitPrice = Double.valueOf(request.getParameter("price"));
		
		dao.insertProduct(name, description, available, unitPrice);
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
			? request.getParameter("action")
			: request.getParameter("submit").toLowerCase();
		final int id = Integer.parseInt(request.getParameter("id"));
		
		Product product = dao.getProduct(id);
		switch (action) {
			case "purchase": product.purchaseMe(); break;
			case "save":
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				int available = Integer.parseInt(request.getParameter("available"));
				double unitPrice = Double.valueOf(request.getParameter("price"));
				
				product.setName(name);
				product.setDescription(description);
				product.setAvailable(available);
				product.setUnitPrice(unitPrice);
				break;
			case "delete": deleteProduct(id, request, response); return;
		}
		dao.updateProduct(product);
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
			final int id = Integer.parseInt(request.getParameter("id"));
			
			Product product = dao.getProduct(id);
			System.out.println(product.getUnitPrice());
			request.setAttribute("product", product);
		} catch (NumberFormatException e) {
			
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("editForm.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	private void deleteProduct(final int id, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		dao.deleteProduct(dao.getProduct(id));
		
		response.sendRedirect(request.getContextPath() + "/");
	}
}