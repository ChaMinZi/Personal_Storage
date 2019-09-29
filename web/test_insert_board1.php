<?php
	header("Content-Type: text/html; charset=UTF-8");
	include "sqlfunction.php";

	//01. 현재 로그인한 아이디 받아와야됨. us_idx 로 교체 예정
	$us_idx_2 = $_REQUEST['us_idx'];
	$result = select_useridx_sqlfunction($us_idx_2);

	$idx = $result['idx'];
	$us_id = $result['us_id'];
	$us_pw = $result['us_pw'];
	$us_name = $result['us_name'];
	$us_email = $result['us_email'];
	$us_total = $result['us_total'];

	$output = array();

	$url = $_REQUEST['url'];

	//instagram의 경우 explode() 사용가능
	echo $url."<bR>";
	$url_explode = explode("//", $url);
	$url_explode_short = explode(".", $url_explode[1]);
	
	print_r($url_explode_short);

	if($url_explode_short[0] =='facebook' || $url_explode_short[1] == 'facebook') {
		$python3 = `python3 facebook.py $url`;
		$pattern = "/<img[^>]*src=[\'\"]?([^>\'\"]+)[\'\"]?[^>]*>/";
		preg_match_all($pattern, $python3, $output);

		print_r($output[1]);
		$count = count($output[1]);
	}
	elseif($url_explode_short[0] == 'instagram' || $url_explode_short[1] == 'instagram') {
		$python3 = exec("python3 instagram.py ".$url."", $output);
		
		print_r($output);
		$count = count($output);
	}
	echo"<br>"."<br>"."<br>"."<br>"."<br>";
	echo"array size: ".$count;

	//03. 리턴값 처리(insert 시작)
	/*idx, us_idx, ur_seq, ur_addr, ur_wr_date*/
	$us_idx = $idx;		//user index
	$ur_seq = $us_total + 1;
	$ur_addr = array();

	$ur_wr_date = date("Y-m-d h:i:s");	

	$ca_idx = '1';
	$txt = "테스트입니다001";
	$wr_date = date("Y-m-d h:i:s");

	//$url count 만큼 insert 대비
	if($url_explode_short[0] =='facebook' || $url_explode_short[1] == 'facebook') {
		for($i = 0; $i<$count; $i++) {
			$ur_addr[$i] = $output[1][$i];
			$sql_url = insert_into_url_sqlfunction($us_idx, $ur_seq, $ur_addr[$i], $ur_wr_date);
		}
	}
	elseif($url_explode_short[0] == 'instagram' || $url_explode_short[1] == 'instagram') {
		for($i = 0; $i<$count; $i++) {
			$sql_url = insert_into_url_sqlfunction($us_idx, $ur_seq, $output[$i], $ur_wr_date);
		}
	}
	$ur_cnt = count_url_table_sqlfunction($us_idx, $ur_seq);
	$sql_board = insert_into_board1_sqlfunction($ca_idx, $us_idx, $ur_seq, $ur_cnt, $txt, $wr_date);
?>