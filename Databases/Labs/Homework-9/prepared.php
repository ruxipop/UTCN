<html>
 <head>
  <title>Acordare nota student Rezultate</title>
  <style>
   table, th, td
   {
     border: 1px solid black;
   }
  </style>
 </head>
<body>
 <h1>Acordare nota student Rezultate</h1>
<?php

$nr_matr=$_POST['nr_matr'];
$nr_matr= trim($nr_matr);
$cod_ex=$_POST['cod_ex'];
$cod_ex= trim($cod_ex);
$nota=$_POST['nota'];
$nota= trim($nota);
if (!$nr_matr)
{
  echo 'Nu ati introdus numarul matricol.';
  exit;
}
if (!$cod_ex)
{
  echo 'Nu ati introdus codul examenului. ';
  exit;
}
if (!$nota)
{
  echo 'Nu ati introdus nota.';
  exit;
}
if (!get_magic_quotes_gpc())
{
  $nr_matr = addslashes($nr_matr);
  $cod_ex = addslashes($cod_ex);
  $nota = addslashes($nota);
}
$user = 'root';
$pass = '';
$host = 'localhost';
$db_name = 'scoala';

$connect = mysqli_connect($host, $user, $pass, $db_name);
=
if ($connect->connect_error)
{
  die('Eroare la conectare: ' . $connect->connect_error);
}

$query = "insert into nota (nr_matr, code, nota) values (?, ?, ?)";
$stmt = $connect->prepare($query);
$stmt->bind_param("ddd", $nr_matr, $cod_ex, $nota);
$stmt->execute();
echo $stmt->affected_rows.' Nota adaugata in BD.';
$stmt->close();

mysqli_close($connect);
?>
</body>
</html>
