<html>
    <head>
        <title>Exercitiul 6 </title>
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
        }
p {
    font-family: "Chalkduster", Times, serif;
    font-size: 17px;
    text-align:left;
    padding: 30px;

}

</style> 
    </head>
    <body>
        <div>
<p>a) Să se găsească titlul cărților cele mai împrumutate.   </p>
<br>SELECT DISTINCT (titlu)
                      <br> FROM Carte c JOIN  Imprumut i ON i.id_carte=c.id_carte
                       <br>WHERE  (SELECT count(*) FROM  Imprumut WHERE i.id_carte=id_carte)>any(SELECT count(*)FROM Imprumut GROUP BY id_carte);
<br><br>
<a href="Exercitiu6a.php" class="button">Cauta</a>
<a href="index.php" class="button">Back</a>
<br><br>
</div>
    </body>
</form>
</html>
