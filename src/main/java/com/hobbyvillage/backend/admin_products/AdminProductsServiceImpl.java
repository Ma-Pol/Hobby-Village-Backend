package com.hobbyvillage.backend.admin_products;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminProductsServiceImpl implements AdminProductsService {

	private AdminProductsMapper mapper;

	public AdminProductsServiceImpl(AdminProductsMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public int getProductCount(String filter) {

		if (filter.equals("none")) {
			filter = "prodIsRental IN (0, 1)";
		} else if (filter.equals("rented")) {
			filter = "prodIsRental = 1";
		} else {
			filter = "prodIsRental = 0";
		}

		return mapper.getProductCount(filter);
	}

	@Override
	public int getSearchProductCount(String filter, String condition, String keyword) {

		if (filter.equals("none")) {
			filter = "prodIsRental IN (0, 1)";
		} else if (filter.equals("rented")) {
			filter = "prodIsRental = 1";
		} else {
			filter = "prodIsRental = 0";
		}

		return mapper.getSearchProductCount(filter, condition, keyword);
	}

	@Override
	public List<AdminProductsDTO> getProductList(String filter, String sort, int pageNum) {

		if (filter.equals("none")) {
			filter = "prodIsRental IN (0, 1)";
		} else if (filter.equals("rented")) {
			filter = "prodIsRental = 1";
		} else {
			filter = "prodIsRental = 0";
		}

		return mapper.getProductList(filter, sort, pageNum);
	}

	@Override
	public List<AdminProductsDTO> getSearchProductList(String filter, String condition, String keyword, String sort,
			int pageNum) {

		if (filter.equals("none")) {
			filter = "prodIsRental IN (0, 1)";
		} else if (filter.equals("rented")) {
			filter = "prodIsRental = 1";
		} else {
			filter = "prodIsRental = 0";
		}

		return mapper.getSearchProductList(filter, condition, keyword, sort, pageNum);
	}

}
