package com.smartbuy.ocb.bo;

import java.util.ArrayList;
import java.util.List;

import com.smartbuy.ocb.dao.OrderCreationDAO_I;
import com.smartbuy.ocb.dto.OrderDTO;
import com.smartbuy.ocb.dto.SKUDto;

public class OrderCreationBatchBO {

	private OrderCreationDAO_I dao = null;
	List<SKUDto> skuList = new ArrayList<SKUDto>();
	OrderDTO dto = new OrderDTO();

	public OrderCreationBatchBO() {

	}

	public void setDao(OrderCreationDAO_I dao) {
		this.dao = dao;
	}

	public List<SKUDto> fetchSkuList(int storeNum) throws Exception {

		skuList = dao.getSkusFromStore(storeNum);

		return skuList;
	}

	public void executeOrderCreation() throws Exception {
		OrderDTO dto = new OrderDTO();
		for (SKUDto sList : skuList) {
			System.out.println("List of Skudetails :" + sList.getSkuNumber() + " " + sList.getShelfQty() + " "
					+ sList.getInStrQty() + " " + sList.getSkuRecThres());

			int qty = sList.getShelfQty() + sList.getInStrQty();
			System.out.println("Total Quantity :" + qty);

			if (qty < sList.getSkuRecThres()) {
				// //order quantity calc
				int skuVel = Integer.parseInt(sList.getSkuVelocity());
				int orderQty = skuVel * sList.getTrkDlvrDays();
				dto.setSkuDto(sList);
				dto.setOrderQty(orderQty);
				
				//get Purchase order number
				int poNum = dao.getPurchaseOrderNum();
//				System.out.println(poNum);
				
				// if PO is not empty populate Sar_Po table with the new PO Number and Sku number
				if (poNum != 0) {
				boolean value =	dao.updateOrderCreation(sList,orderQty,poNum);
					if(!value){
						break;
					}
				}
			}
			
		}

	}

}
