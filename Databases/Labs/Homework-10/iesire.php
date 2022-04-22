<?php
session_start();
$ut_prec = $_SESSION['utilizator'];
unset($_SESSION['utilizator']);
session_destroy();
?>
<html>
<body>
<h1>Iesire</h1>
<?php
if (!empty($ut_prec))
{
echo 'Goodbye...<br />';
}
else
{
echo 'Nu ati efectuat log in]<br />';
}
?>
<a href="sesiune.php"> Pagina principala</a>
</body>
</html>
