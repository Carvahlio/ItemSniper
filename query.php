<?php
require_once ("./Search.php");
$search = new Search();
echo json_encode($search->getItem());
?>