<html>
<head>
    <title>Exercitiul 5-a</title>
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
div {
       border: 35px double;
        text-align: center;
        border-color: gray;
margin: 15px;
    }
table {
    text-align:center;
margin: auto;
    border-color: black;
width: 50%;
}
p {
font-family: "Chalkduster", Times, serif;
font-size: 15px;
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
<?php
function read(){
    $titlu = $_POST['titlu'];
    $titlu = trim($titlu);
    if (!$titlu)
    {
      echo 'Nu ati introdus titlul . Va rog sa incercati din nou.';
        echo '<br><br>';
        echo '<br><a href="Exercitiu51.php" class="button">Back</a>';
      exit;
    }
    return $titlu;
    
}
$titlu = read();
    include "server.php";

// se emite interogarea
$query ="SELECT titlu,gen,nr_pagini
    FROM Carte
    WHERE nr_pagini in (select nr_pagini from Carte where titlu='$_POST[titlu]')
    ORDER BY id_carte;";
$result = mysqli_query($connect, $query);
// verifică dacă rezultatul este în regulă
if (!$result)
{
  die('Interogare gresita: ' . mysqli_error($connect));
}
// se obţine numărul tuplelor returnate
$num_results = mysqli_num_rows($result);
if(!$num_results)
{
  echo 'Nu exista titlul :'. $titlu ;
   echo '<br><br>';
    echo '<br><a href="Exercitiu51.php" class="button">Back</a>';
  exit;
}
echo '<p> Cărțile cu același număr de pagini ca și cartea cu titlul '. $titlu.'</p>';
// se afişează fiecare tuplă returnată
echo '<table>
  <tr>
    <th>titlu</th>

    <th>gen</th>
    <th>nr_pagini</th>
  </tr>';
for ($i = 0; $i < $num_results; $i++)
{
  $row = mysqli_fetch_assoc($result);
echo '<tr><td>'.htmlspecialchars(stripslashes($row['titlu'])).'</td>';
    echo '<td>'.htmlspecialchars(stripslashes($row['gen'])).'</td>';
    echo '<td>'.htmlspecialchars(stripslashes($row['nr_pagini'])).'</td></tr>';
}
echo '</table>';
// deconectarea de la BD
mysqli_close($connect);
?>
<a href="Exercitiu51.php" class="button">Back</a>

</body>
</html>
