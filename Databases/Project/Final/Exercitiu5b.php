<html>
<head>
<title>Exercitiul 5-b</title>
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
div {    border: 35px double;
        text-align: center;
        border-color: gray;
       margin: 15px;
    }
p {
font-family: "Chalkduster", Times, serif;
font-size: 30px;
}
table {
    text-align:center;
margin: auto;
    border-color: black;
width: 50%;
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
</style>
    </head>
<body>
    <div>
<?php
    
    
        include "server.php";

    // se emite interogarea
    $query ="SELECT DISTINCT (id_carte)
    FROM Autor a
    WHERE NOT  (a.id_aut =all (SELECT id_aut FROM Autor  WHERE id_carte=a.id_carte))
    ORDER BY id_carte;";
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
        echo '<br><a href="Exercitiu52.php" class="button">Back</a>';
        echo '<br><br>';
      exit;
    }
   
    // se afişează fiecare tuplă returnată
  echo '<p> Cărțile cu mai mulți autori '. '</p>';
    echo '<table>
      <tr>
        <th>id_carte</th>
      </tr>';
    for ($i = 0; $i < $num_results; $i++)
    {
      $row = mysqli_fetch_assoc($result);
    echo '<tr><td>'.htmlspecialchars(stripslashes($row['id_carte'])).'</td></tr>';

    }
    echo '</table>';
    // deconectarea de la BD
    mysqli_close($connect);
    ?>
	
	<br><br>
    <a href="Exercitiu52.php" class="button">Back</a>
	<br><br>
    </body>
    </html>
