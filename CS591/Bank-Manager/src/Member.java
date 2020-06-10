import java.util.Random;

public abstract class Member{
  public int idNum;
  public String firstName;
  public String lastName;
  public String password;
  public String username;
  
  
  
  public Member() {
	  
  }

  public Member(int idNum, String firstName, String lastName, String password, String username) {
	this.idNum = idNum;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.username = username;
  }
  
  
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getIdNum() {
    return idNum;
  }

  public void setIdNum(int idNum) {
    this.idNum = idNum;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
