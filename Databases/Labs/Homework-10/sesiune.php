<?php
session_start();
if (isset($_POST['nume']) && isset($_POST['parola']))
{

$nume = $_POST['nume'];
$parola = $_POST['parola'];
$db_conn = new mysqli('localhost', 'student', 'student123', 'scoala');
if (mysqli_connect_errno()) {
echo 'Eroare de conectare la BD:'.mysqli_connect_error();
exit();
}
$query = 'select * from acces '
."where nume='$nume' "
." and parola=sha1('$parola')";
$result = $db_conn->query($query);
if ($result->num_rows >0 )
{
$_SESSION['utilizator'] = $nume;
}
$db_conn->close();
}
?>
<html>
<body>
<h1>Pagina principala</h1>
<?php
if (isset($_SESSION['utilizator']))
{
echo 'Ati accesat cu numele: '.$_SESSION['utilizator'].' <br />';
echo '<a href="iesire.php">Iesire</a><br />';
}
else
{
if (isset($nume))
{

echo 'Nu se poate efectua log in.<br />';
}
else
{

echo 'Nu ati efectuat log in.<br />';
}

// provide form to log in
echo '<form method="post" action="sesiune.php">';
echo '<table>';
echo '<tr><td>Numele:</td>';
echo '<td><input type="text" name="nume"></td></tr>';
echo '<tr><td>Parola:</td>';
echo '<td><input type="password" name="parola"></td></tr>';
echo '<tr><td colspan="2" align="center">';
echo '<input type="submit" value="Intrare"></td></tr>';
echo '</table></form>';
}
?>
<br />
<a href="acord.php">Pagina doar pentru cei autoriautorizati</a>
</body>
</html>
