package Project;

public class Main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		DBConnection Connection = new DBConnection();
		Connection.getConnection();
	//AdminForm Form = new AdminForm();
	//Form.ShowForm();
	LoginForm Login= new LoginForm();
	Login.Login();
	//UserForm UserForm = new UserForm();
	//UserForm.showForm();
		
	//	PayForm PayForm = new PayForm();
	//	PayForm.PayFormm();
	//Dashboard ds = new Dashboard();
	//	ds.ShowForm();
				
		
		
		

	}

}
