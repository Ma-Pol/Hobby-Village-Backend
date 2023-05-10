package com.hobbyvillage.backend.user_purchase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyvillage.backend.UploadDir;

@RestController
@RequestMapping("/purchase")
public class UserPurchaseController {

	private UserPurchaseServiceImpl userPurchaseServiceImpl;

	public UserPurchaseController(UserPurchaseServiceImpl userPurchaseServiceImpl) {
		this.userPurchaseServiceImpl = userPurchaseServiceImpl;
	}

	@GetMapping("/productState/{prodCode}")
	public int getProductDetail(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return userPurchaseServiceImpl.getProductState(prodCode);
	}

	@GetMapping("/{email}")
	public UserPurchaseUserDTO getUserInfo(@PathVariable(value = "email", required = true) String email) {
		return userPurchaseServiceImpl.getUserInfo(email);
	}

	@GetMapping("/addresses/{email}")
	public List<UserPurchaseAddressDTO> getAddressList(@PathVariable(value = "email", required = true) String email) {
		return userPurchaseServiceImpl.getAddressList(email);
	}

	@GetMapping("/coupons/{email}")
	public List<UserPurchaseCouponDTO> getCouponList(@PathVariable(value = "email", required = true) String email) {
		return userPurchaseServiceImpl.getCouponList(email);
	}

	@GetMapping("/upload/{prodPicture}")
	public ResponseEntity<byte[]> getProductImgData(
			@PathVariable(value = "prodPicture", required = true) String prodPicture) {
		File file = new File(UploadDir.uploadDir + "\\Uploaded\\ProductsImage", prodPicture);
		ResponseEntity<byte[]> result = null;

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
