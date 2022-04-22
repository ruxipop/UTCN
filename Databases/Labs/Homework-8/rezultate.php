<html>
 <head>
  <title>Afisare note studenti rezultate</title>
 </head>
<body>
<h1>Afisare note studenti rezultate</h1>
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
	die('A aparaut o eroare:'. $dsn->connect_error);
}
$query = "select * from vnotestud where nr_matr=".$nr_matr;
$result = mysqli_query($dsn, $query);

if (!$result)
{
	die('A aparaut o eroare:'.mysqli_error());
}
$num_results = mysqli_num_rows($result);
for ($i=0; $i <$num_results; $i++)
{
$row = mysqli_fetch_assoc($result);
echo '<p><strong>'.($i+1).'. Numele: ';
echo htmlspecialchars(stripslashes($row['numele']));
echo '</strong><br />Materia: ';
echo stripslashes($row['titlu_mat']);
echo '<br />Felul examinarii: ';
echo stripslashes($row['fel_examen']);
echo '<br />Nota: ';
echo stripslashes($row['nota']);
echo '</p>';

}
echo '</table>';
mysqli_close($dsn);
?>
