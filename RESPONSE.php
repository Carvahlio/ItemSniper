<?php

//Routinize common database operations
class RESPONSE{

    private $success = "success";
    private  $error_message = "error_message";
    private $row = "rows";
    private $post = "required";

    function __construct(){
    }

    //Check if a statement was successfull
    public function checkStatementSuccess(PDOStatement $stmt){

        if($stmt->errorInfo()[1] == null){
            return true;
        }else{
            return false;
        }
    }

    public function getRowsOrError(PDOStatement $stmt){
        if($this->checkStatementSuccess($stmt)){

            if($this->hasResults($stmt)){
                return array( $this->success => true, $this->row => $stmt->fetchAll(PDO::FETCH_ASSOC));
            }else{
                return array( $this->success => true, $this->row => []);
            }
        }else{
            return $this->getStamentErrorResponse($stmt);
        }
    }

    public function checkPost(Array $values){

        foreach ($values as $value){
            if(!isset($_POST[$value])){
                echo json_encode(array($this->success => false, $this->error_message => "Post values not set", $this->post => $values ));
                die();
            }
        }
        return true;
    }

    //Check if we have rows
    public function hasResults(PDOStatement $stmt){

        if($stmt->rowCount() >= 1){
            return true;
        }else{
            return false;
        }
    }

    //Get the error information for an operation
    public function getStamentErrorResponse(PDOStatement $stmt){

        if($this->checkStatementSuccess($stmt)){
            return array($this->success => true, $this->error_message => "", );
        }else{
            return array( $this->success => false, $this->error_message=> "SQL failed : ".$stmt->errorInfo()[2]);
        }
    }
}