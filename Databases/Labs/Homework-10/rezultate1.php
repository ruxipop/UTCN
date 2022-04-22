<html>
 <head>
   <title>Afisare note studenti</title>
   <style>
    table, th, td
	{
		border: 1px solid black;
	}
   </style>
  </head>
<body>
  <h1 align="center">Afisare note studenti</h1>
<?php
$nr_matr=$_POST['nr_matr'];
$nr_matr= trim($nr_matr);
if (!$nr_matr)
{
echo 'Nu ati introdus criteriul de cautare. Va rog sa incercati din nou.';
exit;
}
if (!get_magic_quotes_gpc())
{
$nr_matr = addslashes($nr_matr);
}

require_once('PEAR.php');
$user = 'student';
$pass = 'student123';
$host = 'localhost';
$db_name = 'scoala';

$dsn= new mysqli( $host, $user, $pass, $db_name);

if ($dsn->connect_error)
{
	die('Eroare la conectare:'. $dsn->connect_error);
}

$query = "select * from vnotestud where nr_matr=".$nr_matr;
$result = mysqli_query($dsn, $query);
// verifică dacă rezultatul este în regulă
if (!$result)
{
	die('Interogare gresita :'.mysqli_error($dsn));
}

$num_results = mysqli_num_rows($result);

echo ' <Table style = "width:100%">
<tr>
 <th>Nr.crt.</th>
 <th>Numele</th>
 <th>Materia</th>
 <th>Felul examinarii</th>
 <th>Nota</th>
</tr>'; 
for ($i=0; $i <$num_results; $i++)
{
$row = mysqli_fetch_assoc($result);

echo '<tr><td>'.($i+1).'</td>';
echo '<td>'.htmlspecialchars(stripslashes($row['numele'])).'</td>';
echo '<td>'.stripslashes($row['titlu_mat']).'</td>';
echo '<td>'.stripslashes($row['fel_examen']).'</td>';
echo '<td>'.stripslashes($row['nota']).'</td>';

}
echo '</table>';

mysqli_close($dsn);
?>
</body>
</html>
