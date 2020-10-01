package com.wAssets.common;

public interface Constant {
	
	/* const */
	public static final String YYYYMMDD = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
	public static final String YYYYMMDDHH = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])";
	public static final String YYYYMMDDHHMI = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])";
	public static final String YYYYMMDDHHMISS = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])";
	public static final String HH= "(0[0-9]|1[0-9]|2[0-3])";
	public static final String MMDD = "(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
	public static final String HHMI = "(0[0-9]|1[0-9]|2[0-3])([0-5][0-9])";	
	
	/* common */
	public static final String RESULT_CODE_SUCCESS = "0000";
	public static final String RESULT_CODE_SERVER_ERROR = "9000";
	public static final String RESULT_CODE_UNKNOWN_ERROR = "9444";
	
	/* member */
	public static final String RESULT_CODE_DIFF_PASSWORD = "3001";
	public static final String RESULT_CODE_NO_USER = "3002";
	
	/* assets */
	public static final String RESULT_CODE_VALIDATION_ACCOUNT = "4001";
	
	

}
