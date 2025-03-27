package library.model;

public class Librarian extends Person{
    private String staffNumber;

    public Librarian(int id, String name, String email, String staffNumber) {
        super(id, name, email);
        this.staffNumber = staffNumber;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }


    @Override
    public void displayInfo() {
        System.out.println("Librarian [id=" + getId() + ", name=" + getName() + ", staffNumber=" + staffNumber + "]");
    }
}
