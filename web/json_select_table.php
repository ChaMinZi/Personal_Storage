<?php  
	include "sqlfunction.php";
	header('Content-Type: application/json; charset=utf8');
	$case = isset($_GET['case']) ? $_GET['case'] : '';
	$result = select_table_sqlfunction($case);

	$data = array();
	//1번째 값은 정상출력, 2번째 값 출력 문제
	
	/*idx, us_id, us_pw, us_name, us_birth, us_email, us_google, us_total, us_jo_date, us_auth*/
	while($row = mysql_fetch_array($result)){
		if($case == 'user') {
			array_push($data,
				array('idx'=>$row[0],
						'us_id'=>$row[1],
						'us_pw'=>$row[2],
						'us_name'=>$row[3],
						'us_birth'=>$row[4],
						'us_email'=>$row[5],
						'us_google'=>$row[6],
						'us_total'=>$row[7],
						'us_jo_date'=>$row[8],
						'us_auth'=>$row[9])
					);
		}

		elseif($case == 'url') {
			array_push($data,
					array('idx'=>$row[0],
							'us_idx'=>$row[1],
							'ur_seq'=>$row[2],
							'ur_addr'=>$row[3],
							'ur_wr_date'=>$row[4])
			);
		}

		elseif($case == 'category') {
			array_push($data,
					array('idx'=>$row[0],
							'ca_name'=>$row[1],
							'ca_total'=>$row[2],
							'ca_addr'=>$row[3])
			);
		}
		
		elseif($case == 'board1') {
			array_push($data,
					array('idx'=>$row[0],
							'bo_ca_idx'=>$row[1],
							'bo_us_idx'=>$row[2],
							'bo_ur_seq'=>$row[3],
							'bo_ur_cnt'=>$row[4],
							'bo_txt'=>$row[5],
							'bo_wr_date'=>$row[6],
							'bo_mo_date'=>$row[7])
			);
		}
		
		
	}
	$json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE + JSON_UNESCAPED_SLASHES);
	echo $json;
?>