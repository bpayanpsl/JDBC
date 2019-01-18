package fr.dta.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Traitement {

	String url = "jdbc:postgresql://localhost:5432/jdbc";

	Connection conn = null;
	Statement stmt = null;

	public Traitement() {
		try {
			conn = DriverManager.getConnection(url, "benny", "benny1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addBook(Book b) { // AJOUT DE BOOK DANS LA BASE
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(
					"INSERT INTO book (title, author) VALUES ('" + b.getTitre() + "', '" + b.getAuteur() + "')",
					Statement.RETURN_GENERATED_KEYS);
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				generatedKeys.next();
				b.setId(generatedKeys.getInt("id"));
				generatedKeys.close();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void addClient(Client c) { // AJOUT DE CLIENT DANS LA BASE

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(
					"INSERT INTO client (lastname, firstname, gender, favoritebook) " + "VALUES ('" + c.getLastname()
							+ "', '" + c.getFirstname() + "', '" + c.getGender() + "', '" + c.getFavoriteBook() + "');",
					Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			c.setId(generatedKeys.getInt("id"));
			generatedKeys.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// Quels livres ont été achetés par un certain client
	public List<Book> livreAchete(Client c) {

		List<Book> myBooks = new ArrayList<Book>();

		try {
			stmt = conn.createStatement();

			String sql = "SELECT title, author FROM book INNER JOIN achete ON book.id = achete.id_book WHERE achete.id_client = '"
					+ c.getId() + "'";

			ResultSet result = stmt.executeQuery(sql);

			while (result.next()) {
				myBooks.add(new Book(result.getString("title"), result.getString("author")));
			}
			result.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return myBooks;
	}

	public List<Client> ClientLivre(Book b) {

		List<Client> myClients = new ArrayList<Client>();

		try {
			stmt = conn.createStatement();

			String sql = "SELECT firstname, lastname, gender, favoritebook " + "FROM client "
					+ "INNER JOIN achete ON client.id = achete.id_client " + "WHERE id_book = '" + b.getId() + "'";

			ResultSet result = stmt.executeQuery(sql);

			while (result.next()) {
				myClients.add(new Client(result.getString("lastname"), result.getString("firstname"),
						result.getString("gender"), result.getInt("favoritebook")));
			}
			result.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return myClients;
	}
}
