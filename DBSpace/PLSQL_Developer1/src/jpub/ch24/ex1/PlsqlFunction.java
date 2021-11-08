﻿package jpub.ch24.ex1;
import java.sql.*;
//args[0] 값(10)을 넣고 실행하기
public class PlsqlFunction {
  public static void main(String[] args) throws SQLException {
   //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    // DB 접속
	  
	  
    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "scott");

    // 변수 선언
    int p1OutValue ;
    int p2InValue = Integer.parseInt(args[0]);
    
    CallableStatement cs;
    // 다음의 두 가지 방법 중 하나를 사용하면 된다. 두 방법은 완전히 동일하다.
    // 1. 익명 PL/SQL 블록을 사용하는 방법
    cs = conn.prepareCall("BEGIN ? := factorial(?) ; END ;");
    // 2. JDBC에서 지원하는 저장 함수 호출 방법
    // cs = conn.prepareCall("{? = call factorial(?)}");

    // 결과를 받을 OUT parameter에 대한 정보를 registerOutParameter 함수를 이용하여 설정 
    cs.registerOutParameter(1, Types.INTEGER);
    
    // setXXX 메소드를 사용하여 IN 유형 파라미터의 값 설정
    cs.setInt(2, p2InValue);
    
    cs.execute();// Statement 수행
    p1OutValue = cs.getInt(1);// 수행 결과 data를 가져옴
    System.out.println(p2InValue + "! = " + p1OutValue);// 수행 결과 출력
    
    
   //////////////////////////////////////////////////////////////////////////////////////////////
  //SQL92 syntax
    CallableStatement cs1 = conn.prepareCall( "{call proc (?,?)}" ) ;          // stored proc
    CallableStatement cs2 = conn.prepareCall( "{? = call func (?,?)}" ) ;      // stored func
  //Oracle PL/SQL block syntax
    CallableStatement cs3 = conn.prepareCall( "begin proc (?,?); end;" ) ;     // stored proc
    CallableStatement cs4 = conn.prepareCall( "begin ? := func(?,?); end;" ) ; // stored func
   ///////////////////////////////////////////////////////////////////////////////////////////////
  }
}

