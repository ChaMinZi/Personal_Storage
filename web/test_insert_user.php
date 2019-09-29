<?php
	include "sqlfunction.php";
	header('Content-Type: application/json; charset=utf8');

	print_r($_REQUEST);

	//{"us_id":"아이디","us_pw":"비번","us_name":"이름","us_birth":"생일","us_email":"이메일"}
	$us_id = $_REQUEST[id];
	$us_pw = $_REQUEST[pw];
	$us_name = $_REQUEST[name];
	$us_birth = $_REQUEST[birth];
	$us_email = $_REQUEST[email];
	$date = date("Y-m-d h:i:s");	
	
	insert_into_member_sqlfunction($us_id, $us_pw, $us_name, $us_birth, $us_email, $date);
?>