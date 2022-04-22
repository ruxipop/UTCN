<?php
session_start();
echo '<h1>Pagina cu acces limitat</h1>';
// se verifică variabila de sesiune
if (isset($_SESSION['utilizator']))
{
echo '<p>Sunteti intrat cu numele '.$_SESSION['utilizator'].'</p>';
echo '<p>Pentru aceste date detineti drepturi</p>';

echo '<head><title>Afisare note studenti</title></head>';
echo '<body>';
echo '<h1><u>Afisare note studenti</u></h1>';
echo '<form action="rezultate1.php" method=post>';
echo '<table border=0>';
echo '<tr><td>Introduceti numarul matricol:</td><td align="center"><input type="text" name="nr_matr" size="5" maxlength="5"></td></tr>';
echo '<tr><td colspan="2" align="center"><input type=submit value="Afiseaza"></td></tr>';
echo '</table></form></body>';

echo '<head><title>Adaugare nota elev</title></head>';
echo '<body>';
echo '<h1><u>Adaugare nota elev</u></h1>';
echo '<form action="rezultate2.php" method=post>';
echo '<table border=0>';
echo '<tr><td>Introduceti nota:</td><td align="center"><input type="text" name="nota" size="5" maxlength="5"></td></tr>';
echo '<tr><td colspan="2" align="center"><input type=submit value="Afiseaza"></td></tr>';
echo '</table></form></body>';
}
else
{
echo '<p>Nu ati efectuat log in.</p>';
echo '<p>Acces restrictionat.</p>';
}
echo '<a href="sesiune.php"> Pagina principala</a>';
?>
