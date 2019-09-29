<?php
	include "sqlfunction.php";
	header('Content-Type: application/json; charset=utf8');

	$bo_us_idx = $_REQUEST['idx'];
	$bo_ur_seq = $_REQUEST['seq'];

	$result = select_url_sqlfunction($bo_us_idx, $bo_ur_seq);

	$data = array();

	while($row = mysql_fetch_array($result)){
		//idx, us_idx, ur_seq, ur_addr, ur_wr_date
		array_push($data,
				array('idx'=>$row[0],
						'us_idx'=>$row[1],
						'ur_seq'=>$row[2],
						'ur_addr'=>$row[3],
						'ur_wr_date'=>$row[4])
		);
	}
	$json = json_encode(array("webnautes"=>$data),  JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE + JSON_UNESCAPED_SLASHES);
	echo $json;
?>