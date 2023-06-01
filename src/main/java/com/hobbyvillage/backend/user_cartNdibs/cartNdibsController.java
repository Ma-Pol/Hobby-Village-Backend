package com.hobbyvillage.backend.user_cartNdibs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyvillage.backend.UploadDir;

@RestController
public class cartNdibsController {
	
    private cartNdibsServiceImpl cartNdibsServiceImpl;
    
    public cartNdibsController(cartNdibsServiceImpl cartNdibsServiceImpl) {
        this.cartNdibsServiceImpl = cartNdibsServiceImpl;
    }
    
    // 장바구니 리스트 조회
    @RequestMapping("/carts/{email}/lists")
	public List<cartNdibsDTO> getcartlists (
			@PathVariable("email") String email,
			@RequestParam(value = "category", required = true) String category) {
		return cartNdibsServiceImpl.getcartlists(email, category);
	}
	
	// 상품 사진 조회
	@RequestMapping("/carts/getPhoto")
	public String getcartitems (
			@RequestParam(value = "prodCode", required = true) String prodCode) {
		return cartNdibsServiceImpl.getcartitems(prodCode);
	}
	
	// 대여기간 변경
	@PatchMapping("carts/period/modify/{cartCode}")
	public int modifyperiod(@RequestParam(value = "cartCode", required = true) int cartCode) {
		return cartNdibsServiceImpl.modifyperiod(cartCode);
	}
    
    // 이미지 출력
    @GetMapping("/carts/viewImage/{imageName}")
    public ResponseEntity<byte[]> getReqeustFileData(
            @PathVariable(value = "imageName", required = true) String imageName) {
        ResponseEntity<byte[]> result = null;
        
        if (!imageName.equals("undefined")) {
            
        	File file = new File(UploadDir.uploadDir + "\\Uploaded\\ProductsImage", imageName);
            result = null;
            
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", Files.probeContentType(file.toPath()));
                result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;    
                   
    }

}
