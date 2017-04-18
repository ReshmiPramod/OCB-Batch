package com.smartbuy.ocb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.smartbuy.ocb.bo.OrderCreationBatchBO;
import com.smartbuy.ocb.dto.OrderDTO;
import com.smartbuy.ocb.dto.SKUDto;

public class OCBMain {

	public static void main(String[] args) throws Exception {
		// Here you should take store number as input

		try {
			ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

			OrderCreationBatchBO orderBo = (OrderCreationBatchBO) context.getBean("bo");
			List<SKUDto> skus = orderBo.fetchSkuList(501);
			if (!skus.equals(null)) {
				orderBo.executeOrderCreation();
			}

			context.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Send an email using Java Mail ApI
	}
}
