package com.smartbuy.ocb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.smartbuy.ocb.dto.OrderDTO;
import com.smartbuy.ocb.dto.SKUDto;



public class OrderCreationDaoImpl implements OrderCreationDAO_I {

	DBUtil dB = new DBUtil();
	Connection conn = null;
	List<SKUDto> skuList = new ArrayList<SKUDto>();
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	public List<SKUDto> getSkusFromStore(int storeNumber) throws Exception {
	//		Connection conn = null;
			PreparedStatement ps = null;
		try {
			// Connection to database
			conn = dB.getDataSource().getConnection() ;
			
			//conn.setAutoCommit(false);
			
			// Prepare Statement
			ps = conn.prepareStatement(getSkusfromStore);
			ps.setInt(1, storeNumber);
			ResultSet rs = ps.executeQuery();
			int i;
	
			while (rs.next()) {
				i=0;
				SKUDto skus = new SKUDto();
				skus.setSkuNumber(rs.getLong("SKU_NUMBER"));	
				skus.setStoreNumber(rs.getInt("STORE_NUM"));
				skus.setSkuVelocity(rs.getString("SKU_VELOCITY"));
				skus.setTrkDlvrDays(rs.getInt("TRK_DLVR_TIME_DAYS"));
				skus.setShelfQty(rs.getInt("SHELF_QTY"));
				skus.setInStrQty(rs.getInt("IN_STR_QTY"));
				skus.setSkuRecThres(rs.getInt("SKU_RCMD_THRD"));
				skuList.add(i, skus);
				i++;
			//	skuList.add(skus);
			}
		} catch(Exception e){
            e.printStackTrace();
			}finally{
					dB.closeConnection(conn);
					}

		return skuList;
	}


	

	public int getPurchaseOrderNum() throws Exception {
		// TODO Auto-generated method stub
		OrderDTO PONum = new OrderDTO();
		PreparedStatement psSelect = null;
		PreparedStatement psUpdate = null;
		ResultSet rs = null;
		int poNum =0;
		try{
		conn = dB.getDataSource().getConnection();
		conn.setAutoCommit(false);
		
		psSelect = conn.prepareStatement(getPONumber);
		rs = psSelect.executeQuery();
		while(rs.next()){
			PONum.setPONumber(rs.getInt("LAST_PO_NUM"));
			poNum = PONum.getPONumber();
			poNum = poNum + 1;
		}
		if(!PONum.equals(null)){
			psUpdate = conn.prepareStatement(updatePONumber);
			psUpdate.setInt(1,poNum);
			psUpdate.setInt(2, PONum.getPONumber());
			
			psUpdate.executeUpdate();
			System.out.println(poNum);
			conn.commit();
		}
		}catch(Exception e){
            e.printStackTrace();
			}finally{
					dB.closeConnection(conn);
					}
		return poNum;
	}
	
	public boolean updateOrderCreation(SKUDto list,int orderQty,int poNum) throws Exception {
		// TODO Auto-generated method stub
		SKUDto skus = new SKUDto();
		OrderDTO order = new OrderDTO();
		try{
			conn = dB.getDataSource().getConnection();
			PreparedStatement psInsert = null;
			psInsert = conn.prepareStatement(insertValues);
			String poValue = Integer.toString(poNum);
			psInsert.setString(1, poValue);
			psInsert.setLong(2, list.getSkuNumber());
			psInsert.setInt(3, list.getStoreNumber());
			psInsert.setInt(4, orderQty);
			psInsert.setInt(5, 1);
			
			psInsert.executeUpdate();
			psInsert.close();
			
		}catch(Exception e){
            e.printStackTrace();
			}finally{
					dB.closeConnection(conn);
					}
		
		return true;
	}


}




