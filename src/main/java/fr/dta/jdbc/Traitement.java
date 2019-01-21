package fr.dta.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Traitement {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	String url = "jdbc:postgresql://localhost:5432/jdbc";

	Connection conn = null;


	public Traitement() {
		try {
			conn = DriverManager.getConnection(url, "benny", "benny1234");
		} catch (SQLException e) {
			LOG.trace(e.getMessage());
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
			}

		} catch (Exception e) {
			LOG.trace(e.getMessage());
		}
	}

	public void addClient(Client c) { // AJOUT DE CLIENT DANS LA BASE

		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(
					"INSERT INTO client (lastname, firstname, gender, favoritebook) " + "VALUES ('" + c.getLastname()
							+ "', '" + c.getFirstname() + "', '" + c.getGender() + "', '" + c.getFavoriteBook() + "');",
					Statement.RETURN_GENERATED_KEYS);
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				generatedKeys.next();
				c.setId(generatedKeys.getInt("id"));
			}
		} catch (Exception e) {
			LOG.trace(e.getMessage());
		}
	}

	// Quels livres ont été achetés par un certain client
	public List<Book> livreAchete(Client c) {

		List<Book> myBooks = new ArrayList<>();

		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT title, author FROM book INNER JOIN achete ON book.id = achete.id_book WHERE achete.id_client = '"
					+ c.getId() + "'";

			try (ResultSet result = stmt.executeQuery(sql)) {

				while (result.next()) {
					myBooks.add(new Book(result.getString("title"), result.getString("author")));
				}
			}
		} catch (Exception e) {
			LOG.trace(e.getMessage());
		}

		return myBooks;
	}

	public List<Client> clientLivre(Book b) {

		List<Client> myClients = new ArrayList<>();

		try (Statement stmt = conn.createStatement()) {

			String sql = "SELECT firstname, lastname, gender, favoritebook " + "FROM client "
					+ "INNER JOIN achete ON client.id = achete.id_client " + "WHERE id_book = '" + b.getId() + "'";

			try (ResultSet result = stmt.executeQuery(sql)) {

				while (result.next()) {
					myClients.add(new Client(result.getString("lastname"), result.getString("firstname"),
							result.getString("gender"), result.getInt("favoritebook")));
				}
			}
		} catch (Exception e) {
			LOG.trace(e.getMessage());
		}
		return myClients;
	}
}
