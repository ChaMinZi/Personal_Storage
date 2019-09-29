<?php
	header("Content-Type: text/html; charset=UTF-8");
	include "sqlfunction.php";

	//01. 현재 로그인한 아이디 받아와야됨. us_idx 로 교체 예정
	$result = select_memname_sqlfunction2('fantasque');
	print_r($result);

?>