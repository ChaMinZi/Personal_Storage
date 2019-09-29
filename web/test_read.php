<?php
	error_reporting(E_ALL);
	ini_set("display_errors", 1);
	header("Content-Type: text/html; charset=euc-kr");
	include "sqlfunction.php";

	//$python2 = `python2 python2.py`;
	//$python3 = `python3 python2.py`;
	$pattern = "/<img[^>]*src=[\'\"]?([^>\'\"]+)[\'\"]?[^>]*>/";
	preg_match_all($pattern, $python3, $output);
	print_r($output[1]);
?>