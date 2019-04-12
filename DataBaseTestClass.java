package cap_stone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DataBaseTestClass {

	public static void main(String[] args) throws Exception{
		
		Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
		ResultSet result = connection.createStatement().executeQuery("select * from public.yearlyevaluation;");
		while(result.next()) {
			System.out.println(result.getInt("id"));
		}
	}
}
