<?php
        $servername='localhost';
        $username='root';
        $password='';
        $dbname='proiect16';

   
        $connect= new mysqli($servername, $username, $password, $dbname);
    if ($connect->connect_error)
    {
        die('A aparaut o eroare:'. $connect->connect_error);
    }
    
        
?>
