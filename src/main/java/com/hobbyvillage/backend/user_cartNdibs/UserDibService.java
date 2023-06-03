package com.hobbyvillage.backend.user_cartNdibs;

import java.util.List;

public interface UserDibService {
	UserDibCountDTO getDibCount(String email);
	List<UserDibDTO> getDibList(String email, String filter);
	int deleteDib(int dibCode);
	int deleteSelectedDib(List<UserDibCodeDTO> dibList);
}
