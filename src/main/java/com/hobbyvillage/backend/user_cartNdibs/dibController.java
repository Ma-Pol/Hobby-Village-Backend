package com.hobbyvillage.backend.user_cartNdibs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyvillage.backend.UploadDir;

@RestController
public class dibController {
	
	 private dibServiceImpl dibServiceImpl;
	    
	    public dibController(dibServiceImpl dibServiceImpl) {
	        this.dibServiceImpl = dibServiceImpl;
	    }
	    
	// 찜 리스트 조회
    @RequestMapping("/dibs/{email}/lists")
	public List<dibDTO> getdiblists (
			@PathVariable("email") String email,
			@RequestParam(value = "category", required = true) String category) {
		return dibServiceImpl.getdiblists(email, category);
	}
	
	// 찜 리스트 조회(카테고리 수량체크)
		 @RequestMapping("/dibs/{email}/lists/item")
		public List<dibDTO> getdibcategorylist(
				@PathVariable("email") String email) {
			return dibServiceImpl.getdibcategorylist(email);
		}
	
	// 상품 사진 조회
	@RequestMapping("/dibs/getPhoto")
	public String getdibitems (
			@RequestParam(value = "prodCode", required = true) String prodCode) {
		return dibServiceImpl.getdibitems(prodCode);
	}
	
	// 찜 삭제
	@DeleteMapping("/dibs/delete/{dibCode}")
	public int deletedib(@PathVariable(value = "dibCode", required = true) int dibCode) {
		return dibServiceImpl.deletedib(dibCode);
	}
    
    // 이미지 출력
    @GetMapping("/dibs/viewImage/{imageName}")
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
