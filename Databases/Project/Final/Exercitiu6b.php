<html>
<head>
<title>Exercitiul 6-b</title>
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
    margin:30px;
    
}
</style>
</head>
<body>
<?php
$gen= $_POST['gen'];
$gen = trim($gen);
if (!$gen)
{
echo 'Nu ati introdus genul . Va rog sa incercati din nou.';
  echo '<br><br>';
  echo '<br><a href="Exercitiu62.php" class="button">Back</a>';
exit;
}
  include "server.php";
 
// se emite interogarea
$query =" SELECT gen, min(nr_exemplare) as minim,avg(nr_exemplare) as mediu, max(nr_exemplare) as maxim
                        FROM Carte
                        where gen='$_POST[gen]'
                        group by gen;";
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
echo 'Nu exista genul :'. $gen;
 echo '<br><br>';
  echo '<br><a href="Exercitiu62.php" class="button">Back</a>';
exit;
}
echo '<p> Numărul minim ,mediu,maxim pt cărțile cu genul '. $gen.'</p>';
// se afişează fiecare tuplă returnată
echo '<table>
<tr>
    <th>Gen</th>
  <th>Minim</th>

  <th>Mediu</th>
  <th>Maxim</th>
</tr>';
   
for ($i = 0; $i < $num_results; $i++)
{
$row = mysqli_fetch_assoc($result);
echo '<tr><td>'.htmlspecialchars(stripslashes($row['gen'])).'</td>';
    echo '<td>'.htmlspecialchars(stripslashes($row['minim'])).'</td>';
  echo '<td>'.htmlspecialchars(stripslashes($row['mediu'])).'</td>';
  echo '<td>'.htmlspecialchars(stripslashes($row['maxim'])).'</td></tr>';
}
        
echo '</table>';
// deconectarea de la BD
mysqli_close($connect);
?>
<a href="Exercitiu62.php" class="button">Back</a>

</body>
</html>
