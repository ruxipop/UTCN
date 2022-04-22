<html>
<head>
    <title>Exercitiul 4-a</title>
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
<?php
$nrz = $_POST['nrz'];
$nrz= trim($nrz);
if (!$nrz)
{
 echo 'Nu ati introdus nr de zile . Va rog sa incercati din nou.';
   echo '<br><br>';
   echo '<br><a href="Exercitiu41.php" class="button">Back</a>';
 exit;
}
   
   include "server.php";

// se emite interogarea
$query ="CALL procedura4a('$nrz'); ";

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
 echo 'Nu exista carți nerestituite imprumutate in urma cu  :'. $nrz. ' zile ';
  echo '<br><br>';
   echo '<br><a href="Exercitiu41.php" class="button">Back</a>';
 exit;
}
echo '<p> Numele și numărul de telefon pentru persoanele care au cărți nerestituite împrumutate în urmă cu  '.$nrz .' zile'.'</p>';
// se afişează fiecare tuplă returnată
echo '<table>
 <tr>
   <th>nume</th>

   <th>telefon</th>
   
 </tr>';
for ($i = 0; $i < $num_results; $i++)
{
 $row = mysqli_fetch_assoc($result);
echo '<tr><td>'.htmlspecialchars(stripslashes($row['nume'])).'</td>';
   echo '<td>'.htmlspecialchars(stripslashes($row['telefon'])).'</td>';

}
echo '</table>';
// deconectarea de la BD
mysqli_close($connect);
?>
<br><br>
<a href="Exercitiu41.php" class="button">Back</a>
<br><br>
</body>
</html>
