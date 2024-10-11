//package lt.ca.javau10.Receptai.entities;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotBlank;
//
//
//@Entity
//@Table(name="users")
//public class User {
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private long id;
//	
//	@NotBlank(message= "Vartotojo vardas reikalingas")
//	private String userName;
//	
//	@NotBlank(message="Elektroninis pastas reikalingas")
//	private String email;
//	private boolean emailConfirmed;
//	private String password;
//	private LocalDate accountCreationDate;
//	
//	
//	public User(long id, String userName, String email, boolean emailConfirmed, String password) {
//		super();
//		this.id = id;
//		this.userName = userName;
//		this.email = email;
//		this.emailConfirmed = emailConfirmed;
//		this.password = password;
//		this.accountCreationDate = LocalDate.now();
//	}
//	
//	public User(String userName, String email, boolean emailConfirmed, String password) {
//		super();
//		this.userName = userName;
//		this.email = email;
//		this.emailConfirmed = emailConfirmed;
//		this.password = password;
//		this.accountCreationDate = LocalDate.now();
//	}
//	
//	public User() {}
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public boolean isEmailConfirmed() {
//		return emailConfirmed;
//	}
//
//	public void setEmailConfirmed(boolean emailConfirmed) {
//		this.emailConfirmed = emailConfirmed;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public LocalDate getAccountCreationDate() {
//		return accountCreationDate;
//	}
//
//	public void setAccountCreationDate(LocalDate accountCreationDate) {
//		this.accountCreationDate = accountCreationDate;
//	}
//
//	
//}
