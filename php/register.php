<?php
 
   if($_SERVER['REQUEST_METHOD']=='POST'){
  // echo $_SERVER["DOCUMENT_ROOT"];  // /home1/demonuts/public_html
//including the database connection file
       include_once("config.php");
       
	$lastname = $_POST['lastname'];
	$firstname = $_POST['firstname'];
 	$phonenumber = $_POST['phonenumber'];
 	$password = $_POST['password'];
  
	 if($firstname == '' || $lastname == '' || $phonenumber == '' || $password == ''){
	        echo json_encode(array( "status" => "false","message" => "Please enter missing fields!") );
	 }else{
			 
	        $query= "SELECT * FROM users WHERE phoneNumber='$phonenumber'";
	        $result= mysqli_query($con, $query);
		 
	        if(mysqli_num_rows($result) > 0){  
	           echo json_encode(array( "status" => "false","message" => "Phone number is already registered") );
	        }else{ 
		 	 $query = "INSERT INTO users (firstname,lastname,phonenumber,password) VALUES ('$firstname','$lastname','$phonenumber','$password')";
			 if(mysqli_query($con,$query)){
			    
			     $query= "SELECT * FROM users WHERE phonenumber='$phonenumber'";
	                     $result= mysqli_query($con, $query);
		             $emparray = array();
	                     if(mysqli_num_rows($result) > 0){  
	                     while ($row = mysqli_fetch_assoc($result)) {
                                     $emparray[] = $row;
                                   }
	                     }
			    echo json_encode(array( "status" => "true","message" => "Successfully registered!" , "data" => $emparray) );
		 	 }else{
		 		 echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
		 	}
	    }
	            mysqli_close($con);
	 }
     } else{
			echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
	}
 
 ?>