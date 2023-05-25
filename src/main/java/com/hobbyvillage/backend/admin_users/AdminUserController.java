package com.hobbyvillage.backend.admin_users;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
public class AdminUserController {

	private AdminUserServiceImpl Service;

	public AdminUserController(AdminUserServiceImpl Service) {
		this.Service = Service;
	}
	
    @RequestMapping("/m/users/details/{userCode}")
    public AdminUserDTO getTotalUsers(@PathVariable(value = "userCode", required = true) int userCode) throws Exception {
    AdminUserDTO MemberDetail = Service.getTotalUsers(userCode);
    return MemberDetail;
    }
    
    @GetMapping("/m/users/profPicture/{profPicture}")
	public ResponseEntity<byte[]> getReqeustFileData(
			@PathVariable(value = "profPicture", required = true) String profPicture) {
			File file = new File(UploadDir.getUploaddir() + "\\Uploaded\\UserProfileImage", profPicture);
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
