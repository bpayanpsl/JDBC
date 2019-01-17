package fr.dta.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Traitement t1 = new Traitement();
		Client c1 = new Client("Bernard", "Gerard", "M", 1);
		Book b1 = new Book("AAAAAAAAAA", "BBBBBBBBB");

		String url = "jdbc:postgresql://localhost:5432/jdbc";

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(url, "benny", "benny1234");
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS achete;");
			stmt.executeUpdate("DROP TABLE IF EXISTS client;");
			stmt.executeUpdate("DROP TABLE IF EXISTS book;");
			stmt.executeUpdate(
					"CREATE TABLE client (id SERIAL PRIMARY KEY NOT NULL, lastname VARCHAR(100), firstname VARCHAR(100), gender VARCHAR(1));");
			stmt.executeUpdate(
					"CREATE TABLE book (id SERIAL PRIMARY KEY NOT NULL, title VARCHAR(100), author VARCHAR(100));");
			stmt.executeUpdate("ALTER TABLE client ADD favoriteBook INT REFERENCES book(id);");
			stmt.executeUpdate(
					"CREATE TABLE achete (id SERIAL PRIMARY KEY NOT NULL, id_book INT REFERENCES book(id), id_client INT REFERENCES client(id));");
			stmt.executeUpdate(
					"INSERT INTO book (title, author) VALUES ('bjrbjr','Arnorld Ornald'), ('arv arv','Gerard Garerd'), ('fjieofjse', 'fjidsfosd'), ('fedfseoi', 'djifos')");
			stmt.executeUpdate(
					"INSERT INTO client (lastname, firstname, gender, favoritebook) VALUES ('PAYAN', 'Benjamin', 'M', 1), ('DUPONT', 'Dupond', 'M', 2)");
			stmt.executeUpdate("INSERT INTO achete (id_book, id_client) VALUES (1,1), (2,2), (3,1), (4,2)");
			stmt.executeQuery(
					"SELECT lastname, title, author FROM client INNER JOIN achete ON client.id = achete.id_client INNER JOIN book ON achete.id_book = book.id;");
			t1.addClient(c1);
			t1.addBook(b1);
			stmt.executeUpdate("INSERT INTO achete (id_book, id_client) VALUES (1,3), (2,3);");
			stmt.executeUpdate("INSERT INTO achete (id_book, id_client) VALUES (5,1), (5,2);");
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

		for (Book b : t1.livreAchete(c1)) {
			System.out.println(b.toString());
		}

		for (Client c : t1.ClientLivre(b1)) {
			System.out.println(c.toString());
		}
	}
}
