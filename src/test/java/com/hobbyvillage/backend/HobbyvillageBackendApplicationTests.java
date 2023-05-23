package com.hobbyvillage.backend;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HobbyvillageBackendApplicationTests {

	 @Test
	 public void TestConnect() throws Exception
	 {
	  Class.forName( "com.mysql.jdbc.Driver" );

	  // 현재 소스 코드는 DB 이름은 test 이고 사용자 및 비밀번호도 test 이다.

	  // QQQ: 테스트 환경에 적합하게 DB 이름 및 사용자/비밀번호를 수정해야 한다.
	  try( Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/hobbyvillage?serverTimezone=Asia/Seoul",
			  "root", "123456" ) )
	  {
	   System.out.println( "Connection success" );
	  }
	  catch( Exception e )
	  {
	   e.printStackTrace( );
	  }
	 }
	}

