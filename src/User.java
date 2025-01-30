import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class User implements Role {
    protected String name;
    protected String password;
    protected String email;
    protected String status;
    protected String firstName;
    protected String uid;

    public User(String name, String firstName, String email, String password, String uid, String status) {
        this.name = name;
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.uid = uid;
        this.status = status;
    }

    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public abstract void showMenu();

    @JsonIgnore
    @Override
    public String getRoleName() {
        return status;
    }
}