<html>
<head>
    <title>Autor</title>
    <body bgcolor="lightgray">
    <style>
.button {
display: inline-block;
padding: 15px 15px;
font-size: 20px;
cursor: pointer;
text-align: center;
text-decoration: none;
color: black;
background-color: gray;
border: none;
border-radius: 12px;
box-shadow: 0 9px #999;
}


.button:active {
background-color: white;
box-shadow: 0 5px #999;
}
table {
    text-align:center;
margin: auto;
    border-color: black;
width: 50%;
}
div {
       border: 35px double;
        text-align: center;
        border-color: gray;
    }
p {
font-family: "Chalkduster", Times, serif;
font-size: 30px;
}
th {
    background-color: gray;
    color: black;
    border: 1px solid black;

}
td{

    border-color: gray;
    width: auto;

}
tr{
    text-align: center;
    margin: 5px;
    
}

</style>
    </head>
<body>
    <div>
<?php
    
    
        include "server.php";

    // se emite interogarea
    $query ="SELECT id_carte,id_aut

                             FROM Autor";
    $result = mysqli_query($connect, $query);
    // verifică dacă rezultatul este în regulă
    if (!$result)
    {
      die('Interogare gresita: ' . mysqli_error($connect));    }
    // se obţine numărul tuplelor returnate
    $num_results = mysqli_num_rows($result);
    if(!$num_results)
    {
        echo '<br><br>';
      echo 'no data found' ;
       echo '<br><br>';
        echo '<br><a href="view.html" class="button">Back</a>';
        echo '<br><br>';
      exit;
    }
   
    // se afişează fiecare tuplă returnată
    echo '<table>
      <tr>
        <th>id_carte</th>

        <th>id_aut</th>
               
     
      </tr>';
    for ($i = 0; $i < $num_results; $i++)
    {
      $row = mysqli_fetch_assoc($result);
echo '<tr><td>'.htmlspecialchars(stripslashes($row['id_carte'])).'</td>';

   echo '<td>'.htmlspecialchars(stripslashes($row['id_aut'])).'</td></tr>';
    }
    echo '</table>';
    // deconectarea de la BD
    mysqli_close($connect);
    ?>
<br><br>
    <a href="view.html" class="button">Back</a>
<br><br>

    </body>
    </html>
