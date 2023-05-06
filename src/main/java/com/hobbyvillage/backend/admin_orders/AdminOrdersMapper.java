package com.hobbyvillage.backend.admin_orders;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminOrdersMapper {
	@Select("SELECT o.*, u.userCode FROM orders o INNER JOIN users u ON o.odrEmail = u.email WHERE odrNumber = #{odrNumber}")
	AdminOrdersDetailDTO getOrderDetail(@Param("odrNumber") String odrNumber);

	@Select("SELECT o.*, p.prodCategory, p.prodName, p.prodPrice, p.prodShipping "
			+ "FROM orderProducts o INNER JOIN products p ON o.prodCode = p.prodCode "
			+ "WHERE odrNumber = #{odrNumber}")
	List<AdminOrdersProductsDTO> getOrderedProductList(@Param("odrNumber") String odrNumber);

	@Update("UPDATE orders SET odrZipCode = #{odrZipCode}, odrAddress1 = #{odrAddress1}, "
			+ "odrAddress2 = #{odrAddress2} WHERE odrNumber = #{odrNumber};")
	int modifyOrderAddress(AdminOrdersDetailDTO addressData);

	@Select("SELECT COUNT(*) FROM orderProducts WHERE opCode = #{opCode} AND odrState = '반납 중';")
	int returningCheck(@Param("opCode") int opCode);

	@Update("UPDATE orderProducts SET odrState = '반납 완료' WHERE opCode = #{opCode};")
	void modifyOdrState(@Param("opCode") int opCode);

	@Update("UPDATE products SET prodIsRental = 0 WHERE prodCode = #{prodCode};")
	int modifyRentalState(@Param("prodCode") String prodCode);
}
