package fr.dta.jdbc;

public class Client {

	private String lastname;
	private String firstname;
	private String gender;
	private int favoriteBook;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Client(String ln, String fn, String gen, int fav) {
		lastname = ln;
		firstname = fn;
		gender = gen;
		favoriteBook = fav;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getFavoriteBook() {
		return favoriteBook;
	}

	public void setFavoriteBook(int favoriteBook) {
		this.favoriteBook = favoriteBook;
	}

	@Override
	public String toString() {
		return "Client [lastname=" + lastname + ", firstname=" + firstname + ", gender=" + gender + ", favoriteBook="
				+ favoriteBook + "]";
	}
	
}
