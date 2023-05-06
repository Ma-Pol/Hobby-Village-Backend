package com.hobbyvillage.backend.admin_orders;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/orders")
public class AdminOrdersController {

	private AdminOrdersServiceImpl adminOrdersServiceImpl;

	public AdminOrdersController(AdminOrdersServiceImpl adminOrdersServiceImpl) {
		this.adminOrdersServiceImpl = adminOrdersServiceImpl;
	}

	@GetMapping("/orderDetails/{odrNumber}")
	AdminOrdersDetailDTO getOrderDetail(@PathVariable(value = "odrNumber", required = true) String odrNumber) {
		return adminOrdersServiceImpl.getOrderDetail(odrNumber);
	}

	@GetMapping("/productLists/{odrNumber}")
	List<AdminOrdersProductsDTO> getOrderedProductList(
			@PathVariable(value = "odrNumber", required = true) String odrNumber) {
		return adminOrdersServiceImpl.getOrderedProductList(odrNumber);
	}

	@PatchMapping("/modify")
	public int modifyOrderAddress(@RequestBody AdminOrdersDetailDTO addressData) {
		return adminOrdersServiceImpl.modifyOrderAddress(addressData);
	}

	@PatchMapping("/return")
	public int returnProduct(@RequestBody Map<String, String> codeData) {
		int opCode = Integer.parseInt(codeData.get("opCode"));
		String prodCode = codeData.get("prodCode");

		return adminOrdersServiceImpl.returnProduct(opCode, prodCode);
	}
}
