package com.smartbuy.ocb.dao;

import java.sql.Connection;
import java.sql.DriverManager;



public class DBUtil_jdbc {

	public DBUtil_jdbc() {
		// TODO Auto-generated constructor stub
	}
	 public static void main(String[] args) {
		try{
		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
