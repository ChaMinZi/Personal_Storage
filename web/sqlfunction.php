<?php
	/*connect*/
	function db_connect(){
		$db_host = "localhost";
		$db_user = "root";
		$db_pass = "apmsetup";
		$db_name = "fantasque";
		$connected = mysql_connect($db_host, $db_user, $db_pass);
		mysql_select_db($db_name, $connected);
		//mysql_query("SET NAMES utf8");
		mysql_query("set session character_set_connection=utf8;");
		mysql_query("set session character_set_results=utf8;");
		mysql_query("set session character_set_client=utf8;");

		if(!$connected){
			throw new Exception('DB서버에 접속할 수 없습니다');
		}
		else{
			return $connected;
		}
	}

	/*select table_once*/
	function select_table_sqlfunction($table){
		$connected = db_connect();
		if($connected)
			return select_table($connected, $table);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}

	function select_table($connected, $table){
		$select_query = "select * from $table order by idx desc;";
		$result = mysql_query($select_query, $connected) or die(mysql_error());
		mysql_close($connected);

		return $result;
	}

	/*join_check_ajax*/
	function select_table_sqlfunction2($table, $idx, $check_idx){
		$connected = db_connect();
		if($connected)
			return select_table2($connected, $table, $idx, $check_idx);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}

	function select_table2($connected, $table, $idx, $check_idx){
		$select_query = "select * from $table where $idx = '$check_idx'";
		$result = mysql_query($select_query, $connected) or die(mysql_error());
		mysql_close($connected);

		return $result;
	}

	/*insert_user*/
	function insert_into_member_sqlfunction($id, $pw, $name, $birth, $email, $date){
		$connected = db_connect();
		if($connected)
			return insert_into_member($connected, $id, $pw, $name, $birth, $email, $date);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}

	function insert_into_member($connected, $id, $pw, $name, $birth, $email, $date){
		$insert_query	= "insert into user(us_id, us_pw, us_name, us_birth, us_email, us_jo_date) values ('".$id."', '".$pw."', '".$name."', '".$birth."', '".$email."', '".$date."')";
		$result	= mysql_query($insert_query, $connected) or die(mysql_error());
		mysql_close($connected);
		return $result;
	}
	
	/*fetch_array_user table*/
	function select_memname_sqlfunction2($id){
		$connected = db_connect();
		if($connected)
			return select_memname2($connected, $id);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}
	
	function select_memname2($connected, $id){
		$sql = "select * from user where us_id ='$id'";
		$result = mysql_query($sql, $connected) or die(mysql_error());
		mysql_close($connected);
		$row = mysql_fetch_array($result);
		return $row;
	}
	
	/*select board1 us_idx*/
	function select_useridx_sqlfunction($idx){
		$connected = db_connect();
		if($connected)
			return select_useridx($connected, $idx);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}
	
	function select_useridx($connected, $idx){
		$sql = "select * from user where idx ='$idx'";
		$result = mysql_query($sql, $connected) or die(mysql_error());
		mysql_close($connected);
		$row = mysql_fetch_array($result);
		return $row;
	}
	



	/*insert_url*/
	function insert_into_url_sqlfunction($idx, $seq, $addr, $date){
		$connected = db_connect();
		if($connected)
			return insert_into_url($connected, $idx, $seq, $addr, $date);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}
	
	function insert_into_url($connected, $idx, $seq, $addr, $date){
		$insert_query = "insert into url(us_idx, ur_seq, ur_addr, ur_wr_date) values ('".$idx."', '".$seq."', '".$addr."', '".$date."')";
		$result	= mysql_query($insert_query, $connected) or die(mysql_error());
		mysql_close($connected);
		return $result;
	}

	/*fetch_array_category table*/
	function select_category_sqlfunction2($idx){
		$connected = db_connect();
		if($connected)
			return select_category2($connected, $idx);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}
	
	function select_category2($connected, $idx){
		$sql = "select * from category where idx ='$idx'";
		$result = mysql_query($sql, $connected) or die(mysql_error());
		mysql_close($connected);
		$row = mysql_fetch_array($result);
		return $row;
	}
	
	
	/*num_rows_url*/
	function count_url_table_sqlfunction($idx, $seq){
		$connected = db_connect();
		if($connected)
			return count_url_table($connected, $idx, $seq);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}
	
	function count_url_table($connected, $idx, $seq){
		//SELECT * FROM `url` WHERE `us_idx` = 1 AND `ur_seq` = 1
		$sql = "select * from url where us_idx ='$idx' and ur_seq = '$seq'";
		$result = mysql_query($sql, $connected) or die(mysql_error());
		mysql_close($connected);
		$row = mysql_num_rows($result);
		return $row;
	}
	
	/*insert_board1*/
	function insert_into_board1_sqlfunction($ca_idx, $us_idx, $ur_seq, $ur_cnt, $txt, $wr_date){
		$connected = db_connect();
		if($connected)
			return insert_into_board1($connected, $ca_idx, $us_idx, $ur_seq, $ur_cnt, $txt, $wr_date);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}
	
	/*idx, bo_ca_idx, bo_us_idx, bo_ur_seq, bo_ur_cnt, bo_txt, bo_wr_date, bo_mo_date*/
	function insert_into_board1($connected, $ca_idx, $us_idx, $ur_seq, $ur_cnt, $txt, $wr_date){
		$insert_query	= "insert into board1(bo_ca_idx, bo_us_idx, bo_ur_seq, bo_ur_cnt, bo_txt, bo_wr_date) values ('".$ca_idx."', '".$us_idx."', '".$ur_seq."', '".$ur_cnt."', '".$txt."', '".$wr_date."')";
		$result	= mysql_query($insert_query, $connected) or die(mysql_error());
		mysql_close($connected);

		/*us_total*/
		update_user_total_sqlfunction($us_idx, $ur_seq);
		return $result;
	}
	
	/*update_user_table_user_total*/
	function update_user_total_sqlfunction($idx, $seq){
		$connected = db_connect();
		if($connected)
			return update_user_total($connected, $idx, $seq);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}
	/*UPDATE `user` SET `us_total` = '1' WHERE `user`.`idx` = 2*/
	function update_user_total($connected, $idx, $seq){
		$update_query = "update user set us_total = '".$seq."' where idx ='".$idx."'";
		mysql_query("SET NAMES utf8");
		$result = mysql_query($update_query, $connected) or die(mysql_error());
		mysql_close($connected);
		return $result;
	}
	
	/*select url table, $bo_us_idx, $bo_ur_seq*/
	function select_url_sqlfunction($bo_us_idx, $bo_ur_seq){
		$connected = db_connect();
		if($connected)
			return select_url($connected, $bo_us_idx, $bo_ur_seq);
		else
			throw new Exception('DB서버에 접속할 수 없습니다.');
	}
	//SELECT * FROM `url` WHERE us_idx = '1' and ur_seq = '1'
	function select_url($connected, $bo_us_idx, $bo_ur_seq){
		$select_query = "select * from url where us_idx = '".$bo_us_idx."' and ur_seq ='".$bo_ur_seq."'";
		$result = mysql_query($select_query, $connected) or die(mysql_error());
		mysql_close($connected);

		return $result;
	}

?>