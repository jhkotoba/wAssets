package com.wAssets.common;

public interface Constant {
	
	/* const */
	public static final String YYYYMMDD = "(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";
	public static final String YYYYMMDDHH = "(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3])";
	public static final String YYYYMMDDHHMI = "(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])";
	public static final String YYYYMMDDHHMISS = "(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
	public static final String HH= "(0[0-9]|1[0-9]|2[0-3])";
	public static final String MMDD = "(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
	public static final String HHMI = "(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])";
	
	public static final int[] LAST_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/* common */
	public static final String Y = "Y";
	public static final String N = "N";
	public static final String GRID_STATE_SELECT = "SELECT";			//조회
	public static final String GRID_STATE_INSERT = "INSERT";			//저장
	public static final String GRID_STATE_UPDATE = "UPDATE";			//수정
	public static final String GRID_STATE_REMOVE = "REMOVE";			//삭제
	
	public static final String CODE_SUCCESS = "0000";					//성공
	public static final String CODE_SERVER_ERROR = "9000";				//서버에러
	public static final String CODE_REPOSITORY_ERROR = "9001";			//데이터베이스에러
	public static final String CODE_APPALY_EMPTY = "9002";				//적용사항없음
	public static final String CODE_INSERT_EMPTY = "9003";				//저장항목 없음
	public static final String CODE_UPDATE_EMPTY = "9004";				//수정항목 없음
	public static final String CODE_DELETE_EMPTY = "9005";				//삭제항목 없음
	public static final String CODE_DATE_ERROR = "9020";				//날짜오류
	public static final String CODE_NO_DATE_YYMMDD = "9021";			//날짜형식 YYMMDD가 아님
	public static final String CODE_NO_DATE_YYYYMMDD = "9022";			//날짜형식 YYYYMMDD가 아님
	public static final String CODE_NO_DATE_YYYYMMDDHHMM = "9023";		//날짜형식 YYYYMMDDHHMM가 아님
	public static final String CODE_NO_DATE_YYYYMMDDHHMMSS = "9024";	//날짜형식 YYYYMMDDHHMMSS가 아님
	public static final String CODE_DATA_EMPTY = "9400";				//데이터가 없음
	public static final String CODE_ESSENTIAL_DATA_EMPTY = "9401";		//필수데이터가 없음
	public static final String CODE_USER_DEFINED_ERROR = "9998";		//사용자정의오류
	public static final String CODE_UNKNOWN_ERROR = "9999";				//알수없는오류
	
	/* member */
	public static final String TOKEN = "SESSION_TOKEN";	
	public static final String SIGN = "WMEMBER";
	public static final String PASSWORD_FORMAT = "%0128x";
	public static final String JWT_SUBJECT = "wmember";
	
	public static final String CODE_NO_LOGIN = "3000";
	public static final String CODE_DIFF_PASSWORD = "3001";
	public static final String CODE_NO_USER = "3002";
	public static final String CODE_LOING_CHECK_ERROR = "3003";
}