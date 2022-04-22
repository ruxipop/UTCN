<html>
 <head>
   <title>Nota a fost adaugata</title>
   <style>
    table, th, td
	{
		border: 1px solid black;
	}
   </style>
  </head>
<body>
  <h1 align="center">Nota adaugata</h1>
<?php

$nota=$_POST['nota'];
$nota= trim($nota);
if (!$nota)
{
echo 'Nu ati introdus criteriul de cautare. Va rog sa incercati din nou.';
exit;
}
if (!get_magic_quotes_gpc())
{
$nota = addslashes($nota);
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
$stmt = $dsn->prepare("insert into nota values(1, 1, ?)");
$stmt->bind_param("d", $nota);
$stmt->execute();
echo $stmt->affected_rows.' Nota adaugata in BD.';
$stmt->close();

mysqli_close($dsn);
?>
</body>
</html>
