package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBAccess {
	//数据库驱动
	private String driver = "com.mysql.jdbc.Driver";
	//连接URL
	private String url = "jdbc:mysql://infinitex.cn:3306/timeline";
	//用户名
	private String user = "superxyy";
	//密码
	private String pwd = "1928370";
	//数据库连接对象
	private Connection conn = null;
	//数据库声明对象
	private Statement stmt = null;
	//数据结果集对象
	private ResultSet rs = null;
	
	
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public boolean createConn() {
		boolean b = false;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public boolean update(String sql) {
		boolean b = false;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public void query(String sql) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean next() {
		boolean b = false;
		try {
			if(rs.next()) {
				b = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public String getValue(String field) {
		String value = null;
		try {
			if(rs != null) {
				value = rs.getString(field);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public void closeRs() {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeStmt() {
		try {
			if(stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeConn() {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
