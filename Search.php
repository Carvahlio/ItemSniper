<?php
/**
 * Created by PhpStorm.
 * User: Yuval
 * Date: 5/8/2017
 * Time: 11:17 PM
 */

//Class that searches for an item
require_once("./DBCONNECT.php");
class Search{

    private $con;
    private $find;

    function __construct(){
        //Connect to the database
        $this->con = DBCONNECT::CONNECT();
    }

    function __destruct(){
        $this->con = null;
    }

    function getItem(){

        $r = new RESPONSE();

        if($r->checkPost(array("query"))){

            $this->find = $_POST["query"];

            //declaring query
            $sql = "SELECT * FROM product WHERE (product_name LIKE ?) OR (product_category LIKE ?) OR (description LIKE ?)";

            $stmt = $this->con->prepare($sql);
            // execting query
            $stmt->execute(array("%".$this->find."%"   ,   "%".$this->find."%"     , "%".$this->find."%"));

            return $r->getRowsOrError($stmt);
        }
    }
}
?>